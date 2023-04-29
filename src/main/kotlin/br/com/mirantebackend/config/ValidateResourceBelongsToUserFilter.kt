package br.com.mirantebackend.config

import org.springframework.security.core.context.SecurityContextHolder
import java.net.http.HttpRequest
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class ValidateResourceBelongsToUserFilter: Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        val httpServletRequest = request as HttpServletRequest
        SecurityContextHolder.getContext().authentication?.let { authentication ->
            val username = authentication.name
            println("Acessing user name: $username")

            //todo adicionar username no header
        }
        chain.doFilter(request, response)
    }
}