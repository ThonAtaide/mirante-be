package br.com.mirantebackend.dao.interfaces

import br.com.mirantebackend.model.documents.MatchDocument
import org.springframework.data.domain.Page

interface MatchDao {

    fun findAll(
        championshipId: String? = null,
        principal: String? = null,
        challenger: String? = null,
        field: String? = null,
        playedAt: String? = null,
        matchEnded: Boolean? = null,
        pageNumber: Int,
        pageSize: Int

    ): Page<MatchDocument>
}
