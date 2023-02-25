package academy.user


import academy.user.security.AcademyRole
import academy.user.security.AcademyUserRole

class AcademyUser {

    def springSecurityService


    String email

    String password
    String passwordConfirm

    boolean enabled
    boolean accountExpired
    boolean passwordExpired
    boolean accountLocked

    static mapping = {
        autowire true
    }

    static transients = ['springSecurityService', 'passwordConfirm']

    static constraints = {
        email(blank: false, email: true, validator: { value, user ->
            String login = value.trim()

            def notUnique
            if (AcademyUser.findAllByEmail(login).find { it.id != user.id }) {
                notUnique = ['AcademyUser.email.unique']
            }
            if (notUnique) {
                return notUnique
            }

            true
        })

        password(nullable: false, blank: false, password: true)
        passwordConfirm(nullable: false, blank: false, bindable: true, password: true, validator: { val, obj ->
            obj.password == val ? true :
                    ['invalid.matchingpasswords']
        })
    }


    Set<AcademyRole> authorities() {
        AcademyUserRole.findAllByUser(this).collect { it.role } as Set
    }

    def afterLoad() {
        passwordConfirm = password
    }

    def beforeInsert() {
        encodePassword()
        capitalizeName()
    }

    def beforeUpdate() {
        if (password != null && isDirty('password') && validate()) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
        passwordConfirm = password
    }


}
