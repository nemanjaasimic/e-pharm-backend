package com.isa.epharm.config.security;

import com.isa.epharm.config.CustomProperties;
import com.isa.epharm.controller.exception.CustomException;
import com.isa.epharm.model.User;
import com.isa.epharm.model.enumeration.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final CustomProperties customProperties;

    public String generateAuthToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + customProperties.getJwtExpirationInMs());
        Claims claims = Jwts.claims().setSubject(userPrincipal.getId().toString());
        claims.put("role", userPrincipal.getAuthorities()
                .stream()
                .findFirst()
                .orElseThrow(() -> new CustomException("Role not found!"))
                .getAuthority());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, customProperties.getJwtSecret())
                .compact();
    }

    public UUID getUserIdFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(customProperties.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return UUID.fromString(claims.getSubject());
    }

    public Role getRoleFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(customProperties.getJwtSecret())
                .parseClaimsJws(token)
                .getBody();

        return Role.valueOf((String) claims.get("role"));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(customProperties.getJwtSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
