package auth.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

import java.util.function.Function

class JwtUtilService implements Serializable {

    static final long JWT_TOKEN_VALIDITY = 5*60*60

    @Value('${auth.jwt.secret}')
    private String secret

     String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>()
        return doGenerateToken(claims, userDetails.getUsername())
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY*1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

}