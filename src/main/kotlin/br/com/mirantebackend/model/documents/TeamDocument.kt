package br.com.mirantebackend.model.documents

import org.springframework.data.mongodb.core.mapping.Field

data class TeamDocument(
    @Field("name")
    var name: String,

    @Field("score")
    var score: Int? = null
)
