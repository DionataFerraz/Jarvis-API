package br.com.jarvis.rest.controller.userinteraction

import br.com.jarvis.rest.controller.dto.MessageDTO
import br.com.jarvis.service.userinteraction.StorageVolumeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/storage")
class StorageVolumeController(
    private val service: StorageVolumeService
) {

    @PostMapping("{volumeId}/volume")
    fun saveComicBookFavorite(
        @PathVariable volumeId: Long,
    ): ResponseEntity<*> {
        service.save(volumeId = volumeId)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("Has volume save successfully"))
    }

    @DeleteMapping("{volumeId}/volume")
    fun removeComicBookFavorite(
        @PathVariable volumeId: Long,
    ): ResponseEntity<Unit> {
        service.remove(volumeId = volumeId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}



