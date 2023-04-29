package br.com.mirantebackend.services

import br.com.mirantebackend.mapper.toUserDetailsDto
import br.com.mirantebackend.repository.UserDocumentRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceLocalImpl(
    private val userDocumentRepository: UserDocumentRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        return username.let {
            userDocumentRepository.findByUsername(username)
                .map { it.toUserDetailsDto() }
                .map { User(it.username, it.password, true, true, true, true, it.authorities) }
                .orElseThrow { UsernameNotFoundException("The follow username was not found: $username") }
        }
    }
}
