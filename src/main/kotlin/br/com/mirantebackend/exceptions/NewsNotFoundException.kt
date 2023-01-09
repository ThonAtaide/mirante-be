package br.com.mirantebackend.exceptions

class NewsNotFoundException: RuntimeException {

    constructor(message: String): super(message)
    constructor(throwable: Throwable): super(throwable)
}