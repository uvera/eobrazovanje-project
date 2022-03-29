package io.uvera.eobrazovanje.error.exception

import io.uvera.eobrazovanje.error.dto.ApiError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import javax.naming.AuthenticationException
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice
class ExceptionHandlers {
    private fun exceptionEntity(ex: Exception, request: HttpServletRequest, status: HttpStatus) =
        ResponseEntity<ApiError>(ApiError.fromException(ex, request, status), status)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun badRequestException(ex: BadRequestException, req: HttpServletRequest) =
        exceptionEntity(ex, req, HttpStatus.BAD_REQUEST)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(ex: NotFoundException, req: HttpServletRequest) =
        exceptionEntity(ex, req, HttpStatus.NOT_FOUND)

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException::class)
    fun usernameNotFoundException(ex: AuthenticationException, req: HttpServletRequest) =
        exceptionEntity(ex, req, HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(WebExchangeBindException::class)
    fun webExchangeBindException(ex: WebExchangeBindException, req: HttpServletRequest) =
        exceptionEntity(ex, req, HttpStatus.BAD_REQUEST)
}
