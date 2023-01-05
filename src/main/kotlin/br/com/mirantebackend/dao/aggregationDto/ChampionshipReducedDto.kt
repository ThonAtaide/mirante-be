package br.com.mirantebackend.dao.aggregationDto

import br.com.mirantebackend.model.MatchDocument

data class ChampionshipReducedDto(
    var id: String? = null,
    var name: String? = null,
    var matches: MatchDocument
)