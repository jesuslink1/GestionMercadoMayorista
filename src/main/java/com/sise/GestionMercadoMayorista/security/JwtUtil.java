package com.sise.GestionMercadoMayorista.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${app.jwt.secret}")
    private String secretKey;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;

    // NUEVO: expiración para reset (ms). Ej: 900000 = 15 min
    @Value("${app.jwt.reset-expiration:900000}")
    private long resetExpirationMs;

    private static final String CLAIM_ROLE = "rol";
    private static final String CLAIM_PURPOSE = "purpose";
    private static final String PURPOSE_RESET_PASSWORD = "RESET_PASSWORD";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // ======== EXTRACCIÓN DE CLAIMS ========

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ======== GENERACIÓN TOKEN LOGIN ========

    public String generateToken(String username, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ROLE, rol);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ======== GENERACIÓN TOKEN RESET ========

    public String generatePasswordResetToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_PURPOSE, PURPOSE_RESET_PASSWORD);

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + resetExpirationMs);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email) // subject = email
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Valida que:
     * - Firma válida
     * - No expirado
     * - purpose == RESET_PASSWORD
     * Retorna el email (subject) si es válido.
     */
    public String validatePasswordResetTokenAndGetEmail(String token) {
        Claims claims = extractAllClaims(token);

        String purpose = claims.get(CLAIM_PURPOSE, String.class);
        if (!PURPOSE_RESET_PASSWORD.equals(purpose)) {
            throw new JwtException("Token purpose inválido");
        }
        if (claims.getExpiration() == null || claims.getExpiration().before(new Date())) {
            throw new ExpiredJwtException(null, claims, "Token expirado");
        }
        return claims.getSubject();
    }

    // ✅ NUEVO: para usarlo fácil en controller sin try/catch repetido
    public boolean isPasswordResetTokenValid(String token) {
        try {
            validatePasswordResetTokenAndGetEmail(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // ✅ NUEVO (opcional): nombre más claro para controller
    public String extractEmailFromPasswordResetToken(String token) {
        return validatePasswordResetTokenAndGetEmail(token);
    }

    // ======== VALIDACIÓN LOGIN TOKEN ========

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String usernameToken = extractUsername(token);
        return usernameToken.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}