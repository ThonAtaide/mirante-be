package br.com.mirantebackend.mapper

import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.documents.MatchDocument
import br.com.mirantebackend.model.documents.TeamDocument
import br.com.mirantebackend.model.dto.matches.MatchDto
import br.com.mirantebackend.model.dto.matches.TeamDto
import java.time.LocalDateTime
import java.time.ZoneOffset

fun MatchDto.toMatchDocument(championshipDocument: ChampionshipDocument) = MatchDocument(
    id,
    field,
    playedAt,
    principal = principal?.toTeamDocument(),
    challenger = challenger?.toTeamDocument(),
    createdAt,
    updatedAt = LocalDateTime.now(ZoneOffset.UTC),
    matchEnded = matchEnded,
    championship = championshipDocument
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
    championship = championship.toChampionshipDto()
)

fun TeamDocument.toTeamDto() = TeamDto(
    name, score
)
