package by.next.way.spring.data.handlers

import org.springframework.data.rest.core.RepositoryConstraintViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(RepositoryConstraintViolationException::class)
    fun handleAccessDeniedException(ex: Exception, request: WebRequest): ResponseEntity<out Any> {
        val nevEx = ex as RepositoryConstraintViolationException
        val errors = nevEx.errors.allErrors.map { it.toString() }.joinToString { "$it\n" }
        return ResponseEntity(errors, HttpHeaders(), HttpStatus.NOT_ACCEPTABLE)
    }

}