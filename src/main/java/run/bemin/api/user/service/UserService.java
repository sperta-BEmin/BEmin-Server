package run.bemin.api.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.user.dto.EmailCheckResponseDto;
import run.bemin.api.user.dto.NicknameCheckResponseDto;
import run.bemin.api.user.exception.UserException;
import run.bemin.api.user.repository.UserRepository;

import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * 이메일 중복 체크
     **/
    @Transactional(readOnly = true)
    public EmailCheckResponseDto checkEmail(String email) {
        validateEmail(email);
        boolean isDuplicate = userRepository.existsByUserEmail(email);

        if (isDuplicate) {
            return new EmailCheckResponseDto(true, ErrorCode.DUPLICATE_EMAIL.getMessage(), ErrorCode.DUPLICATE_EMAIL.getCode());
        }

        return new EmailCheckResponseDto(false, "사용 가능한 이메일입니다.", null);
    }

    // 이메일 형식 검증
    private void validateEmail(String email) {
        String emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        if (!Pattern.matches(emailPattern, email)) {
            throw new UserException(ErrorCode.INVALID_EMAIL_FORMAT);
        }
    }

    /**
     * 닉네임 중복 체크
     **/
    @Transactional(readOnly = true)
    public NicknameCheckResponseDto checkNickname(String nickname) {
        validateNickname(nickname);
        boolean isDuplicate = userRepository.existsByNickname(nickname);

        if (isDuplicate) {
            return new NicknameCheckResponseDto(true, ErrorCode.DUPLICATE_NICKNAME.getMessage(), ErrorCode.DUPLICATE_NICKNAME.getCode());
        }

        return new NicknameCheckResponseDto(false, "사용 가능한 닉네임입니다.", null);
    }

    // 닉네임 형식 검증
    private void validateNickname(String nickname) {
        String nicknamePattern = "^[a-z0-9]{4,10}$";
        if (!Pattern.matches(nicknamePattern, nickname)) {
            throw new UserException(ErrorCode.INVALID_NICKNAME_FORMAT);
        }
    }
}
