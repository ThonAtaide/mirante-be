package br.com.mirantebackend.exceptions

class ChampionshipCreationException : RuntimeException {
    constructor(throwable: Throwable) : super("Championship could not be created. ${throwable.message}")
    constructor(errorMessage: String) : super(errorMessage)
}
