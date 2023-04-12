package academy.auth.jwt

import academy.model.auth.jwt.JwtRequest
import academy.model.auth.jwt.JwtResponse
import academy.user.AcademyUser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller

@Controller
class JwtAuthController {

    static allowedMethods = [generateAuthToken: "POST"]

    @Autowired(required = false)
    PasswordEncoder passwordEncoder

    def jwtUtilService

    ResponseEntity<?> generateAuthToken(JwtRequest authRequest) throws Exception {

        final AcademyUser user = AcademyUser.findByEmail(authRequest.email)

        if (user) {
            if (comparePasswords(authRequest.password, user.password)) {
                final String token = jwtUtilService.generateToken(user)

                return ResponseEntity.ok(new JwtResponse(token))
            }
        }

        return ResponseEntity.badRequest().body("Invalid email or password")
    }

    boolean comparePasswords(String enteredPassword, String encodedPassword) {
        String encodedEnteredPassword = passwordEncoder.encode(enteredPassword)

        passwordEncoder.matches(enteredPassword, encodedPassword)
    }
}
