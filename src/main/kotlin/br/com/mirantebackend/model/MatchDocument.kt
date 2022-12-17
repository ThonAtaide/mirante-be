package br.com.mirantebackend.model

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@NoArgsConstructor
@Document(collection = "coll_match")
data class MatchDocument(

    @Id
    val id: String?,

    @Field("field")
    var field: String,

    @Field("cup")
    var cup: String,

    @Field("played_at")
    var playedAt: LocalDateTime,

    @Field("principal")
    var principal: TeamDocument,

    @Field("challenger")
    var challenger: TeamDocument,

    @Field("created_at")
    @CreatedDate
    val createdAt: LocalDateTime? = null,

    @Field("updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null,

    @Field("match_ended")
    var matchEnded: Boolean,
)