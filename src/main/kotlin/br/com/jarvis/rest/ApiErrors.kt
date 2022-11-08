package br.com.jarvis.rest

class ApiErrors {
    var errors: List<String>
        private set

    constructor(errors: List<String>) {
        this.errors = errors
    }

    constructor(errorMessage: String) {
        errors = listOf(errorMessage)
    }
}