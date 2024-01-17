package com.dcorp.hightech.acounts.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * This class will aware a person who is doing when data is revised.
 * Supporting LastModifyBy and CreateBy Annotation
 * To enable this:
 * -Step 1: Adding @EntityListeners(AuditingEntityListener.class) annotation in base class
 * -Step 2: Adding @EnableJpaAuditing(auditorAwareRef = "auditAwareImpl") annotation in main class
 */
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("ACCOUNT_MS");
    }
}
