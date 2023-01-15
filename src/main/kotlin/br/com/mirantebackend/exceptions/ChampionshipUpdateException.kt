package br.com.mirantebackend.exceptions

class ChampionshipUpdateException : RuntimeException {

    constructor(throwable: Throwable) : super(throwable)
    constructor(message: String) : super(message)
}
