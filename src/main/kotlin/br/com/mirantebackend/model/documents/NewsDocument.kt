package br.com.mirantebackend.model.documents

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@NoArgsConstructor
@Document(collection = "coll_news")
data class NewsDocument(
    @Id
    val id: Long?,

    @Field("text")
    var text: String,

    @Field("created_by")
    val createdBy: String?,

    @Field("created_at")
    val createdAt: LocalDateTime,

    @Field("updated_at")
    var updatedAt: LocalDateTime,

    @Field("image_path")
    var imagePath: String
)