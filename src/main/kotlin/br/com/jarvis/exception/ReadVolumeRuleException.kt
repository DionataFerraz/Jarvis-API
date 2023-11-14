package br.com.jarvis.exception

object ReadVolumeDuplicatedException : RuntimeException("Duplicate action to save read volume")
object ReadVolumeNotFoundException : RuntimeException(
    "You cannot delete volume is read because it is exist"
)
object ReadVolumeErrorException : RuntimeException("Error to remove read")
