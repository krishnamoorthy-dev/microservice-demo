package com.krishna.api_gateway.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JWTUtil {
    private final static String mySecret = "my_secret";
    private final static Algorithm algorithm = Algorithm.HMAC256(mySecret);

    public static boolean verifyToken(String token) {
        boolean isTokenValid = false;
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            isTokenValid = true;
        } catch (JWTVerificationException e) {
            log.error("Token is not valid!", e);
        }
        return isTokenValid;
    }
}
