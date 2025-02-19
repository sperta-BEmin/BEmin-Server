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

    Object principal = authentication.getPrincipal();
    if (principal instanceof UserDetailsImpl) {
      return Optional.of(((UserDetailsImpl) principal).getUsername());
    } else if (principal instanceof String) {
      // 예를 들어, 익명 사용자일 경우 Optional.empty()를 반환하거나 기본값을 줄 수 있습니다.
      // 회원 가입하면, 스프링 시큐리티 세션 안에 저장된 회원 정보가 없습니다! 그래서 Optional 타입으로 null 처리하고,
      // created_by 컬럼에 null 이 저장됩니다.
      return Optional.empty();
    }

    return Optional.empty();
  }

  private void validateAuthentication(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new AuthAccessDeniedException(ErrorCode.AUTH_ACCESS_DENIED.getMessage());
    }
  }


}
