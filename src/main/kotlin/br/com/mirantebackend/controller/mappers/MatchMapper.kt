package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.matches.TeamDto
import br.com.mirantebackend.model.MatchDocument
import br.com.mirantebackend.model.TeamDocument
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
    matchEnded = matchEnded
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
    championship = MatchDto.ChampionshipDtoReduced(championshipId, championshipName)

)
