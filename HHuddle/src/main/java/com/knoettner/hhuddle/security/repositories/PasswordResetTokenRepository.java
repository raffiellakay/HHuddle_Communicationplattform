package com.knoettner.hhuddle.security.repositories;

import com.knoettner.hhuddle.security.models.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken (String token);
}
