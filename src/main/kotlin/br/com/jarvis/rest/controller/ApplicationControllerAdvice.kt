package br.com.jarvis.rest.controller

import br.com.jarvis.exception.BusinessRuleException
import br.com.jarvis.exception.ComicBookExistsException
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.rest.ApiErrors
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class ApplicationControllerAdvice {

    @ExceptionHandler(BusinessRuleException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBusinessRuleException(exception: BusinessRuleException): ApiErrors {
        val errorMessage: String = exception.message
        return ApiErrors(errorMessage)
    }

    @ExceptionHandler(ComicBookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleComicBookNotFoundException(exception: ComicBookNotFoundException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(ComicBookExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleComicBookExistsException(exception: ComicBookExistsException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodNotValidException(ex: MethodArgumentNotValidException): ApiErrors {
        val errors = ex.bindingResult.allErrors
            .stream()
            .map { error -> error.defaultMessage }
            .collect(Collectors.toList())
        return ApiErrors(errors)
    }
}