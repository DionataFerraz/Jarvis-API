package br.com.jarvis.service.userinteraction

import br.com.jarvis.domain.entity.FavoriteComicBookEntity
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.FavoriteComicBookRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.exception.FavoriteDuplicatedException
import br.com.jarvis.exception.FavoriteErrorException
import br.com.jarvis.exception.FavoriteNotFoundException
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class FavoriteComicBookServiceImpl(
    private val repository: FavoriteComicBookRepository,
    private val comicBookRepository: ComicBookRepository,
    private val userService: UserService
) : FavoriteComicBookService {

    @Transactional
    override fun favorite(comicBookId: Long) {
        val user = userService.retrieveUser()

        comicBookRepository.findById(comicBookId).map { comicBook ->
            if (repository.isFavorite(comicBookId, user.id)) {
                throw FavoriteDuplicatedException
            } else {
                repository.save(
                    FavoriteComicBookEntity(
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
        try {
            val userId = userService.retrieveUser().id

            if (repository.isFavorite(comicBookId, userId)) {
                val entity = repository.findByComicBookIdAndUserId(comicBookId = comicBookId, userId = userId)
                repository.delete(entity)
            } else {
                throw FavoriteNotFoundException
            }
        } catch (exception: Exception) {
            throw FavoriteErrorException(exception.message.orEmpty())
        }
    }
}
