package br.com.mirantebackend.exceptions

class ChampionshipCreationException(throwable: Throwable): RuntimeException("Championship could not be created. ${throwable.message}")