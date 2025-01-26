package roomescape.service.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;

@Component
public class TokenProvider {

    private static final String ROLE = "role";

    private final String secret;
    private final long expiration;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration
    ) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(long memberId, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(memberId))
                .claim(ROLE, role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public long parseMemberId(String token) {
        Claims claims = parseClaims(token);

        return Long.valueOf(claims.getSubject());
    }

    public String parseRole(String token) {
        Claims claims = parseClaims(token);

        return claims.get(ROLE, String.class);
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new BadRequestException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new BadRequestException(ErrorCode.EXPIRED_TOKEN);
        }
    }
}
