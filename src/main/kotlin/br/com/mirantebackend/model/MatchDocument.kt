package br.com.mirantebackend.model

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@NoArgsConstructor
data class MatchDocument(

    val id: String? = null,

    @field:NotNull(message = "É preciso informar o campo.")
    @field:NotEmpty(message = "É preciso informar o campo.")
    @Field("field")
    var field: String? = null,

    @field:NotNull(message = "É preciso informar a data e horário do jogo.")
    @Field("played_at")
    var playedAt: LocalDateTime? = null,

    @field:NotNull(message = "É preciso informar o time mandante.")
    @Field("principal")
    var principal: TeamDocument? = null,

    @field:NotNull(message = "É preciso informar o time desafiante.")
    @Field("challenger")
    var challenger: TeamDocument? = null,

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
        const val FIELD_PRINCIPAL_NAME= "principal.name"
        const val FIELD_CHALLENGER = "challenger"
        const val FIELD_CHALLENGER_NAME = "challenger.name"
        const val FIELD_UPDATED_AT = "updated_at"
        const val FIELD_MATCH_ENDED = "match_ended"
    }
}