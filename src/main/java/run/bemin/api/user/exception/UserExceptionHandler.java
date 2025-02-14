package run.bemin.api.user.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import run.bemin.api.general.exception.ErrorResponse;

@Slf4j(topic = "user 패키지 예외만 처리")
public class UserExceptionHandler {

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<ErrorResponse> handleUserException(UserException e) {
        log.error("handleUserException", e);
        log.error("UserException 발생: {}", e.getErrorCode());
        final ErrorResponse response = ErrorResponse.of(e.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getErrorCode().getStatus()));
    }

}
