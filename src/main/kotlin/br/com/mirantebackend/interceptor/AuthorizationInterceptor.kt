package br.com.mirantebackend.interceptor

import br.com.mirantebackend.services.interfaces.AuthService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

//@Component
class AuthorizationInterceptor(
    private val authService: AuthService
): HandlerInterceptor {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "Bearer "
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {

        val requestMethod = request.method
        val requestURI = request.requestURI
        val jwtToken = request.getHeader(AUTHORIZATION_HEADER).substringAfter(BEARER)

//        val authorized = authService.authorize(jwtToken, requestURI, requestMethod)
//        if (!authorized) {
//            response.status = HttpStatus.UNAUTHORIZED.value()
//        }
//        return authorized
        TODO("DSJFD")
    }


    private fun isRequestMethodPostOrPut(requestMethod: String) =
         requestMethod == RequestMethod.POST.name || requestMethod == RequestMethod.PUT.name

}