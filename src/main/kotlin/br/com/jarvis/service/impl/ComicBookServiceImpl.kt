package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.ComicBook
import br.com.jarvis.domain.entity.ComicBookLocale
import br.com.jarvis.domain.entity.ComicType
import br.com.jarvis.domain.mapper.ComicBookDTOToComicBookLocaleMapper
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.service.ComicBookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ComicBookServiceImpl(
    private val repository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository,
    private val mapper: ComicBookDTOToComicBookLocaleMapper
) : ComicBookService {

    @Transactional
    override fun save(dto: ComicBookDTO) {
        val comicBook = comicBookLocaleRepository.findByName(dto.name).firstOrNull()?.comicBook

        if (comicBook != null) {
            comicBookLocaleRepository.save(
                mapper.mapFrom(dto, comicBook)
            )
        } else {
            val newComicBook = ComicBook(
                comicType = ComicType.valueOf(dto.comicType),
                hasAnimation = dto.hasAnimation,
                releaseDate = dto.releaseDate,
                completionDate = dto.completionDate,
            )
            repository.save(newComicBook)

            comicBookLocaleRepository.save(
                mapper.mapFrom(dto, newComicBook)
            )
        }
    }


    @Transactional
    override fun fetchAllComics(language: String?): List<ComicBookDTO> {
        val comicBooks = repository.findByLanguageComicBookLocaleIn(language)
        val locales = comicBookLocaleRepository.findByComicBookIn(comicBooks)

        return comicBooks.map { comicBook ->
            val locale = locales.first {
                it.comicBook == comicBook
            }

            ComicBookDTO(
                comicType = comicBook.comicType.name,
                hasAnimation = comicBook.hasAnimation,
                releaseDate = comicBook.releaseDate,
                completionDate = comicBook.completionDate,
                name = locale.name,
                description = locale.description,
                language = locale.language,
            )
        }
    }
}