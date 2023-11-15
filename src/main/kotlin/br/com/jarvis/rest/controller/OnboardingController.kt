package br.com.jarvis.rest.controller

import br.com.jarvis.rest.controller.dto.MessageDTO
import br.com.jarvis.rest.controller.dto.TagDTO
import br.com.jarvis.service.TagService
import br.com.jarvis.service.userinteraction.UserInterestService
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
@RequestMapping("/api/onboarding")
class OnboardingController(
    private val service: UserInterestService
) {

    @PostMapping
    fun saveUserTagPreferences(
        @RequestBody tagsIds: List<Long>,
    ): ResponseEntity<*> {
        service.saveUserTagPreferences(tagsIds = tagsIds)
        return ResponseEntity.status(HttpStatus.CREATED).body(MessageDTO("User interest saved successfully"))
    }

    @PutMapping
    fun updateUserTagPreferences(
        @RequestBody tagsIds: List<Long>,
    ): ResponseEntity<*> {
        service.updateUserTagPreferences(tagsIds = tagsIds)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(MessageDTO("User interest updated successfully"))
    }

}



