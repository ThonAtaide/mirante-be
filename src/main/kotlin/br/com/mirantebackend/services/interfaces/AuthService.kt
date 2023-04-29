package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.model.dto.authentication.CreateUserDto
import br.com.mirantebackend.model.dto.authentication.UserCredentialsDto

interface AuthService {

    fun login(userCredentials: UserCredentialsDto): String

    fun createCommonUser(createUserDto: CreateUserDto): String

    fun recoverAccess(emailRecovery: String): String
}
