package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.authentication.CreateUserVo
import br.com.mirantebackend.controller.vo.authentication.UserCredentialsVo
import br.com.mirantebackend.model.documents.UserDocument
import br.com.mirantebackend.model.dto.authentication.CreateUserDto
import br.com.mirantebackend.model.dto.authentication.UserCredentialsDto
import br.com.mirantebackend.model.enums.UserRolesEnum

fun CreateUserVo.toCreateUserDto() = CreateUserDto(
    username!!,
    name!!,
    email!!,
    password!!
)

fun UserCredentialsVo.toUserCredentialsDto() = UserCredentialsDto(
    username!!,
    password!!
)

fun CreateUserDto.toUserDocument() = UserDocument(
    username = username,
    name = name,
    email = email,
    password = password,
    userRole = UserRolesEnum.ROLE_COMMON_USER
)
