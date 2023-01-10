package br.com.mirantebackend.model.documents

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@NoArgsConstructor
@Document(collection = "coll_news")
data class NewsDocument(
    @Id
    val id: String? = null,

    @Field("title")
    var title: String,

    @Field("text")
    var text: String,

    @Field("created_by")
    val createdBy: String,

    @CreatedDate
    @Field("created_at")
    val createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @Field("updated_at")
    var updatedAt: LocalDateTime? = null,

    @Field("image_path")
    var imagePath: String
) {
    companion object {
        const val FIELD_ID = "_id"
        const val FIELD_TEXT = "text"
        const val FIELD_CREATED_BY = "created_by"
        const val FIELD_CREATED_AT = "created_at"
        const val FIELD_UPDATED_AT = "updated_at"
        const val FIELD_IMAGE_PATH = "image_path"
    }
}