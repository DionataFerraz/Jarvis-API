package br.com.jarvis.service.home

import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.FavoriteComicBookRepository
import br.com.jarvis.domain.repository.ReviewComicBookRepository
import br.com.jarvis.exception.ComicBookException
import br.com.jarvis.rest.controller.dto.response.ComicBookResponseDTO
import br.com.jarvis.rest.controller.dto.response.ImageResponseDTO
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class HomeServiceImpl(
    private val repository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository,
    private val reviewComicBookRepository: ReviewComicBookRepository,
    private val favoriteBookRepository: FavoriteComicBookRepository,
    private val userService: UserService,
) : HomeService {

    @Transactional
    override fun fetchAllComics(language: String): List<ComicBookResponseDTO> {
        try {
            val comicBooks = repository.findComicBookByLanguage(language)
            val locales = comicBookLocaleRepository.findByComicBookIn(comicBooks)
            val userId = userService.retrieveUser().id

            return comicBooks.map { comicBook ->
                val locale = locales.first {
                    it.comicBook == comicBook
                }

                val userRating = reviewComicBookRepository.findAverageReview(comicBook.id)
                val isFavorite = favoriteBookRepository.isFavorite(
                    comicBookId = comicBook.id,
                    userId = userId
                )

                ComicBookResponseDTO(
                    id = comicBook.id,
                    comicType = comicBook.comicType.name,
                    imagePath = comicBook.imagePath,
                    coversImage = comicBook.coversImage.map { imageEntity ->
                        ImageResponseDTO(
                            image = imageEntity.imagePath,
                            description = imageEntity.description
                        )
                    },
                    hasAnimation = comicBook.hasAnimation,
                    releaseDate = comicBook.releaseDate,
                    completionDate = comicBook.completionDate,
                    userRating = userRating,
                    isFavorite = isFavorite,
                    name = locale.name,
                    description = locale.description,
                )
            }
        } catch (ex: Exception) {
            throw ComicBookException(ex.message ?: "Error fetch all comics")
        }
    }
}