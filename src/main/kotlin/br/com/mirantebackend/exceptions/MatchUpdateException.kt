package br.com.mirantebackend.exceptions

class MatchUpdateException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(match: Any) : super("Match $match could not be updated.")
}
