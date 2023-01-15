package br.com.mirantebackend.mapper

import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.dto.championship.ChampionshipDto

fun ChampionshipDto.toChampionshipDocument() = ChampionshipDocument(
    id,
    name,
    organizedBy,
    season,
    matches = matches?.map { it.toMatchDocument() }?.toMutableList(),
    createdAt,
    updatedAt
)

fun ChampionshipDocument.toChampionshipDto() = ChampionshipDto(
    id,
    name,
    organizedBy,
    season,
    matches = matches?.map { match -> match.toMatchDto() }?.toMutableList(),
    createdAt,
    updatedAt
)
