package br.com.jarvis.service.userinteraction

import br.com.jarvis.domain.entity.StorageVolumeEntity
import br.com.jarvis.domain.repository.StorageVolumeRepository
import br.com.jarvis.domain.repository.VolumeRepository
import br.com.jarvis.exception.StorageVolumeDuplicatedException
import br.com.jarvis.exception.StorageVolumeErrorException
import br.com.jarvis.exception.StorageVolumeNotFoundException
import br.com.jarvis.exception.VolumeNotFoundException
import br.com.jarvis.service.auth.UserService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class StorageVolumeServiceImpl(
    private val repository: StorageVolumeRepository,
    private val volumeRepository: VolumeRepository,
    private val userService: UserService
) : StorageVolumeService {

    @Transactional
    override fun save(volumeId: Long) {
        val user = userService.retrieveUser()

        volumeRepository.findById(volumeId).map { volume ->
            if (repository.isDuplicate(volumeId = volumeId, userId = user.id)) {
                throw StorageVolumeDuplicatedException
            } else {
                repository.save(
                    StorageVolumeEntity(
                        volumeEntity = volume,
                        hasVolume = true,
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
                val entity = repository.findByVolumeIdAndUserId(volumeId = volumeId, userId = userId)
                repository.delete(entity)
            } else {
                throw StorageVolumeNotFoundException
            }
        } catch (exception: Exception) {
            throw StorageVolumeErrorException(exception.message.orEmpty())
        }
    }
}
