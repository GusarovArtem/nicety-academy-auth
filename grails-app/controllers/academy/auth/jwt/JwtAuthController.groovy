package academy.auth.jwt

import academy.user.AcademyUser
import academy.user.UserCredentials
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity

class JwtAuthController {

    static allowedMethods = [generateAuthToken: "GET"]

    @Value('${auth.header}')
    private String AUTH_HEADER

    def passwordEncoder

    def jwtUtilService

    def generateAuthToken() {
        UserCredentials credentials = credentialsFromAuthHeader(request.getHeader(AUTH_HEADER))

        if (credentials) {
            final AcademyUser user = AcademyUser.findByEmail(credentials.email)
            if (user) {
                if (comparePasswords(credentials.password, user.password)) {
                    return render(jwtUtilService.generateToken(user.email))
                }
            }
        }

        return ResponseEntity.badRequest().body("Invalid email or password")
    }

    private UserCredentials credentialsFromAuthHeader(String authHeader) {
        String encodedCredentials = authHeader.replaceFirst("Basic ", "")
        String decodedCredentials = new String(Base64.getDecoder().decode(encodedCredentials))

        String[] credentials =  decodedCredentials.split(":")
        if (credentials.size() == 2)
            return new UserCredentials(credentials[0], credentials[1])
        else
            return null
    }

    boolean comparePasswords(String plainPassword, String hashedPassword) {
        hashedPassword = hashedPassword.replace('{bcrypt}', '')
        passwordEncoder.matches(plainPassword, hashedPassword)
    }
}
