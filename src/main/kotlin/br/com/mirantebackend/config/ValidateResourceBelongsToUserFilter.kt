package br.com.mirantebackend.config

import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class ValidateResourceBelongsToUserFilter: Filter {

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        SecurityContextHolder.getContext().authentication?.let { authentication ->
            val username = authentication.name
            println("Acessing user name: $username")
            chain.doFilter(request, response)
            //todo validar se o recurso pertence ao usuário ou pode ser alterado por ele
        }
        chain.doFilter(request, response)
    }
}