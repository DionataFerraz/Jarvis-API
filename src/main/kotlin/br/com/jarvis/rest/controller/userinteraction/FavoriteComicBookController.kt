package br.com.jarvis.rest.controller.userinteraction

import br.com.jarvis.rest.controller.dto.MessageDTO
import br.com.jarvis.service.userinteraction.FavoriteComicBookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/favorite")
class FavoriteComicBookController(
    private val service: FavoriteComicBookService,
) {

    @PostMapping("{comicBookId}/comicbook")
    fun saveComicBookFavorite(
        @PathVariable comicBookId: Long,
    ): ResponseEntity<*> {
        service.favorite(comicBookId = comicBookId)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("Item favorited successfully"))
    }

    @DeleteMapping("{comicBookId}/comicbook")
    fun removeComicBookFavorite(
        @PathVariable comicBookId: Long,
    ): ResponseEntity<Unit> {
        service.unFavorite(comicBookId = comicBookId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}



