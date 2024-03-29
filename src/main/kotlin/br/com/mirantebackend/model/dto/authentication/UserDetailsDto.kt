package br.com.mirantebackend.model.dto.authentication

import br.com.mirantebackend.model.enums.UserRolesEnum
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

data class UserDetailsDto(
    private val id: String?,

    private val name: String,

    private val username: String,

    private val email: String,

    private val password: String,

    private val userRole: UserRolesEnum,

    private val isEnable: Boolean,

    private val createdAt: LocalDateTime

) : UserDetails {

    override fun getUsername(): String = username

    override fun getPassword(): String = password

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(userRole)
    }

    override fun isAccountNonExpired(): Boolean = isEnable

    override fun isAccountNonLocked(): Boolean = isEnable

    override fun isCredentialsNonExpired(): Boolean = isEnable

    override fun isEnabled(): Boolean = isEnabled
}
