package br.com.jarvis.rest.controller

import br.com.jarvis.exception.AuthRuleException
import br.com.jarvis.exception.BusinessRuleException
import br.com.jarvis.exception.ComicBookException
import br.com.jarvis.exception.ComicBookExistsException
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.exception.FavoriteDuplicatedException
import br.com.jarvis.exception.FavoriteNotFoundException
import br.com.jarvis.exception.ReadVolumeDuplicatedException
import br.com.jarvis.exception.ReadVolumeErrorException
import br.com.jarvis.exception.ReadVolumeNotFoundException
import br.com.jarvis.exception.ReviewDuplicatedException
import br.com.jarvis.exception.ReviewUpdateException
import br.com.jarvis.exception.StorageVolumeDuplicatedException
import br.com.jarvis.exception.StorageVolumeErrorException
import br.com.jarvis.exception.StorageVolumeNotFoundException
import br.com.jarvis.exception.TagDuplicatedException
import br.com.jarvis.exception.TagErrorException
import br.com.jarvis.exception.TagNotFoundException
import br.com.jarvis.exception.TagUserInterestDuplicatedException
import br.com.jarvis.exception.UserRuleException
import br.com.jarvis.exception.VolumeNotFoundException
import br.com.jarvis.rest.ApiErrors
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.util.stream.Collectors

@RestControllerAdvice
class ApplicationControllerAdvice {

    @ExceptionHandler(AuthRuleException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun handleAuthRuleException(exception: AuthRuleException): ApiErrors {
        val errorMessage: String = exception.message
        return ApiErrors(errorMessage)
    }

    @ExceptionHandler(UserRuleException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserRuleException(exception: UserRuleException): ApiErrors {
        val errorMessage: String = exception.message
        return ApiErrors(errorMessage)
    }

    @ExceptionHandler(BusinessRuleException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBusinessRuleException(exception: BusinessRuleException): ApiErrors {
        val errorMessage: String = exception.message
        return ApiErrors(errorMessage)
    }

    @ExceptionHandler(ComicBookException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleComicBookException(exception: ComicBookException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
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

    @ExceptionHandler(VolumeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleVolumeNotFoundException(exception: VolumeNotFoundException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(FavoriteDuplicatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleUserComicBookInteractionDuplicatedException(exception: FavoriteDuplicatedException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }
    @ExceptionHandler(FavoriteNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleUserComicBookInteractionNotFoundException(exception: FavoriteNotFoundException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(ReviewDuplicatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleReviewDuplicatedException(exception: ReviewDuplicatedException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(ReviewUpdateException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleReviewUpdateException(exception: ReviewUpdateException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(ReadVolumeDuplicatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleReadVolumeDuplicatedException(exception: ReadVolumeDuplicatedException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(ReadVolumeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleReadVolumeNotFoundException(exception: ReadVolumeNotFoundException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(ReadVolumeErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleReadVolumeErrorException(exception: ReadVolumeErrorException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(StorageVolumeDuplicatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleStorageVolumeDuplicatedException(exception: StorageVolumeDuplicatedException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(StorageVolumeNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleStorageVolumeNotFoundException(exception: StorageVolumeNotFoundException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(StorageVolumeErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleStorageVolumeErrorException(exception: StorageVolumeErrorException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(TagUserInterestDuplicatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleTagUserInterestDuplicatedException(exception: TagUserInterestDuplicatedException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(TagDuplicatedException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleTagDuplicatedException(exception: TagDuplicatedException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(TagNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleTagNotFoundException(exception: TagNotFoundException): ApiErrors {
        return ApiErrors(exception.message.orEmpty())
    }

    @ExceptionHandler(TagErrorException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleTagErrorException(exception: TagErrorException): ApiErrors {
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