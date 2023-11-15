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
import br.com.jarvis.domain.repository.TagRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ComicBookException
import br.com.jarvis.exception.ComicBookExistsException
import br.com.jarvis.exception.ComicBookNeedsImageTypeException
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.rest.controller.dto.request.ComicBookRequestDTO
import br.com.jarvis.rest.controller.dto.response.AnimationResponseDTO
import br.com.jarvis.rest.controller.dto.response.AuthorResponseDTO
import br.com.jarvis.rest.controller.dto.response.ComicBookResponseDTO
import br.com.jarvis.rest.controller.dto.response.ImageResponseDTO
import br.com.jarvis.rest.controller.dto.response.TagsResponseDTO
import br.com.jarvis.rest.controller.dto.response.VolumeResponseDTO
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
    private val tagRepository: TagRepository,
    private val mapper: ComicBookDTOToComicBookLocaleMapper
) : ComicBookService {

    @Transactional
    override fun save(dto: ComicBookRequestDTO) {
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
                tags = tagRepository.findAllById(dto.tags.asIterable()).toSet()
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
    override fun fetchAllComics(language: String): List<ComicBookResponseDTO> {
        try {
            val comicBooks = repository.findByLanguageComicBookLocaleIn(language)
            val locales = comicBookLocaleRepository.findByComicBookIn(comicBooks)

            return comicBooks.map { comicBook ->
                val locale = locales.first {
                    it.comicBook == comicBook
                }

                ComicBookResponseDTO(
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
    override fun fetchComic(id: Long, language: String): ComicBookResponseDTO {
        return repository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
            val locale = comicBook.locales.first {
                it.language == language
            }

            val authorResponseDTO = comicBook.author.map { authorEntity ->
                AuthorResponseDTO(
                    name = authorEntity.name,
                    synopsis = authorEntity.synopsis,
                    birthday = authorEntity.birthday,
                    imagePath = authorEntity.imagePath,
                )
            }

            val animationResponseDTO = comicBook.animation.map { animationEntity ->
                AnimationResponseDTO(
                    name = animationEntity.name,
                    releaseDate = animationEntity.releaseDate,
                    completionDate = animationEntity.completionDate,
                    episodeQtd = animationEntity.episodeQtd,
                    seasonQtd = animationEntity.seasonQtd,
                    imagePath = animationEntity.imagePath,
                )
            }

            ComicBookResponseDTO(
                id = comicBook.id,
                comicType = comicBook.comicType.name,
                imagePath = comicBook.imagePath,
                hasAnimation = comicBook.hasAnimation,
                releaseDate = comicBook.releaseDate,
                completionDate = comicBook.completionDate,
                name = locale.name,
                description = locale.description,
                language = locale.language,
                authors = authorResponseDTO,
                animations = animationResponseDTO,
                volumes = locale.volumes
                    .sortedBy { it.number }
                    .map { volumeEntity ->
                        VolumeResponseDTO(
                            id = volumeEntity.id,
                            releaseYear = volumeEntity.releaseYear,
                            number = volumeEntity.number,
                            description = volumeEntity.description,
                            isbn = volumeEntity.isbn,
                            pages = volumeEntity.pages,
                            bookCoverType = volumeEntity.bookCoverType.name,
                            images = volumeEntity.images.map { imageEntity ->
                                ImageResponseDTO(
                                    image = imageEntity.imagePath,
                                    description = imageEntity.description
                                )
                            }
                        )
                    },
                tags = comicBook.tags.map { tagEntity ->
                    TagsResponseDTO(
                        id = tagEntity.id,
                        name = tagEntity.name,
                    )
                }
            )
        }.orElseThrow {
            ComicBookNotFoundException
        }
    }
}