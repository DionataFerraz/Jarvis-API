package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.ComicBookDTO
import br.com.jarvis.service.ComicBookService
import org.springframework.http.HttpStatus
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/comics")
@PreAuthorize("#oauth2.hasScope('app')")//comentei isso aqui
//@Secured("#oauth2.hasScope('app')")//comentei isso aqui
//@Secured("app")
class ComicBookController(private val service: ComicBookService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun save(@RequestBody dto: @Valid ComicBookDTO) {
        service.save(dto)
    }

    @GetMapping
    fun fetchComicsByLanguage(
        @RequestHeader("Accept-Language") language: String?,
//        @RequestHeader("access_token") accessToken: String
    ): List<ComicBookDTO> {
        return service.fetchAllComics(language = language)
    }

}



