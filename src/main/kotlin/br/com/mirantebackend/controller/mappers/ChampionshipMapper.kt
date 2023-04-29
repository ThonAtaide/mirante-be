package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.championship.ChampionshipVo
import br.com.mirantebackend.model.dto.championship.ChampionshipDto

fun ChampionshipDto.toChampionshipVo() = ChampionshipVo(
    id,
    name,
    organizedBy,
    season,
    createdAt,
    updatedAt
)

fun ChampionshipVo.toChampionshipDto() = ChampionshipDto(
    id,
    name!!,
    organizedBy!!,
    season!!,
    createdAt,
    updatedAt
)
