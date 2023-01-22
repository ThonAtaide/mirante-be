package br.com.mirantebackend.factories

import br.com.mirantebackend.model.documents.ChampionshipDocument
import br.com.mirantebackend.model.documents.MatchDocument
import org.bson.types.ObjectId
import java.time.LocalDateTime
import java.time.ZoneOffset.UTC
import java.util.UUID

class ChampionshipDocumentFactory {

    companion object {

        fun buildWith(
            id: String? = ObjectId().toString(),
            name: String = UUID.randomUUID().toString(),
            organizedBy: String = UUID.randomUUID().toString(),
            season: String = UUID.randomUUID().toString(),
            matches: MutableList<MatchDocument> = mutableListOf(),
            createdAt: LocalDateTime = LocalDateTime.now(UTC),
            updatedAt: LocalDateTime = LocalDateTime.now(UTC).plusHours(1)
        ): ChampionshipDocument =
            ChampionshipDocument(
                id,
                name,
                organizedBy,
                season,
                matches,
                createdAt,
                updatedAt
            )
    }
}
