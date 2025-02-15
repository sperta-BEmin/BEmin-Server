package run.bemin.api.user.exception;

import lombok.Getter;
import run.bemin.api.general.exception.ErrorCode;

@Getter
public class UserException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
