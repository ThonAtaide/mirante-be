package br.com.mirantebackend.controller

import br.com.mirantebackend.controller.interfaces.AuthenticationController
import br.com.mirantebackend.controller.mappers.toCreateUserDto
import br.com.mirantebackend.controller.mappers.toUserCredentialsDto
import br.com.mirantebackend.controller.vo.authentication.CreateUserVo
import br.com.mirantebackend.controller.vo.authentication.RecoverUserCredentialsVo
import br.com.mirantebackend.controller.vo.authentication.UserCredentialsVo
import br.com.mirantebackend.services.interfaces.AuthService
import br.com.mirantebackend.utils.JwtUtils.Companion.BEARER_PREFIX
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthenticationControllerImpl(
    private val authService: AuthService
) : AuthenticationController {

    override fun login(userCredentials: UserCredentialsVo): ResponseEntity<Void> {
        return try {
            userCredentials.toUserCredentialsDto()
                .let { authService.login(it) }
                .let { ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "$BEARER_PREFIX $it").build() }
        } catch (err: BadCredentialsException) {
            ResponseEntity.status(UNAUTHORIZED).build()
        }
    }

    override fun createCommonUser(createUserVo: CreateUserVo): String =
        createUserVo.toCreateUserDto()
            .let { authService.createCommonUser(it) }

    override fun recoverAccess(recoverUserCredentialsVo: RecoverUserCredentialsVo): String =
        recoverUserCredentialsVo.email!!
            .let { authService.recoverAccess(it) }
}
