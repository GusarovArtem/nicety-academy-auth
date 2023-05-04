package academy.user


import academy.user.role.Role
import academy.user.role.UserRole

class User {

    String email

    String password

    static mapping = {
        autowire true
    }

    static constraints = {
        email(blank: false, email: true, validator: { value, user ->
            String login = value.trim()

            def notUnique
            if (User.findAllByEmail(login).find { it.id != user.id }) {
                notUnique = ['User.email.unique']
            }
            if (notUnique) {
                return notUnique
            }

            true
        })

        password(nullable: false, blank: false, password: true)
    }


    Set<Role> authorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
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
