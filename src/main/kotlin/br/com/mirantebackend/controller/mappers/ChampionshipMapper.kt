package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.ChampionshipVo
import br.com.mirantebackend.model.dto.championship.ChampionshipDto

fun ChampionshipDto.toChampionshipVo() = ChampionshipVo(
    id,
    name,
    organizedBy,
    season,
    matches = matches?.map { it.toMatchVo() }?.toMutableList(),
    createdAt,
    updatedAt
)

fun ChampionshipVo.toChampionshipDto() = ChampionshipDto(
    id,
    name!!,
    organizedBy!!,
    season!!,
    matches = matches?.map { match -> match.toMatchDto() }?.toMutableList(),
    createdAt,
    updatedAt
)