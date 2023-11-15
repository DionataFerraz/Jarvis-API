package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.MessageDTO
import br.com.jarvis.rest.controller.dto.TagDTO
import br.com.jarvis.service.TagService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/tag")
class TagController(
    private val service: TagService
) {

    @PostMapping
    fun saveTag(
        @RequestBody tagDTO: TagDTO,
    ): ResponseEntity<*> {
        service.createTag(tag = tagDTO.tag)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("Tag created successfully"))
    }

    @PostMapping("list")
    fun saveTagList(
        @RequestBody tagList: List<TagDTO>,
    ): ResponseEntity<*> {
        service.saveTagList(list = tagList)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("Tags created successfully"))
    }

    @DeleteMapping("{tagId}")
    fun removeTag(
        @PathVariable tagId: Long,
    ): ResponseEntity<Unit> {
        service.removeTag(tagId = tagId)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }

}



