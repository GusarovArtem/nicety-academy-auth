package academy.auth.jwt

import academy.model.auth.jwt.JwtRequest
import academy.model.auth.jwt.JwtResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

class JwtAuthController {

    def authenticationManager

    def jwtUtilService

    def userDetailsService

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    ResponseEntity<?> generateAuthToken(JwtRequest authRequest) throws Exception {

        authenticate(authRequest.email, authRequest.password)

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.email)

        final String token = jwtUtilService.generateToken(userDetails)

        return ResponseEntity.ok(new JwtResponse(token))
    }

    private void authenticate(String email, String password) throws Exception {
        Objects.requireNonNull(email)
        Objects.requireNonNull(password)

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password))
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e)
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e)
        }
    }
}
