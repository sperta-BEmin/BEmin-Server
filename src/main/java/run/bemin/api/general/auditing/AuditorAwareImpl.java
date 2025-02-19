package run.bemin.api.general.auditing;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import run.bemin.api.auth.exception.AuthAccessDeniedException;
import run.bemin.api.general.exception.ErrorCode;
import run.bemin.api.security.UserDetailsImpl;

public class AuditorAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    validateAuthentication(authentication);
    return Optional.of(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
  }

  private void validateAuthentication(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new AuthAccessDeniedException(ErrorCode.AUTH_ACCESS_DENIED.getMessage());
    }
  }
}
