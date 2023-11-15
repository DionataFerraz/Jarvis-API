package br.com.jarvis.service

import br.com.jarvis.rest.controller.dto.TagDTO

interface TagService {
    fun createTag(tag: String)
    fun saveTagList(list: List<TagDTO>)
    fun removeTag(tagId: Long)
}
