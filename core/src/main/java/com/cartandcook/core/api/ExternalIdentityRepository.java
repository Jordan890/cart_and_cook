package com.cartandcook.core.api;

import com.cartandcook.core.domain.ExternalIdentity;

import java.util.Optional;

public interface ExternalIdentityRepository {

    Optional<ExternalIdentity> findByIssuerAndSubject(String issuer, String subject);
    ExternalIdentity save(ExternalIdentity identity);
}
