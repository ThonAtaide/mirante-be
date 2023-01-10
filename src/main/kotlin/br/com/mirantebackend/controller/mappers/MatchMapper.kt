package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.MatchVo
import br.com.mirantebackend.controller.vo.TeamVo
import br.com.mirantebackend.model.documents.MatchDocument
import br.com.mirantebackend.model.dto.matches.MatchDto
import br.com.mirantebackend.model.dto.matches.TeamDto
import java.time.LocalDateTime
import java.time.ZoneOffset


fun MatchDto.toMatchVo() = MatchVo(
    id,
    field,
    playedAt,
    principal = principal?.toTeamVo(),
    challenger = challenger?.toTeamVo(),
    matchEnded,
    createdAt = createdAt,
    updatedAt = LocalDateTime.now(ZoneOffset.UTC),
    championship = championship?.toChampionshipInfoVo()
)

fun TeamDto.toTeamVo() = TeamVo(
    name, score
)

fun MatchVo.toMatchDto() = MatchDto(
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

fun TeamVo.toTeamDto() = TeamDto(
    name, score
)

fun MatchDto.ChampionshipInfoDto.toChampionshipInfoVo() = MatchVo.ChampionshipInfoVo(
    id = id,
    name = name
)

fun MatchVo.ChampionshipInfoVo.toChampionshipInfoDto() = MatchDto.ChampionshipInfoDto(
    id = id,
    name = name
)