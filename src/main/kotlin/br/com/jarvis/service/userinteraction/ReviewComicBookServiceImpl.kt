package br.com.jarvis.service.userinteraction

import br.com.jarvis.domain.entity.ReviewComicBookEntity
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.ReviewComicBookRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.exception.ReviewDuplicatedException
import br.com.jarvis.exception.ReviewUpdateException
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ReviewComicBookServiceImpl(
    private val repository: ReviewComicBookRepository,
    private val comicBookRepository: ComicBookRepository,
    private val userService: UserService
) : ReviewComicBookService {

    @Transactional
    override fun review(comicBookId: Long, review: Double) {
        val user = userService.retrieveUser()

        comicBookRepository.findById(comicBookId).map { comicBook ->
            if (repository.isDuplicate(comicBookId, user.id)) {
                throw ReviewDuplicatedException
            } else {
                repository.save(
                    ReviewComicBookEntity(
                        comicBookEntity = comicBook,
                        review = review,
                        userEntity = user
                    )
                )
            }
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }

    @Transactional
    override fun updateReview(comicBookId: Long, review: Double) {
        try {
            val userId = userService.retrieveUser().id

            val entity = repository.findByComicBookIdAndUserId(comicBookId = comicBookId, userId = userId)
            repository.save(
                ReviewComicBookEntity(
                    id = entity.id,
                    review = review,
                    comicBookEntity = entity.comicBookEntity,
                    userEntity = entity.userEntity
                )
            )
        } catch (exception: Exception) {
            throw ReviewUpdateException(exception.message.orEmpty())
        }
    }
}
