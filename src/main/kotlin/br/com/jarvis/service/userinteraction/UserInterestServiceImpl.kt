package br.com.jarvis.service.userinteraction

import br.com.jarvis.domain.entity.UserInterestEntity
import br.com.jarvis.domain.repository.TagRepository
import br.com.jarvis.domain.repository.UserInterestRepository
import br.com.jarvis.exception.TagDuplicatedException
import br.com.jarvis.exception.TagErrorException
import br.com.jarvis.exception.TagNotFoundException
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.Date

@Service
open class UserInterestServiceImpl(
    private val repository: UserInterestRepository,
    private val tagRepository: TagRepository,
    private val userService: UserService,
) : UserInterestService {

    @Transactional
    override fun saveUserTagPreferences(tagsIds: List<Long>) {
        try {
            val user = userService.retrieveUser()
            if (repository.isDuplicate(tagsIds = tagsIds, userId = user.id)) {
                throw TagDuplicatedException
            }
            repository.saveAll(tagsIds.map { tagId ->
                UserInterestEntity(
                    date = Date(),
                    userEntity = user,
                    tagEntity = tagRepository.findById(tagId).orElseThrow {
                        TagNotFoundException
                    }
                )
            })
        } catch (exception: Exception) {
            throw TagErrorException(exception.message.orEmpty())
        }
    }

    @Transactional
    override fun updateUserTagPreferences(tagsIds: List<Long>) {
        try {
            val user = userService.retrieveUser()
            repository.deleteAllByUserId(userId = user.id)
            repository.saveAll(tagsIds.map { tagId ->
                UserInterestEntity(
                    date = Date(),
                    userEntity = user,
                    tagEntity = tagRepository.findById(tagId).orElseThrow {
                        TagNotFoundException
                    }
                )
            })
        } catch (exception: Exception) {
            throw TagErrorException(exception.message.orEmpty())
        }
    }
}
