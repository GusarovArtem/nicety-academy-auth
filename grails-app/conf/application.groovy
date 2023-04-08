// QUARTZ
quartz {
    autoStartup = false

    globalJobListenerNames = []
    globalTriggerListenerNames = []
    schedulerListenerNames = []
}

// CACHE
grails {
    cache {
        enabled = true

        ehcache {
            ehcacheXmlLocation = 'classpath:ehcache-cache-plugin.xml'
            lockTimeout = 200 // In milliseconds
        }
    }
}

// JWT
auth{
    jwt {
        secret='kY2iN4qrWe/VFzN1dSmpSuxzFJwJKllt4p/fv4zz1ayGGkdzzxQ2X93TH0O312S'
    }
}

//SPRING SECURITY
// in current version spring-security the username property might not be override-able
//grails.plugin.springsecurity.userLookup.usernamePropertyName = 'email'
grails.plugin.springsecurity.userLookup.userDomainClassName = 'academy.user.AcademyUser'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'academy.user.security.AcademyUserRole'
grails.plugin.springsecurity.authority.className = 'academy.user.security.AcademyRole'

grails.plugin.springsecurity.useSwitchUserFilter = true

grails.plugin.springsecurity.rememberMe.cookieName = "academyRememberMeCookieName"
grails.plugin.springsecurity.rememberMe.key = "academyRememberMeKEY"
grails.plugin.springsecurity.rememberMe.tokenValiditySeconds = 1209600

grails.plugin.springsecurity.useBasicAuth = true
grails.plugin.springsecurity.basic.realmName = "Academy basic auth realm"
grails.plugin.springsecurity.filterChain.chainMap = [
        [pattern: '/**',                 filters: 'JOINED_FILTERS,-basicAuthenticationFilter,-basicExceptionTranslationFilter']
]

grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.logout.alwaysUseDefaultTargetUrl = true

grails.plugin.springsecurity.controllerAnnotations.staticRules = [
        [pattern: '/',               access: ['permitAll']]
]

grails.plugin.console.csrfProtection.enabled = false
