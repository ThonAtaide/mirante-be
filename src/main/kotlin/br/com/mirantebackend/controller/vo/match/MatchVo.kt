package br.com.mirantebackend.controller.vo.match

import br.com.mirantebackend.controller.vo.championship.ChampionshipVo
import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class MatchVo(
    var id: String?,

    @field:NotNull(message = "É preciso informar o campo.")
    @field:NotEmpty(message = "É preciso informar o campo.")
    var field: String? = null,

    @field:NotNull(message = "É preciso informar a data e horário do jogo.")
    var playedAt: LocalDateTime? = null,

    @field:NotNull(message = "É preciso informar o time mandante.")
    var principal: TeamVo? = null,

    @field:NotNull(message = "É preciso informar o time desafiante.")
    var challenger: TeamVo? = null,

    var matchEnded: Boolean = false,

    var championship: ChampionshipVo? = null,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null

) : RecordDto
