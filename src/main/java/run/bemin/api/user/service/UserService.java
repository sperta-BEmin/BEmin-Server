package run.bemin.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.EmailCheckResponseDto;
import run.bemin.api.user.exception.UserException;
import run.bemin.api.user.repository.UserRepository;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // 이메일 중복 체크
    public EmailCheckResponseDto checkEmail(String email) {
        validateEmail(email);
        boolean isDuplicate = userRepository.existsByUserEmail(email);
        return new EmailCheckResponseDto(isDuplicate, isDuplicate ? "이미 존재하는 이메일입니다." : "사용 가능한 이메일입니다.");
    }

    private void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new UserException(ErrorCode.EMAIL_REQUIRED);
        }

        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!Pattern.matches(emailPattern, email)) {
            throw new UserException(ErrorCode.INVALID_EMAIL_FORMAT);
        }
    }
}
