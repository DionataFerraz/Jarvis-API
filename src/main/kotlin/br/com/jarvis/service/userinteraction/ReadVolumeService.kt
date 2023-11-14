package br.com.jarvis.service.userinteraction

interface ReadVolumeService {
    fun save(volumeId: Long)
    fun remove(volumeId: Long)
}