package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.BookCoverType
import br.com.jarvis.domain.entity.ComicBook
import br.com.jarvis.domain.entity.ComicType
import br.com.jarvis.domain.entity.Volume
import br.com.jarvis.domain.mapper.ComicBookDTOToComicBookLocaleMapper
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ComicBookExistsException
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.service.ComicBookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ComicBookServiceImpl(
    private val repository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository,
    private val volumeRepository: VolumeRepository,
    private val mapper: ComicBookDTOToComicBookLocaleMapper
) : ComicBookService {

    @Transactional
    override fun save(dto: ComicBookDTO) {
        val comicBookLocale = comicBookLocaleRepository.findByName(dto.name).firstOrNull()
        val comicBook = comicBookLocale?.comicBook

        if (comicBook != null) {
            if (comicBookLocale.language == dto.language && comicBookLocale.name == dto.name) {
                throw ComicBookExistsException
            }

            val newComicBookLocale = mapper.mapFrom(dto, comicBook)
            comicBookLocaleRepository.save(newComicBookLocale)
        } else {
            val newComicBook = ComicBook(
                comicType = ComicType.valueOf(dto.comicType),
                hasAnimation = dto.hasAnimation,
                releaseDate = dto.releaseDate,
                completionDate = dto.completionDate,
            )
            repository.save(newComicBook)

            val newComicBookLocale = mapper.mapFrom(dto, newComicBook)
            comicBookLocaleRepository.save(newComicBookLocale)

            val listVolume = dto.volumes?.map { volumeDTO ->
                Volume(
                    releaseYear = volumeDTO.releaseYear,
                    number = volumeDTO.number,
                    description = volumeDTO.description,
                    isbn = volumeDTO.isbn,
                    pages = volumeDTO.pages,
                    bookCoverType = BookCoverType.valueOf(volumeDTO.bookCoverType),
                    locale = newComicBookLocale,
                )
            }
            if (listVolume != null) {
                volumeRepository.saveAll(listVolume)
            }
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