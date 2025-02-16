package run.bemin.api.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.auth.dto.SigninResponseDto;
import run.bemin.api.auth.jwt.JwtUtil;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.user.dto.SignupRequestDto;
import run.bemin.api.user.dto.SignupResponseDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.exception.UserException;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(@Valid SignupRequestDto requestDto) {
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkEmail = userRepository.findByUserEmail(requestDto.getUserEmail());
        if (checkEmail.isPresent()) {
            throw new UserException(ErrorCode.DUPLICATE_EMAIL);
        }

        Optional<User> checkNickname = userRepository.findByNickname(requestDto.getNickname());
        if (checkNickname.isPresent()) {
            throw new UserException(ErrorCode.DUPLICATE_NICKNAME);
        }


        User user = User.builder()
                .userEmail(requestDto.getUserEmail())
                .password(encodePassword)
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .phone(requestDto.getPhone())
                .address(requestDto.getAddress())
                .role(requestDto.getRole())
                .build();
        User savedUser = userRepository.save(user);

        return new SignupResponseDto(savedUser.getUserEmail(), savedUser.getRole().getAuthority());
    }

    @Transactional(readOnly = true)
    public SigninResponseDto signin(String userEmail, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userEmail, password)
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String authToken = jwtUtil.createToken(userDetails.getUsername(), userDetails.getRole());
            return new SigninResponseDto(authToken, userDetails.getUsername());

        } catch (BadCredentialsException e) {
            throw new UserException(ErrorCode.UNAUTHORIZED_USER);
        }
    }

}
