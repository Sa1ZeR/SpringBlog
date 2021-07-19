package me.sa1zer_.springblog.security;

import io.jsonwebtoken.*;
import me.sa1zer_.springblog.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTTokenProvider {

    public static final Logger logger = LoggerFactory.getLogger(JWTTokenProvider.class);

    public String generateToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        long now = System.currentTimeMillis();

        Date expiredDate = new Date(now + SecurityConstants.EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String, Object> claimsMap = new HashMap<>();

        claimsMap.put("id", userId);
        claimsMap.put("username", user.getUsername());
        claimsMap.put("firstName", user.getName());
        claimsMap.put("lastName", user.getLastname());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claimsMap)
                .setIssuedAt(new Date(now))
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException
                | UnsupportedJwtException | IllegalArgumentException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return false;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();

        return Long.parseLong((String)claims.get("id"));
    }
}
