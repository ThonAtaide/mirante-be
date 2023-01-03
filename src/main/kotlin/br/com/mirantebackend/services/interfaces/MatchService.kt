package br.com.mirantebackend.services.interfaces

import br.com.mirantebackend.dto.matches.MatchDto
import br.com.mirantebackend.dto.pageable.PageDto
import org.springframework.validation.annotation.Validated
import java.time.LocalDateTime
import javax.validation.Valid

@Validated
interface MatchService {

    fun createMatch(championshipId: String, @Valid matchDto: MatchDto): MatchDto

    fun updateMatch(championshipId: String, matchId: String, @Valid matchDto: MatchDto): MatchDto

    fun findById(championshipId: String, matchId: String): MatchDto

    fun findAll(
        championshipId: String? = null,
        championshipName: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        playedAtAfter: LocalDateTime? = null,
        playedAtBefore: LocalDateTime? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int
    ): PageDto<MatchDto>

}