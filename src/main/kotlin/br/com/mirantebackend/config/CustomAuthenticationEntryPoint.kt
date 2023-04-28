package br.com.mirantebackend.config

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import com.fasterxml.jackson.databind.ObjectMapper

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.io.OutputStream


@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val re = hashMapOf<Any, Any>()
        re["Http-status"] = HttpStatus.UNAUTHORIZED.toString()
        re["Message"] = "Authentication failed"

        response!!.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val responseStream: OutputStream = response.outputStream
        val mapper = ObjectMapper()
        mapper.writeValue(responseStream, re)
        responseStream.flush()
    }
}