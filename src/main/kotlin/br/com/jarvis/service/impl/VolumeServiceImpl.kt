package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.BookCoverType
import br.com.jarvis.domain.entity.Volume
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.rest.controller.dto.VolumeDTO
import br.com.jarvis.service.VolumeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class VolumeServiceImpl(
    private val repository: VolumeRepository,
    private val comicBookRepository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository,
) : VolumeService {

    @Transactional
    override fun saveById(id: Long, volumes: List<VolumeDTO>?) {
        comicBookLocaleRepository.findById(id).map { comicBookLocale ->
            volumes?.map { volumeDTO ->
                Volume(
                    releaseYear = volumeDTO.releaseYear,
                    number = volumeDTO.number,
                    description = volumeDTO.description,
                    isbn = volumeDTO.isbn,
                    pages = volumeDTO.pages,
                    bookCoverType = BookCoverType.valueOf(volumeDTO.bookCoverType),
                    locale = comicBookLocale,
                )
            }?.let { listVolume ->
                repository.saveAll(listVolume)
            }
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }

    @Transactional
    override fun fetchAllComics(id: Long, language: String?): ComicBookDTO {
        return comicBookRepository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
            val locale = comicBook.locales.first {
                it.language == language
            }

            ComicBookDTO(
                comicType = comicBook.comicType.name,
                hasAnimation = comicBook.hasAnimation,
                releaseDate = comicBook.releaseDate,
                completionDate = comicBook.completionDate,
                name = locale.name,
                description = locale.description,
                language = locale.language,
                volumes = locale.volumes
                    .sortedBy { it.number }
                    .map { volumeEntity ->
                        VolumeDTO(
                            releaseYear = volumeEntity.releaseYear,
                            number = volumeEntity.number,
                            description = volumeEntity.description,
                            isbn = volumeEntity.isbn,
                            pages = volumeEntity.pages,
                            bookCoverType = volumeEntity.bookCoverType.name,
                        )
                    }
            )
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }
}