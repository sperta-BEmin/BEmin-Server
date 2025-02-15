package run.bemin.api.auth.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import run.bemin.api.user.dto.SignupRequestDto;
import run.bemin.api.user.dto.SignupResponseDto;
import run.bemin.api.user.entity.User;
import run.bemin.api.user.exception.UserException;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SignupResponseDto signup(@Valid SignupRequestDto requestDto) {
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkEmail = userRepository.findByUserEmail(requestDto.getUserEmail());
        if(checkEmail.isPresent()) {
            throw new UserException(ErrorCode.DUPLICATE_EMAIL);
        }


        Optional<User> checkNickname = userRepository.findByNickname(requestDto.getNickname());
        if(checkNickname.isPresent()) {
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

        return new SignupResponseDto(
                savedUser.getUserEmail(),
                savedUser.getRole().getAuthority()
        );
    }
}
