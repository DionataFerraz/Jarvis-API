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
import br.com.jarvis.exception.ComicBookNotFoundException
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.rest.controller.dto.VolumeDTO
import br.com.jarvis.service.ComicBookService
import br.com.jarvis.service.VolumeService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class VolumeServiceImpl(
    private val repository: ComicBookRepository,
    private val comicBookLocaleRepository: ComicBookLocaleRepository,
    private val volumeRepository: VolumeRepository,
    private val mapper: ComicBookDTOToComicBookLocaleMapper
) : VolumeService {

    @Transactional
    override fun fetchAllComics(id: Long, language: String?): ComicBookDTO {
        /*return repository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
//            val locale = comicBookLocaleRepository.findByComicBookIn(comicBook)
            val locales = comicBookLocaleRepository.findByComicBookIn(listOf(comicBook))//TA ERRADO ESSE LIST OF
            val locale = comicBook.locales.first()

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
        }*/

        return repository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
//            val locale = comicBookLocaleRepository.findByComicBookIn(comicBook)
            val locale = comicBookLocaleRepository.findByComicBookIn(listOf(comicBook).toSet())//TA ERRADO ESSE LIST OF
            /* val locale = locales.first {
                 it.comicBook == comicBook
             }*/
            if (locale.isPresent){
                ComicBookDTO(
                    comicType = comicBook.comicType.name,
                    hasAnimation = comicBook.hasAnimation,
                    releaseDate = comicBook.releaseDate,
                    completionDate = comicBook.completionDate,
                    name = locale.get().name,
                    description = locale.get().description,
                    language = locale.get().language,
                    volumes = locale.get().volumes
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
            }
            else{
                throw  ComicBookNotFoundException
            }

        }.orElseThrow {
            ComicBookNotFoundException
        }
    }
}
// fun findByComicBookIn(comicBook: List<ComicBook>): Optional<ComicBookLocale>
/*
return repository.findByIdComicBookLocaleIn(id, language).map { comicBook ->
//            val locale = comicBookLocaleRepository.findByComicBookIn(comicBook)
            val locale = comicBookLocaleRepository.findByComicBookIn(listOf(comicBook))//TA ERRADO ESSE LIST OF
           /* val locale = locales.first {
                it.comicBook == comicBook
            }*/
            if (locale.isPresent){
                ComicBookDTO(
                    comicType = comicBook.comicType.name,
                    hasAnimation = comicBook.hasAnimation,
                    releaseDate = comicBook.releaseDate,
                    completionDate = comicBook.completionDate,
                    name = locale.get().name,
                    description = locale.get().description,
                    language = locale.get().language,
                    volumes = locale.get().volumes
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
            }
            else{
                throw  ComicBookNotFoundException
            }

        }.orElseThrow {
            ComicBookNotFoundException
        }

*/