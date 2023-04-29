package br.com.mirantebackend.exceptions

class MatchNotFoundException(matchId: String) :
    RuntimeException("Match from id $matchId not found.")
