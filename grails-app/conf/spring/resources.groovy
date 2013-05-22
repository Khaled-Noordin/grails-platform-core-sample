// Place your Spring DSL code here
beans = {
    grailsSecurityBridge(com.gopivotal.security.MySecurityProvider) {
        springSecurityService = ref('springSecurityService')
        grailsApplication = ref('grailsApplication')
    }
}
