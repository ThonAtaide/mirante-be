package br.com.mirantebackend.exceptions

class MatchNotFoundException(matchId: String, championshipId: String): RuntimeException("Match of id $matchId from championship $championshipId not found.")