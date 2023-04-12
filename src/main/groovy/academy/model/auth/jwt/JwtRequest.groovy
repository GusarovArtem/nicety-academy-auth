package academy.model.auth.jwt

import grails.validation.Validateable

class JwtRequest implements Validateable {

    String email

    String password

}
