package br.com.mirantebackend.exceptions

class ChampionshipNotFoundException(championshipId: String): RuntimeException("Championship of id $championshipId not found.")