package br.com.mirantebackend.controller.vo

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class NewsRequestVo(
    var id: String? = null,

    @field:NotNull(message = "É preciso informar o título do post.")
    @field:NotEmpty(message = "É preciso informar o título do post.")
    var title: String? = null,

    @field:NotNull(message = "É preciso informar o texto do post.")
    @field:NotEmpty(message = "É preciso informar o texto do post.")
    var text: String? = null,

//    @field:NotNull(message = "É preciso informar a imagem do post.")
    var image: MultipartFile? = null
)