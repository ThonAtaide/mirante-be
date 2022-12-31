package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.dto.championship.ChampionshipDto
import br.com.mirantebackend.model.ChampionshipDocument

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