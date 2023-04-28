package br.com.mirantebackend.controller.interfaces

import br.com.mirantebackend.controller.vo.authentication.CreateUserVo
import br.com.mirantebackend.controller.vo.authentication.RecoverUserCredentialsVo
import br.com.mirantebackend.controller.vo.authentication.UserCredentialsVo
import br.com.mirantebackend.controller.vo.authentication.UserSessionVo
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/user")
@Tag(name = "AuthenticationController", description = "Operations about user credentials")
interface AuthenticationController {

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
        "/login",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [
            MediaType.APPLICATION_JSON_VALUE
        ]
    )
    fun login(@Validated @RequestBody userCredentials: UserCredentialsVo): ResponseEntity<Void>

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
        "/register",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [
            MediaType.APPLICATION_JSON_VALUE
        ]
    )
    fun createCommonUser(@Validated @RequestBody createUserVo: CreateUserVo): String

    @ResponseStatus(HttpStatus.OK)
    @PostMapping(
        "/recoverAccess",
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [
            MediaType.APPLICATION_JSON_VALUE
        ]
    )
    fun recoverAccess(@Validated @RequestBody recoverUserCredentialsVo: RecoverUserCredentialsVo): String


}
