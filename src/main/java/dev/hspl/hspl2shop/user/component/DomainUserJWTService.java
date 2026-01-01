package dev.hspl.hspl2shop.user.component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import dev.hspl.hspl2shop.common.DomainUser;
import dev.hspl.hspl2shop.common.component.ApplicationAttributeProvider;
import dev.hspl.hspl2shop.user.exception.JWTGenerationErrorException;
import dev.hspl.hspl2shop.user.exception.JWTTokenVerificationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.Instant;

@Component
@ApplicationScope
@RequiredArgsConstructor
public class DomainUserJWTService {
    private final ApplicationAttributeProvider attributeProvider;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    private void init() {
        this.algorithm = Algorithm.HMAC384(attributeProvider.jwtSignatureSecret());
        this.verifier = JWT.require(algorithm).build();
    }

    private static final String CLAIM_USER_FULL_NAME = "u_fn";
    //private static final String CLAIM_USER_EMAIL = "u_em";
    private static final String CLAIM_USER_PHONE_NUMBER = "u_ph";
    private static final String CLAIM_USER_ROLE = "u_rl";
    private static final String CLAIM_USER_STATUS = "u_ss";

    public String generateTokenForUser(DomainUser user) {
        try {
            return JWT.create()
                    .withSubject(user.id().toString())
                    //.withIssuer("Hspl2Shop")
                    //.withAudience("Hspl2Shop")
                    .withClaim(CLAIM_USER_FULL_NAME, user.fullName().value())
                    .withClaim(CLAIM_USER_PHONE_NUMBER, user.phoneNumber().value())
                    .withClaim(CLAIM_USER_ROLE, user.role().toString())
                    .withClaim(CLAIM_USER_STATUS, user.isAccountActive())
                    .withExpiresAt(Instant.now().plusSeconds(attributeProvider.accessTokenLifetimeMinutes() * 60))
                    .sign(this.algorithm);
        } catch (JWTCreationException exception) {
            throw new JWTGenerationErrorException(exception.getMessage());
        }
    }

    public DomainUser validateTokenAndExtractUserInfo(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);

            return new JWTPayloadDomainUser(
                    jwt.getSubject(),
                    jwt.getClaim(CLAIM_USER_FULL_NAME).asString(),
                    jwt.getClaim(CLAIM_USER_PHONE_NUMBER).asString(),
                    jwt.getClaim(CLAIM_USER_ROLE).asString(),
                    jwt.getClaim(CLAIM_USER_STATUS).asBoolean()
            );
        } catch (JWTVerificationException exception) {
            throw new JWTTokenVerificationException(exception.getMessage());
        }
    }
}
