package br.com.jarvis.exception

object StorageVolumeDuplicatedException : RuntimeException("Duplicate action to save has volume")
object StorageVolumeNotFoundException : RuntimeException(
    "You cannot delete has volume because it not exist"
)
data class StorageVolumeErrorException(override val message: String) : RuntimeException(
    "Error to remove has volume: $message"
)
