package run.bemin.api.auth.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import run.bemin.api.auth.dto.SigninRequestDto;
import run.bemin.api.auth.dto.SigninResponseDto;
import run.bemin.api.auth.dto.SignupRequestDto;
import run.bemin.api.auth.dto.SignupResponseDto;
import run.bemin.api.auth.exception.SigninUnauthorizedException;
import run.bemin.api.auth.exception.SignupDuplicateEmailException;
import run.bemin.api.auth.exception.SignupDuplicateNicknameException;
import run.bemin.api.auth.exception.SignupInvalidEmailFormatException;
import run.bemin.api.auth.exception.SignupInvalidNicknameFormatException;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.auth.repository.AuthRepository;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.user.dto.UserAddressDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.entity.UserAddress;
import run.bemin.api.user.entity.UserRoleEnum;
import run.bemin.api.user.repository.UserAddressRepository;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

  @Mock
  private AuthRepository authRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtUtil jwtUtil;

  @Mock
  private UserAddressRepository userAddressRepository;

  @InjectMocks
  private AuthService authService;

  private User testUser;
  private SignupRequestDto signupRequestDto;
  private SigninRequestDto signinRequestDto;
  private UserAddressDto userAddressDto;


  @BeforeEach
  void setUp() {
    testUser = User.builder()
        .userEmail("test@gmail.com")
        .password("test1234")
        .name("testUser")
        .nickname("testUser1")
        .phone("010-1111-1111")
        .role(UserRoleEnum.CUSTOMER)
        .build();

    userAddressDto = UserAddressDto.builder()
        .bcode("bcode1")
        .jibunAddress("jibunAddress1")
        .roadAddress("roadAddress1")
        .detail("detail1")
        .build();

    signupRequestDto = SignupRequestDto.builder()
        .userEmail(testUser.getUserEmail())
        .password(testUser.getPassword())
        .name(testUser.getName())
        .nickname(testUser.getNickname())
        .phone(testUser.getPhone())
        .role(testUser.getRole())
        .address(userAddressDto)
        .build();
  }

  @Test
  @DisplayName("회원가입 성공 테스트")
  void SignupSuccessTest() {
    // Given
    // 중복된 이메일이나 닉네임이 없는 상황 가정
    when(authRepository.findByUserEmail(signupRequestDto.getUserEmail()))
        .thenReturn(Optional.empty());
    when(authRepository.findByNickname((signupRequestDto.getNickname())))
        .thenReturn(Optional.empty());
    // 인코딩된 비밀번호 반환
    when(passwordEncoder.encode(signupRequestDto.getPassword()))
        .thenReturn("encodedPassword");

    User savedUser = User.builder()
        .userEmail(signupRequestDto.getUserEmail())
        .password("encodedPassword")
        .name(signupRequestDto.getName())
        .nickname(signupRequestDto.getNickname())
        .phone(signupRequestDto.getPhone())
        .role(signupRequestDto.getRole())
        .build();
    when(authRepository.save(any(User.class))).thenReturn(savedUser);

    UserAddress savedUserAddress = UserAddress.builder()
        .bcode(userAddressDto.getBcode())
        .jibunAddress(userAddressDto.getJibunAddress())
        .roadAddress(userAddressDto.getRoadAddress())
        .detail(userAddressDto.getDetail())
        .isRepresentative(true)
        .user(savedUser)
        .build();
    when(userAddressRepository.save(any(UserAddress.class))).thenReturn(savedUserAddress);

    // When
    SignupResponseDto response = authService.signup(signupRequestDto);

    // Then
    assertEquals("test@gmail.com", response.getUserEmail());
    assertEquals(testUser.getRole().getAuthority(), response.getRole());
  }

  @Test
  @DisplayName("회원가입 실패 테스트 - 이메일 중복")
  void SignupDuplicateEmailTest() {
    // Given
    User existingEmail = User.builder().userEmail(signupRequestDto.getUserEmail()).build();
    when(authRepository.findByUserEmail(signupRequestDto.getUserEmail()))
        .thenReturn(Optional.of(existingEmail));

    // When & Then
    SignupDuplicateEmailException exception = assertThrows(SignupDuplicateEmailException.class,
        () -> authService.signup(signupRequestDto));

    System.out.println("Expected exception occurred: " + exception.getMessage());
  }

  @Test
  @DisplayName("회원가입 실패 테스트 - 닉네임 중복")
  void SignupDuplicateNicknameTest() {
    // Given
    User existingNickname = User.builder().nickname(signupRequestDto.getNickname()).build();
    when(authRepository.findByNickname(signupRequestDto.getNickname()))
        .thenReturn(Optional.of(existingNickname));

    // When & Then
    SignupDuplicateNicknameException exception = assertThrows(SignupDuplicateNicknameException.class,
        () -> authService.signup(signupRequestDto));

    System.out.println("Expected exception occurred: " + exception.getMessage());
  }

  @Test
  @DisplayName("회원가입 - 이메일 형식이 올바르지 않을 경우")
  void invalidEmailFormatTest() {
    // Given
    String invalidEmail = "invalid@test";

    // When & Then
    SignupInvalidEmailFormatException exception = assertThrows(SignupInvalidEmailFormatException.class,
        () -> authService.checkEmail(invalidEmail));

    assertEquals("이메일 형식이 올바르지 않습니다.", exception.getMessage());
    System.out.println("Expected exception occurred: " + exception.getMessage());
  }

  @Test
  @DisplayName("회원가입 - 닉네임 형식이 올바르지 않을 경우")
  void invalidNicknameFormatTest() {
    // Given
    String invalidNickname = "!";

    // When & Then
    SignupInvalidNicknameFormatException exception = assertThrows(SignupInvalidNicknameFormatException.class,
        () -> authService.checkNickname(invalidNickname));

    assertEquals("닉네임 형식이 올바르지 않습니다.", exception.getMessage());
    System.out.println("Expected exception occurred: " + exception.getMessage());
  }


  @Test
  @DisplayName("로그인 성공 테스트")
  void SigninSuccessTest() {
    // Given
    signinRequestDto = SigninRequestDto.builder()
        .userEmail("test@gmail.com")
        .password("test1234")
        .build();

    testUser = User.builder()
        .userEmail("test@gmail.com")
        .password("encodedTest1234") // 실제 저장된 인코딩된 비밀번호
        .role(UserRoleEnum.CUSTOMER)
        .build();

    // 모의 Authentication 객체 반환
    Authentication dummyAuth = mock(Authentication.class);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenReturn(dummyAuth);

    // dummyAuth.getPrincipal()에서 UserDetailsImpl 객체 반환
    UserDetailsImpl userDetails = new UserDetailsImpl(testUser);
    when(dummyAuth.getPrincipal()).thenReturn(userDetails);

    when(jwtUtil.createAccessToken(anyString(), any(UserRoleEnum.class)))
        .thenReturn("testToken");

    // When
    SigninResponseDto response = authService.signin(signinRequestDto.getUserEmail(), signinRequestDto.getPassword());

    // Then
    assertEquals("testToken", response.getToken());
    System.out.println("token: " + response.getToken());
  }

  @Test
  @DisplayName("로그인 실패 - 존재하지 않은 회원")
  void SigninFailureNonExistentUserTest() {
    // Given
    signinRequestDto = SigninRequestDto.builder()
        .userEmail("non@test.com")
        .password("non123")
        .build();

    // When
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
        .thenThrow(new BadCredentialsException("Bad credentials"));

    // Then
    SigninUnauthorizedException exception = assertThrows(SigninUnauthorizedException.class, () ->
        authService.signin(signinRequestDto.getUserEmail(), signinRequestDto.getPassword())
    );

    System.out.println("Expected exception occurred: " + exception.getMessage());
  }
}