package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.response.ComicBookResponseDTO
import br.com.jarvis.service.home.HomeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/home")
class HomeController(private val service: HomeService) {

    @GetMapping
    fun fetchComicsByLanguage(
        @RequestHeader("Accept-Language") language: String
    ): List<ComicBookResponseDTO> {
        return service.fetchAllComics(language = language)
    }

}
