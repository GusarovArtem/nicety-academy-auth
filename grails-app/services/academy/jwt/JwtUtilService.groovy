package academy.jwt


import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value

class JwtUtilService implements Serializable {

    static final long JWT_TOKEN_VALIDITY = 5*60*60

    @Value('${auth.jwt.secret}')
    private String secret

     String generateToken(String email) {
        Map<String, Object> claims = new HashMap<>()
        return doGenerateToken(claims, email)
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