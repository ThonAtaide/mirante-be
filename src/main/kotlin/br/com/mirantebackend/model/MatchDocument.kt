package br.com.mirantebackend.model

import br.com.mirantebackend.annotations.NoArgsConstructor
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
)