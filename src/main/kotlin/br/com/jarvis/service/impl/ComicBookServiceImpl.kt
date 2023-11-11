package br.com.jarvis.service.impl

import br.com.jarvis.domain.entity.AnimationEntity
import br.com.jarvis.domain.entity.AuthorEntity
import br.com.jarvis.domain.entity.BookCoverType
import br.com.jarvis.domain.entity.ComicBookEntity
import br.com.jarvis.domain.entity.ComicType
import br.com.jarvis.domain.entity.ImageEntity
import br.com.jarvis.domain.entity.VolumeEntity
import br.com.jarvis.domain.mapper.ComicBookDTOToComicBookLocaleMapper
import br.com.jarvis.domain.repository.AnimationRepository
import br.com.jarvis.domain.repository.AuthorRepository
import br.com.jarvis.domain.repository.ComicBookLocaleRepository
import br.com.jarvis.domain.repository.ComicBookRepository
import br.com.jarvis.domain.repository.ImageRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ComicBookException
import br.com.jarvis.exception.ComicBookExistsException
import br.com.jarvis.exception.ComicBookNeedsImageTypeException
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.rest.controller.dto.AnimationDTO
import br.com.jarvis.rest.controller.dto.AuthorDTO
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.rest.controller.dto.ImageDTO
import br.com.jarvis.rest.controller.dto.VolumeDTO
import br.com.jarvis.service.ComicBookService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ComicBookServiceImpl(
    private val repository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository,
    private val volumeRepository: VolumeRepository,
    private val imageRepository: ImageRepository,
    private val authorRepository: AuthorRepository,
    private val animationRepository: AnimationRepository,
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
            val newComicBook = ComicBookEntity(
                comicType = ComicType.valueOf(dto.comicType),
                imagePath = dto.imagePath,
                hasAnimation = dto.hasAnimation,
                releaseDate = dto.releaseDate,
                completionDate = dto.completionDate,
            )
            repository.save(newComicBook)

            val newComicBookLocale = mapper.mapFrom(dto, newComicBook)
            comicBookLocaleRepository.save(newComicBookLocale)

            val authors = dto.authors?.map { author ->
                AuthorEntity(
                    name = author.name,
                    birthday = author.birthday,
                    synopsis = author.synopsis,
                    imagePath = author.imagePath,
                    comicBook = newComicBook,
                )
            }

            val animations = dto.animations?.map { animation ->
                AnimationEntity(
                    name = animation.name,
                    releaseDate = animation.releaseDate,
                    completionDate = animation.completionDate,
                    episodeQtd = animation.episodeQtd,
                    seasonQtd = animation.seasonQtd,
                    imagePath = animation.imagePath,
                    comicBook = newComicBook,
                )
            }

            val listVolume = dto.volumes?.map { volumeDTO ->
                val newVolume = VolumeEntity(
                    releaseYear = volumeDTO.releaseYear,
                    number = volumeDTO.number,
                    description = volumeDTO.description,
                    isbn = volumeDTO.isbn,
                    pages = volumeDTO.pages,
                    bookCoverType = BookCoverType.valueOf(volumeDTO.bookCoverType),
                    locale = newComicBookLocale,
                )

                imageRepository.saveAll(
                    volumeDTO.images?.map {
                        ImageEntity(
                            imagePath = it.image,
                            description = it.description,
                            volume = newVolume
                        )
                    } ?: listOf(
                        if (dto.imageType == null) {
                            throw ComicBookNeedsImageTypeException
                        } else {
                            ImageEntity(
                                imagePath = dto.name
                                    .lowercase()
                                    .replace(" ", "")
                                    .plus("_${volumeDTO.number}")
                                    .plus(dto.imageType),
                                volume = newVolume
                            )
                        }
                    )
                )

                newVolume
            }

            if (listVolume != null) {
                volumeRepository.saveAll(listVolume)
            }

            if (authors != null) {
                authorRepository.saveAll(authors)
            }

            if (animations != null) {
                animationRepository.saveAll(animations)
            }
        }
    }

    @Transactional
    override fun fetchAllComics(language: String): List<ComicBookDTO> {
        try {
            val comicBooks = repository.findByLanguageComicBookLocaleIn(language)
            val locales = comicBookLocaleRepository.findByComicBookIn(comicBooks)

            return comicBooks.map { comicBook ->
                val locale = locales.first {
                    it.comicBook == comicBook
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
                )
            }
        } catch (ex: Exception) {
            throw ComicBookException(ex.message ?: "Error fetch all comics")
        }
    }

    @Transactional
    override fun fetchComic(id: Long, language: String): ComicBookDTO {
        return repository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
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
            )
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }
}