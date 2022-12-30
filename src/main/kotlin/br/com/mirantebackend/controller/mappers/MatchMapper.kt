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
    principal = principal.toTeamDocument(),
    challenger = challenger.toTeamDocument(),
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
    principal = principal.toTeamDto(),
    challenger = challenger.toTeamDto(),
    createdAt,
    updatedAt,
    matchEnded
)

fun TeamDocument.toTeamDto() = TeamDto(
    name, score
)
