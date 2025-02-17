package run.bemin.api.general.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.security.UserDetailsImpl;
import run.bemin.api.user.exception.UserException;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    // 권한 설정이 완성되면 주석 해제 하겠습니다.
    //validateAuthentication(authentication);
    return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
  }

  private void validateAuthentication(Authentication authentication) throws UserException {
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new UserException(ErrorCode.INVALID_ACCESS);
    }
  }
}
