package br.com.jarvis.service.userinteraction

interface StorageVolumeService {
    fun save(volumeId: Long)
    fun remove(volumeId: Long)
}