package br.com.mirantebackend.model.documents

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@NoArgsConstructor
data class MatchDocument(

    val id: String? = null,

    @Field("field")
    var field: String,

    @Field("played_at")
    var playedAt: LocalDateTime,

    @Field("principal")
    var principal: TeamDocument,

    @Field("challenger")
    var challenger: TeamDocument,

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: LocalDateTime? = null,

    @Field("match_ended")
    var matchEnded: Boolean = false,

    @DocumentReference
    var championship: ChampionshipDocument,
) {
    companion object {
        const val FIELD_ID = "_id"
        const val FIELD_FIELD = "field"
        const val FIELD_PLAYED_AT = "played_at"
        const val FIELD_PRINCIPAL = "principal"
        const val FIELD_PRINCIPAL_NAME = "principal.name"
        const val FIELD_CHALLENGER = "challenger"
        const val FIELD_CHALLENGER_NAME = "challenger.name"
        const val FIELD_CREATED_AT = "created_at"
        const val FIELD_UPDATED_AT = "updated_at"
        const val FIELD_MATCH_ENDED = "match_ended"
        const val FIELD_CHAMPIONSHIP_ID = "championship.id"
    }
}
