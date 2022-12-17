package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.model.MatchDocument
import br.com.mirantebackend.model.TeamDocument
import br.com.mirantebackend.services.dto.matches.MatchDto
import br.com.mirantebackend.services.dto.matches.TeamDto


fun MatchDto.toMatchDocument() = MatchDocument(
    id,
    field,
    cup,
    playedAt,
    principal = principal.toTeamDocument(),
    challenger = challenger.toTeamDocument(),
    createdAt,
    matchEnded = matchEnded
)

fun TeamDto.toTeamDocument() = TeamDocument(
    name, score
)

fun MatchDocument.toMatchDto() = MatchDto(
    id,
    field,
    cup,
    playedAt,
    principal = principal.toTeamDto(),
    challenger = challenger.toTeamDto(),
    createdAt,
    updatedAt,
    matchEnded
)

fun TeamDocument.toTeamDto() = TeamDto(
    name, score
)