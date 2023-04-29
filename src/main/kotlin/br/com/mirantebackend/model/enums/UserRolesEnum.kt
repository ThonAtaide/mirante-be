package br.com.mirantebackend.model.enums

import org.springframework.security.core.GrantedAuthority

enum class UserRolesEnum(private val authority: String) : GrantedAuthority {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_COMMON_USER("ROLE_COMMON_USER"),
    ROLE_VISITANT_USER("ROLE_VISITANT_USER");

    override fun getAuthority(): String = authority
}
