package br.com.mirantebackend.exceptions

class NewsCreateException: RuntimeException {

    constructor(message: String): super(message)
    constructor(throwable: Throwable): super(throwable)
}