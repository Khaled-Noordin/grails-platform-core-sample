/*
 * This is based on http://grailsrocks.com/blog/2012/03/28/hooking-up-platform-core-security-api-to-your-security-provider
 */
package com.gopivotal.security
 
import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils        
import org.grails.plugin.platform.security.SecurityBridge
import org.apache.commons.logging.LogFactory
 
class MySecurityProvider implements SecurityBridge {
    def log = LogFactory.getLog(MySecurityProvider)
 
    def springSecurityService
    def grailsApplication
    
    String getProviderName() {
        "My Spring Security"
    }
    
    String getUserIdentity() {
        def princ = springSecurityService.principal
        if (princ instanceof String) {
            return null
        } else {
            //return princ?.identity   
            return princ?.id
        }
    }
 
    def getUserInfo() {
        def princ = springSecurityService.principal 
        def value = princ instanceof String ? null : princ
        return value
    }
 
    boolean userHasRole(role) {
        springSecurityService.ifAnyGranted(role.toString())
    }
 
    /**
     * Can the current user access this object to perform the named action?
     */
    boolean userIsAllowed(object, action) {
        true // Not implemented yet
    }
    
    Map createLink(String action) {
        def r = [controller:'myAuth']
        switch (action) {
            case 'login': r.action = "login"; break;
            case 'logout': r.action = "logout"; break;
            case 'signup': r.action = "signup"; break;
            default: 
                throw new IllegalArgumentException('Security link [$action] is not recognized')
        }
        return r
    }
    
    def withUser(identity, Closure closure) {
        SpringSecurityUtils.doWithAuth(identity, closure)
    }
}
