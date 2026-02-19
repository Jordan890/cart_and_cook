package com.cartandcook.selfhosted.security;

import com.cartandcook.core.api.ExternalIdentityRepository;
import com.cartandcook.core.api.UserRepository;
import com.cartandcook.core.domain.ExternalIdentity;
import com.cartandcook.core.domain.User;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserProvider {

    private final ExternalIdentityRepository identityRepo;
    private final UserRepository userRepo;

    public CurrentUserProvider(
            ExternalIdentityRepository identityRepo,
            UserRepository userRepo
    ) {
        this.identityRepo = identityRepo;
        this.userRepo = userRepo;
    }

    /**
     * Resolve the internal User from the JWT.
     * Auto-provisions a User + ExternalIdentity if first login.
     */
    public User getCurrentUser(Jwt jwt) {
        String issuer = jwt.getIssuer().toString();
        String sub = jwt.getSubject();
        String email = jwt.getClaimAsString("email");
        String name = jwt.getClaimAsString("name");

        // Look up external identity first
        ExternalIdentity identity = identityRepo.findByIssuerAndSubject(issuer, sub)
                .orElseGet(() -> {
                    // First login → create internal User
                    User user = userRepo.save(new User(null, email, name));
                    return identityRepo.save(
                            new ExternalIdentity(user.getId(), issuer, sub)
                    );
                });

        // Return the internal User
        return userRepo.findById(identity.getUserId())
                .orElseThrow(() -> new RuntimeException("Internal user not found"));
    }
}
