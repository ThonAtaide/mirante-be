package br.com.mirantebackend.exceptions

class MatchCreationException: RuntimeException {

    constructor(throwable: Throwable): super(throwable)
    constructor(message: String): super(message)
}