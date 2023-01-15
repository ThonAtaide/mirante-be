package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.domain.Page
import java.util.Optional

interface MatchDao {

    fun save(championshipId: String, matchDocument: MatchDocument): MatchDocument

    fun update(championshipId: String, matchId: String, matchDocument: MatchDocument): MatchDocument

    fun findById(championshipId: String, matchId: String): Optional<MatchDocument>

    fun findAll(
        championshipId: String? = null,
        championshipName: String? = null,
        season: String? = null,
        organizedBy: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        playedAt: String? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int

    ): Page<MatchDocument>
}
