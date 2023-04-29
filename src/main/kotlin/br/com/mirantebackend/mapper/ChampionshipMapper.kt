package br.com.mirantebackend.mapper

import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.dto.championship.ChampionshipDto

fun ChampionshipDto.toChampionshipDocument() = ChampionshipDocument(
    id,
    name,
    organizedBy,
    season,
    createdAt,
    updatedAt
)

fun ChampionshipDocument.toChampionshipDto() = ChampionshipDto(
    id,
    name,
    organizedBy,
    season,
    createdAt,
    updatedAt
)
