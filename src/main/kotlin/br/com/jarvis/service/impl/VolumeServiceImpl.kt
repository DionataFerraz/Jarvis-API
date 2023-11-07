package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.BookCoverType
import br.com.jarvis.domain.entity.VolumeEntity
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.rest.controller.dto.AnimationDTO
import br.com.jarvis.rest.controller.dto.AuthorDTO
import br.com.jarvis.rest.controller.dto.ComicBookDTO
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
    override fun fetchAllComics(id: Long, language: String?): ComicBookDTO {
        return comicBookRepository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
            val locale = comicBook.locales.first {
                it.language == language
            }

            val authorDTO = comicBook.author.map { authorEntity ->
                AuthorDTO(
                    name = authorEntity.name,
                    synopsis = authorEntity.synopsis,
                    birthday = authorEntity.birthday,
                    imagePath = authorEntity.imagePath,
                )
            }

            val animationDTO = comicBook.animation.map { animationEntity ->
                AnimationDTO(
                    name = animationEntity.name,
                    releaseDate = animationEntity.releaseDate,
                    completionDate = animationEntity.completionDate,
                    episodeQtd = animationEntity.episodeQtd,
                    seasonQtd = animationEntity.seasonQtd,
                    imagePath = animationEntity.imagePath,
                )
            }

            ComicBookDTO(
                id = comicBook.id,
                comicType = comicBook.comicType.name,
                imagePath = comicBook.imagePath,
                hasAnimation = comicBook.hasAnimation,
                releaseDate = comicBook.releaseDate,
                completionDate = comicBook.completionDate,
                name = locale.name,
                description = locale.description,
                language = locale.language,
                authors = authorDTO,
                animations = animationDTO,
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
                            images = volumeEntity.images.map { imageEntity ->
                                ImageDTO(
                                    image = imageEntity.imagePath,
                                    description = imageEntity.description
                                )
                            }
                        )
                    }
            )
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }
}
