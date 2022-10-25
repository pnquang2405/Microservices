package com.example.testjwt.Config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.example.testjwt.model.UserPrincipal;

@Component
public class UpdateConfig implements AuditorAware<Long> {

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}
		if (authentication.getPrincipal() == "anonymousUser") {
			return Optional.of(0l);
		}
		return Optional.of(((UserPrincipal) authentication.getPrincipal()).getUserId());
	}

}
