package br.com.mirantebackend.model

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.annotation.ReadOnlyProperty
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

    @Field("created_at")
    val createdAt: LocalDateTime? = null,

    @Field("updated_at")
    var updatedAt: LocalDateTime? = null,

    @Field("match_ended")
    var matchEnded: Boolean = false,
) {
    companion object {
        const val FIELD_ID = "_id"
        const val FIELD_FIELD = "field"
        const val FIELD_PLAYED_AT = "played_at"
        const val FIELD_PRINCIPAL= "principal"
        const val FIELD_CHALLENGER = "challenger"
        const val FIELD_UPDATED_AT = "updated_at"
        const val FIELD_MATCH_ENDED = "match_ended"
    }
}