package br.com.mirantebackend.model

import br.com.mirantebackend.annotations.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Field

@NoArgsConstructor
data class TeamDocument(
    @Field("name")
    var name: String,

    @Field("score")
    var score: Int? = null
)