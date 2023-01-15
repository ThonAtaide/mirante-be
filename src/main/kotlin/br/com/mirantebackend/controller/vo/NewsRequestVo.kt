package br.com.mirantebackend.controller.vo

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class NewsRequestVo(
    var id: String? = null,

    @field:NotNull(message = "É preciso informar o título do post.")
    @field:NotEmpty(message = "É preciso informar o título do post.")
    var title: String? = null,

    @field:NotNull(message = "É preciso informar o texto do post.")
    @field:NotEmpty(message = "É preciso informar o texto do post.")
    var text: String? = null
)
