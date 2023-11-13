package br.com.jarvis.service.userinteraction

import br.com.jarvis.domain.entity.UserComicBookInteractionEntity
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.UseComicBookInteractionRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.exception.UserComicBookInteractionDuplicatedException
import br.com.jarvis.exception.UserComicBookInteractionNotFoundException
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class UserComicBookInteractionServiceImpl(
    private val repository: UseComicBookInteractionRepository,
    private val comicBookRepository: ComicBookRepository,
    private val userService: UserService
) : UserComicBookInteractionService {

    @Transactional
    override fun favorite(comicBookId: Long) {
        val user = userService.retrieveUser()

        comicBookRepository.findById(comicBookId).map { comicBook ->
            if (repository.isDuplicate(comicBookId, user.id)) {
                throw UserComicBookInteractionDuplicatedException
            } else {
                repository.save(
                    UserComicBookInteractionEntity(
                        comicBookEntity = comicBook,
                        isFavorite = true,
                        userEntity = user
                    )
                )
            }
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }

    @Transactional
    override fun unFavorite(comicBookId: Long) {
        val userId = userService.retrieveUser().id

        if (repository.isDuplicate(comicBookId, userId)) {
            val entity = repository.findByComicBookIdAndUserId(comicBookId = comicBookId, userId = userId)
            repository.delete(entity)
        } else {
            throw UserComicBookInteractionNotFoundException
        }
    }

    @Transactional
    override fun review(comicBookId: Long, review: Double, userId: Long) {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun readAll(comicBookId: Long, isReadAll: Boolean, userId: Long) {
        TODO("Not yet implemented")
    }

    @Transactional
    override fun hasAllVolume(comicBookId: Long, isReadAll: Boolean, userId: Long) {
        TODO("Not yet implemented")
    }
}
