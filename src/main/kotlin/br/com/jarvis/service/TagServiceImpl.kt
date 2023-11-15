package br.com.jarvis.service

import br.com.jarvis.domain.entity.TagEntity
import br.com.jarvis.domain.repository.TagRepository
import br.com.jarvis.exception.TagDuplicatedException
import br.com.jarvis.exception.TagErrorException
import br.com.jarvis.exception.TagNotFoundException
import br.com.jarvis.rest.controller.dto.TagDTO
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class TagServiceImpl(
    private val repository: TagRepository,
) : TagService {

    @Transactional
    override fun createTag(tag: String) {
        if (repository.isDuplicate(tag)) {
            throw TagDuplicatedException
        } else {
            repository.save(TagEntity(name = tag))
        }
    }

    override fun saveTagList(list: List<TagDTO>) {
        try {
            repository.saveAll(list.map {
                TagEntity(name = it.tag)
            })
        } catch (exception: Exception) {
            throw TagErrorException(exception.message.orEmpty())
        }
    }

    @Transactional
    override fun removeTag(tagId: Long) {
        try {
            repository.findById(tagId).orElseThrow {
                throw TagNotFoundException
            }
            repository.deleteById(tagId)
        } catch (exception: Exception) {
            throw TagErrorException(exception.message.orEmpty())
        }
    }
}
