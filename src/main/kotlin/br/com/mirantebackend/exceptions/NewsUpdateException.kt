package br.com.mirantebackend.exceptions

class NewsUpdateException : RuntimeException {

    constructor(message: String) : super(message)
    constructor(throwable: Throwable) : super(throwable)
}
