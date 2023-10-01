package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.rest.controller.dto.VolumeDTO
import br.com.jarvis.service.ComicBookService
import br.com.jarvis.service.VolumeService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/volume")
class VolumeController(private val service: VolumeService) {

    @PostMapping("{id}")
    fun saveComicsById(
        @PathVariable id: Long,
        @RequestBody dto: @Valid List<VolumeDTO>
    ) {
        service.saveById(id = id, volumes = dto)
    }

    @GetMapping("{id}")
    fun fetchComicsByLanguage(
        @PathVariable id: Long,
        @RequestHeader("Accept-Language") language: String?
    ): ComicBookDTO {
        return service.fetchAllComics(id = id, language = language)
    }

    @GetMapping("/authenticate")
    fun testAuthenticate(
    ): List<String> {
        return listOf()
    }

}



