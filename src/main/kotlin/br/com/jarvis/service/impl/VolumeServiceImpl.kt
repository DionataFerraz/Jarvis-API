package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.BookCoverType
import br.com.jarvis.domain.entity.VolumeEntity
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.exception.VolumeNotFoundException
import br.com.jarvis.rest.controller.dto.ImageDTO
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
                VolumeEntity(
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
    override fun fetchVolume(id: Long, language: String): VolumeDTO {
        return repository.findById(id).map { volumeEntity ->
            VolumeDTO(
                id = volumeEntity.id,
                releaseYear = volumeEntity.releaseYear,
                number = volumeEntity.number,
                description = volumeEntity.description,
                isbn = volumeEntity.isbn,
                pages = volumeEntity.pages,
                bookCoverType = volumeEntity.bookCoverType.name,
                images = volumeEntity.images.map { imageEntity ->
                    ImageDTO(
                        image = imageEntity.imagePath,
                        description = imageEntity.description
                    )
                }
            )
        }.orElseThrow {
            VolumeNotFoundException
        }
    }

    @Transactional
    override fun fetchAllVolumes(id: Long, language: String): List<VolumeDTO> {
        return comicBookRepository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
            val locale = comicBook.locales.first {
                it.language == language
            }

            locale.volumes
                .sortedBy { it.number }
                .map { volumeEntity ->
                    VolumeDTO(
                        id = volumeEntity.id,
                        releaseYear = volumeEntity.releaseYear,
                        number = volumeEntity.number,
                        description = volumeEntity.description,
                        isbn = volumeEntity.isbn,
                        pages = volumeEntity.pages,
                        bookCoverType = volumeEntity.bookCoverType.name,
                        images = volumeEntity.images.map { imageEntity ->
                            ImageDTO(
                                image = imageEntity.imagePath,
                                description = imageEntity.description
                            )
                        }
                    )
                }
        }.orElseThrow {
            VolumeNotFoundException
        }
    }
}
