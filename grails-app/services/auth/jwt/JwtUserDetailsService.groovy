package auth.jwt

import academy.user.AcademyUser
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JwtUserDetailsService implements UserDetailsService {

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AcademyUser user = AcademyUser.findByEmail(username);
        if (user) {
            return new User(
                    user.email,
                    user.password,
                    user.enabled,
                    user.accountNonExpired,
                    user.credentialsNonExpired,
                    user.accountNonLocked,
                    new ArrayList<>()
            )
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username)
        }
    }

}