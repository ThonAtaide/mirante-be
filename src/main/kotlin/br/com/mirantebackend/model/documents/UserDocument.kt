package br.com.mirantebackend.model.documents

import br.com.mirantebackend.model.enums.UserRolesEnum
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document("coll_user_document")
data class UserDocument(

    @Id
    var id: String? = null,

    @Field("name")
    var name: String,

    @Field("username")
    var username: String,

    @Field("email")
    var email: String,

    @Field("password")
    var password: String,

    @Field("user_role")
    var userRole: UserRolesEnum,

    @Field("is_enable")
    var isEnable: Boolean = true,

    @Field("created_at")
    @CreatedDate
    var createdAt: LocalDateTime? = null
)
