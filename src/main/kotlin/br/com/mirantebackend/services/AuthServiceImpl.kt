package br.com.mirantebackend.services

import br.com.mirantebackend.controller.mappers.toUserDocument
import br.com.mirantebackend.model.dto.authentication.CreateUserDto
import br.com.mirantebackend.model.dto.authentication.UserCredentialsDto
import br.com.mirantebackend.repository.UserDocumentRepository
import br.com.mirantebackend.services.interfaces.AuthService
import br.com.mirantebackend.utils.JwtUtils
import mu.KotlinLogging
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwsHeader
import org.springframework.security.oauth2.jwt.JwtClaimsSet
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.JwtEncoderParameters
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class AuthServiceImpl(
    private val userDocumentRepository: UserDocumentRepository,
    private val jwtEncoder: JwtEncoder,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val authenticationManager: AuthenticationManager
): AuthService {

    companion object {
        private val logger = KotlinLogging.logger {}
        private val TOKEN_DURATION: Long = 600000 //TODO REPLACE ADJUST
    }

    override fun login(userCredentials: UserCredentialsDto): String {

        val user = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(userCredentials.username, userCredentials.password)
        ).principal as User

        return JwtUtils.buildJwtToken(user, jwtEncoder, TOKEN_DURATION)
    }

    override fun createCommonUser(createUserDto: CreateUserDto): String {
        createUserDto.toUserDocument()
            .also { it.password = bCryptPasswordEncoder.encode(createUserDto.password) }
            .let { userDocumentRepository.save(it) }
        TODO("Not yet implemented")
    }

    override fun recoverAccess(emailRecovery: String): String {
        TODO("Not yet implemented")
    }
}