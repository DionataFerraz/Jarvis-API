package br.com.jarvis.domain.mapper

import br.com.jarvis.domain.entity.ComicBookEntity
import br.com.jarvis.domain.entity.ComicBookLocale
import br.com.jarvis.rest.controller.dto.ComicBookDTO
import org.springframework.stereotype.Component

@Component
class ComicBookDTOToComicBookLocaleDefaultMapper : ComicBookDTOToComicBookLocaleMapper {

    override fun mapFrom(dto: ComicBookDTO, comicBook: ComicBookEntity): ComicBookLocale {
        return ComicBookLocale(
            name = dto.name,
            comicBook = comicBook,
            description = dto.description,
            language = dto.language,
        )
    }
}