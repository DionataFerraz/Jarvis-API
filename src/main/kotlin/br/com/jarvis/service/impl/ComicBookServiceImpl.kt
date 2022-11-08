package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.ComicBook
import br.com.jarvis.domain.entity.ComicBookLocale
import br.com.jarvis.domain.entity.ComicType
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.service.ComicBookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ComicBookServiceImpl(
    private val repository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository
) : ComicBookService {

    @Transactional
    override fun save(dto: ComicBookDTO) {
        val comicBook = ComicBook(
            comicType = ComicType.valueOf(dto.comicType),
            hasAnimation = dto.hasAnimation,
        )
        repository.save(comicBook)

        comicBookLocaleRepository.save(
            ComicBookLocale(
                name = dto.name,
                description = dto.description,
                comicBook = comicBook,
                language = dto.language,
                releaseDate = dto.releaseDate,
                completionDate = dto.completionDate,
            )
        )
    }


    @Transactional
    override fun fetchAllComics(language: String?): List<ComicBookDTO> {
        val comicBooks = repository.findAll()
        val locales = comicBookLocaleRepository.findByComicBookIn(comicBooks)

        return comicBooks.map { comicBook ->
            val locale = locales.first {
                it.comicBook == comicBook
            }

            ComicBookDTO(
                comicType = comicBook.comicType.name,
                hasAnimation = comicBook.hasAnimation,
                name = locale.name.orEmpty(),
                description = locale.description.orEmpty(),
                language = locale.language,
                releaseDate = locale.releaseDate,
                completionDate = locale.completionDate,
            )
        }
    }
}