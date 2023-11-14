package br.com.jarvis.rest.controller.userinteraction

import br.com.jarvis.rest.controller.dto.MessageDTO
import br.com.jarvis.rest.controller.dto.ReviewDTO
import br.com.jarvis.service.userinteraction.ReviewComicBookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/review")
class ReviewComicBookController(
    private val service: ReviewComicBookService,
) {

    @PostMapping("{comicBookId}")
    fun saveComicBookReview(
        @PathVariable comicBookId: Long,
        @RequestBody reviewDTO: ReviewDTO,
    ): ResponseEntity<*> {
        service.review(comicBookId = comicBookId, review = reviewDTO.review)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("Volume review saved successfully"))
    }

    @PutMapping("{comicBookId}")
    fun updateComicBookReview(
        @PathVariable comicBookId: Long,
        @RequestBody reviewDTO: ReviewDTO,
    ): ResponseEntity<Unit> {
        service.updateReview(comicBookId = comicBookId, review = reviewDTO.review)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}



