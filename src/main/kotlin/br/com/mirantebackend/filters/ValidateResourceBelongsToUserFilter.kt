package br.com.mirantebackend.filters

import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class ValidateResourceBelongsToUserFilter : Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        SecurityContextHolder.getContext().authentication?.let { authentication ->
            val username = authentication.name
            println("Acessing user name: $username")
            // todo adicionar username no header
        }
        chain.doFilter(request, response)
    }
}
