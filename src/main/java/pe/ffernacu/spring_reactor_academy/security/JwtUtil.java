package pe.ffernacu.spring_reactor_academy.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 60  * 1000;

    @Value("${jjwt.secret}")
    private String secret;

    public String generateToken(User user){

        //Payload
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles());
        claims.put("username", user.getUsername());
        claims.put("test-value", "sample-test-value");

        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .claims(claims) //informacion complementaria
                .subject(user.getUsername()) //usuario creador del token
                .issuedAt(new Date(System.currentTimeMillis())) //fecha de creacion del token
                .expiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY)) //fecha de expiracion del token
                .signWith(key) //firma o sello del token
                .compact();
    }

    public Claims getAllClaimsFromToken(String token){
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();


    }

    public String getUserNameFromToken(String token){
        return getAllClaimsFromToken(token).getSubject();
    }

    public Date getExpirationDateFromToken(String token){
        return getAllClaimsFromToken(token).getExpiration();
    }

    public boolean validateToken(String token){
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }


}
