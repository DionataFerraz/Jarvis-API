package br.com.jarvis.service.userinteraction

interface UserInterestService {
    fun saveUserTagPreferences(tagsIds: List<Long>)
    fun updateUserTagPreferences(tagsIds: List<Long>)
}
