package br.com.mirantebackend.exceptions

class NewsNotFoundException : RuntimeException {

    constructor(message: String) : super(message)
}
