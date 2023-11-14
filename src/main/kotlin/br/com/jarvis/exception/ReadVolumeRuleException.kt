package br.com.jarvis.exception

object ReadVolumeDuplicatedException : RuntimeException("Duplicate action to save read volume")
object ReadVolumeNotFoundException : RuntimeException(
    "You cannot delete volume is read because it is exist"
)
data class ReadVolumeErrorException(override val message: String) : RuntimeException("Error to remove read: $message")
