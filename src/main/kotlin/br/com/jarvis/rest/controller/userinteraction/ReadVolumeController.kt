package br.com.jarvis.rest.controller.userinteraction

import br.com.jarvis.rest.controller.dto.MessageDTO
import br.com.jarvis.rest.controller.dto.ReviewDTO
import br.com.jarvis.service.userinteraction.ReadVolumeService
import br.com.jarvis.service.userinteraction.ReviewComicBookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/read")
class ReadVolumeController(
    private val service: ReadVolumeService
    ,
) {

    @PostMapping("{volumeId}/volume")
    fun saveComicBookFavorite(
        @PathVariable volumeId: Long,
    ): ResponseEntity<*> {
        service.save(volumeId = volumeId)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("Volume read save successfully"))
    }

    @DeleteMapping("{volumeId}/volume")
    fun removeComicBookFavorite(
        @PathVariable volumeId: Long,
    ): ResponseEntity<Unit> {
        service.remove(volumeId = volumeId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}



