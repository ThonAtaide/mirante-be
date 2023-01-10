package br.com.mirantebackend.controller

import br.com.mirantebackend.exceptions.ChampionshipNotFoundException
import br.com.mirantebackend.exceptions.MatchNotFoundException
import br.com.mirantebackend.exceptions.NewsNotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import javax.validation.ConstraintViolationException

@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    companion object {
        const val ERROR_MESSAGES = "errorMessages"
        const val STATUS_CODE = "statusCode"
        const val TIMESTAMP = "timestamp"
        const val ERROR = "error"
    }

    //TODO("ALTERAR ")
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error { "Handling MethodArgumentNotValidException " }
        val errorMessages = ex.fieldErrors.stream().map { it.defaultMessage }.distinct().toList()
        return buildResponseError(
            errorMessages,
            ex::class.qualifiedName.toString(),
            HttpStatus.BAD_REQUEST.value()
        ).let { ResponseEntity.badRequest().body(it) }
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    protected fun handleConstraintViolation(
        constraintViolationException: ConstraintViolationException,
        webRequest: WebRequest
    ): ResponseEntity<Map<String, Any>> {
        constraintViolationException.printStackTrace()

        val errorMessageList =
            constraintViolationException.constraintViolations.toSet().distinctBy { it.propertyPath }.map { it.message }

        return buildResponseError(
            errorMessageList,
            constraintViolationException::class.qualifiedName.toString(),
            HttpStatus.BAD_REQUEST.value()
        ).let { ResponseEntity.badRequest().body(it) }
    }

    @ExceptionHandler(value = [RuntimeException::class])
    protected fun handleRuntimeException(
        runtimeException: RuntimeException,
        webRequest: WebRequest
    ): ResponseEntity<Map<String, Any>> {
        runtimeException.printStackTrace()
        val errorMessage = runtimeException.message

        return buildResponseError(
            listOf(errorMessage),
            runtimeException::class.qualifiedName.toString(),
            HttpStatus.BAD_REQUEST.value()
        ).let { ResponseEntity.badRequest().body(it) }
    }

    @ExceptionHandler(
        value = [ChampionshipNotFoundException::class,
            MatchNotFoundException::class, NewsNotFoundException::class]
    )
    protected fun handleNotFoundExceptions(
        runtimeException: RuntimeException,
        webRequest: WebRequest
    ): ResponseEntity<Map<String, Any>> {
        runtimeException.printStackTrace()
        val errorMessage = runtimeException.message

        return buildResponseError(
            listOf(errorMessage),
            runtimeException::class.qualifiedName.toString(),
            HttpStatus.NOT_FOUND.value()
        ).let { ResponseEntity.badRequest().body(it) }
    }

    protected fun buildResponseError(
        errorMessages: Collection<String?>,
        throwable: String,
        statusCode: Int
    ): Map<String, Any> =
        hashMapOf<String, Any>()
            .apply { put(TIMESTAMP, LocalDateTime.now(UTC)) }
            .apply { put(ERROR_MESSAGES, errorMessages) }
            .apply { put(STATUS_CODE, statusCode) }
            .apply { put(ERROR, throwable) }

}