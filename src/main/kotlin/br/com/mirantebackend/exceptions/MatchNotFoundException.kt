package br.com.mirantebackend.exceptions

class MatchNotFoundException(matchId: String): RuntimeException("Match of id $matchId not found.")