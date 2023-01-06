package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.model.dto.matches.MatchDto
import br.com.mirantebackend.model.dto.matches.TeamDto
import br.com.mirantebackend.model.documents.MatchDocument
import br.com.mirantebackend.model.documents.TeamDocument
import java.time.LocalDateTime
import java.time.ZoneOffset


fun MatchDto.toMatchDocument() = MatchDocument(
    id,
    field,
    playedAt,
    principal = principal?.toTeamDocument(),
    challenger = challenger?.toTeamDocument(),
    createdAt,
    updatedAt = LocalDateTime.now(ZoneOffset.UTC),
    matchEnded = matchEnded
)

fun TeamDto.toTeamDocument() = TeamDocument(
    name, score
)

fun MatchDocument.toMatchDto() = MatchDto(
    id,
    field,
    playedAt,
    principal = principal?.toTeamDto(),
    challenger = challenger?.toTeamDto(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    matchEnded = matchEnded,
    championship = championship?.toChampionshipInfoDto()
)

fun TeamDocument.toTeamDto() = TeamDto(
    name, score
)

fun MatchDocument.toMatchDto(championshipId: String?, championshipName: String?) = MatchDto(
    id,
    field,
    playedAt,
    principal = principal?.toTeamDto(),
    challenger = challenger?.toTeamDto(),
    createdAt = createdAt,
    updatedAt = updatedAt,
    matchEnded = matchEnded,
    championship = MatchDto.ChampionshipInfoDto(championshipId, championshipName)
)

fun MatchDocument.ChampionshipInfo.toChampionshipInfoDto() = MatchDto.ChampionshipInfoDto(
    id = id,
    name = name
)