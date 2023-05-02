package br.com.mirantebackend.filters

import br.com.mirantebackend.model.enums.UserRolesEnum
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class HeaderAppenderFilter : Filter {

    companion object {
        const val HEADER_USERNAME = "username"
        const val HEADER_IS_USER_PRIVILEGED = "isUserPrivileged"
    }

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        if (isUserAuthenticated()) {
            val username = getAuthenticatedUsername()
            val hasPrivilegedAccess = hasUserPrivileged()
            val httpServletRequest = request as HttpServletRequest
            val customRequestWrapper = CustomRequestWrapper(httpServletRequest)
            customRequestWrapper.putHeader(HEADER_USERNAME, username)
            customRequestWrapper.putHeader(HEADER_IS_USER_PRIVILEGED, hasPrivilegedAccess.toString())
            chain.doFilter(customRequestWrapper, response)
        } else {
            chain.doFilter(request, response)
        }
    }

    private fun isUserAuthenticated() =
        SecurityContextHolder.getContext().authentication != null

    private fun getAuthenticatedUsername(): String {
        if (isUserAuthenticated()) {
            return SecurityContextHolder.getContext().authentication.name
        }
        return ""
    }

    private fun hasUserPrivileged(): Boolean {
        if (isUserAuthenticated()) {
            return SecurityContextHolder
                .getContext()
                .authentication
                .authorities.contains(UserRolesEnum.ROLE_ADMIN)
        }
        return false
    }
}
