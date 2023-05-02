package br.com.mirantebackend.model.dto.news

import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class NewsDto(
    var id: String?,

    @field:NotNull(message = "É preciso informar o título da postagem.")
    @field:NotEmpty(message = "É preciso informar o título da postagem.")
    var title: String,

    @field:NotNull(message = "É preciso informar o texto da postagem.")
    @field:NotEmpty(message = "É preciso informar o texto da postagem.")
    var text: String,

    @field:NotNull(message = "É preciso informar o autor da postagem.")
    @field:NotEmpty(message = "É preciso informar o autor da postagem.")
    var createdBy: String,

    val updatedBy: String,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null,

    @field:NotNull(message = "É preciso informar o caminho da imagem da postagem.")
    @field:NotEmpty(message = "É preciso informar o caminho da imagem da postagem.")
    var imagePath: String
) : RecordDto
