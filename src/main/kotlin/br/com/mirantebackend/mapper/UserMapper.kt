package br.com.mirantebackend.mapper

import br.com.mirantebackend.model.documents.UserDocument
import br.com.mirantebackend.model.dto.authentication.UserDetailsDto

fun UserDocument.toUserDetailsDto() = UserDetailsDto(
    id,
    nickname,
    username,
    email,
    password,
    userRole,
    isEnable,
    createdAt!!
)