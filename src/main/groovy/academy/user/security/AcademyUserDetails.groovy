package academy.user.security

import grails.plugin.springsecurity.userdetails.GrailsUser
import org.springframework.security.core.GrantedAuthority

class AcademyUserDetails extends GrailsUser {

    AcademyUserDetails(
            String email, String password,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Collection<GrantedAuthority> authorities,
    ) {
        super(email, password, enabled, accountNonExpired,
                credentialsNonExpired, accountNonLocked, authorities, id)


    }
}
