package academy.user


import academy.user.role.AcademyRole
import academy.user.role.AcademyUserRole

class AcademyUser {

    String email

    String password

    static mapping = {
        autowire true
    }

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
    }


    Set<AcademyRole> authorities() {
        AcademyUserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (password != null && isDirty('password') && validate()) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }


}
