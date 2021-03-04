package com.task.manager.model.audit;

import com.task.manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = "";
        if (authentication.isAuthenticated()) {
             name = SecurityContextHolder.getContext().getAuthentication().getName();
            return Optional.ofNullable(name);
        }
        return Optional.ofNullable(name);
    }
}
