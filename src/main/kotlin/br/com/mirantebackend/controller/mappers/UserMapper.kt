package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.authentication.CreateUserVo
import br.com.mirantebackend.controller.vo.authentication.UserCredentialsVo
import br.com.mirantebackend.controller.vo.authentication.UserSessionVo
import br.com.mirantebackend.model.documents.UserDocument
import br.com.mirantebackend.model.dto.authentication.CreateUserDto
import br.com.mirantebackend.model.dto.authentication.UserCredentialsDto
import br.com.mirantebackend.model.dto.authentication.UserSessionDto
import br.com.mirantebackend.model.enums.UserAuthoritiesEnum

fun CreateUserVo.toCreateUserDto() = CreateUserDto(
    username!!,
    nickname!!,
    email!!,
    password!!
)

fun UserCredentialsVo.toUserCredentialsDto() = UserCredentialsDto(
    username!!,
    password!!
)

fun UserSessionDto.toUserSessionVo() = UserSessionVo(
    nickname,
    jwtToken
)

fun CreateUserDto.toUserDocument() = UserDocument(
    username = username,
    nickname = nickname,
    email = email,
    password = password,
    userRole = UserAuthoritiesEnum.COMMON_USER
)