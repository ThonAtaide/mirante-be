package br.com.mirantebackend.model.enums

import org.springframework.security.core.GrantedAuthority

enum class UserAuthoritiesEnum(private val authority: String) : GrantedAuthority {
    ADMIN("ADMIN"),
    COMMON_USER("COMMON_USER"),
    VISITANT_USER("VISITANT_USER");

    override fun getAuthority(): String = authority
}