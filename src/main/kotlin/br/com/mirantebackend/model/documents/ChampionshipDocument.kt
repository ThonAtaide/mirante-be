package br.com.mirantebackend.model.documents

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@NoArgsConstructor
@Document(collection = "coll_championship")
data class ChampionshipDocument(

    @Id
    var id: String? = null,

    @Field("name")
    var name: String,

    @Field("organized_by")
    var organizedBy: String,

    @Field("season")
    var season: String,

    @Field("created_at")
    @CreatedDate
    val createdAt: LocalDateTime? = null,

    @Field("updated_at")
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null
) {
    companion object {
        const val FIELD_ID = "_id"
        const val FIELD_NAME = "name"
        const val FIELD_ORGANIZED_BY = "organized_by"
        const val FIELD_SEASON = "season"
        const val FIELD_CREATED_AT = "created_at"
        const val FIELD_UPDATED_AT = "updated_at"
        const val FIELD_MATCHES = "matches"
    }
}
