package br.com.jarvis.service.userinteraction

import br.com.jarvis.domain.entity.ReadVolumeEntity
import br.com.jarvis.domain.repository.ReadVolumeRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.ReadVolumeDuplicatedException
import br.com.jarvis.exception.ReadVolumeErrorException
import br.com.jarvis.exception.ReadVolumeNotFoundException
import br.com.jarvis.exception.VolumeNotFoundException
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class ReadVolumeServiceImpl(
    private val repository: ReadVolumeRepository,
    private val volumeRepository: VolumeRepository,
    private val userService: UserService
) : ReadVolumeService {

    @Transactional
    override fun save(volumeId: Long) {
        val user = userService.retrieveUser()

        volumeRepository.findById(volumeId).map { volume ->
            if (repository.isDuplicate(volumeId = volumeId, userId = user.id)) {
                throw ReadVolumeDuplicatedException
            } else {
                repository.save(
                    ReadVolumeEntity(
                        volumeEntity = volume,
                        isRead = true,
                        userEntity = user
                    )
                )
            }
        }.orElseThrow {
            VolumeNotFoundException
        }
    }

    @Transactional
    override fun remove(volumeId: Long) {
        try {
            val userId = userService.retrieveUser().id

            if (repository.isDuplicate(volumeId, userId)) {
                val entity = repository.findByComicBookIdAndUserId(volumeId = volumeId, userId = userId)
                repository.delete(entity)
            } else {
                throw ReadVolumeNotFoundException
            }
        } catch (exception: Exception) {
            throw ReadVolumeErrorException
        }
    }
}
