package br.com.mirantebackend.exceptions

class MatchUpdateException: RuntimeException {

    constructor(throwable: Throwable): super(throwable)
    constructor(message: String): super(message)
    constructor(match: Any): super("Match $match could not be updated.")
}