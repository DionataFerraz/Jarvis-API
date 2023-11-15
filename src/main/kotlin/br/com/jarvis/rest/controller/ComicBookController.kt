package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.request.ComicBookRequestDTO
import br.com.jarvis.rest.controller.dto.response.ComicBookResponseDTO
import br.com.jarvis.service.ComicBookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comics")
class ComicBookController(private val service: ComicBookService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody dto: @Valid ComicBookRequestDTO) {
        service.save(dto)
    }

    @GetMapping
    fun fetchComicsByLanguage(
        @RequestHeader("Accept-Language") language: String
    ): List<ComicBookResponseDTO> {
        return service.fetchAllComics(language = language)
    }

    @GetMapping("{id}")
    fun fetchComicByLanguage(
        @PathVariable id: Long,
        @RequestHeader("Accept-Language") language: String
    ): ComicBookResponseDTO {
        return service.fetchComic(id = id, language = language)
    }

}



