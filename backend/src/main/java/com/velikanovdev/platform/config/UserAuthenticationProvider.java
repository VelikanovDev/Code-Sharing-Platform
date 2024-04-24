package com.velikanovdev.platform.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.velikanovdev.platform.dto.UserAuthDetails;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    private Algorithm algorithm;
    private final UserDetailsService userDetailsService;

    @Autowired
    public UserAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        // this is to avoid having the raw secret key available in the JVM
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        algorithm = Algorithm.HMAC256(secretKey);
    }

    public String createToken(UserAuthDetails user) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000); // 1 hour

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(user.getUsername())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("username", user.getUsername())
                .withClaim("role", user.getRole().toString())
                .sign(algorithm);
    }

    public Authentication validateToken(String token) {
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded;
        try {
            decoded = verifier.verify(token);
        } catch (Exception e) {
            throw new RuntimeException("Token validation failed");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(decoded.getSubject());

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

}
