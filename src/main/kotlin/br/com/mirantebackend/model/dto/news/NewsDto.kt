package br.com.mirantebackend.model.dto.news

import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime

data class NewsDto(
    var id: String?,
    var title: String,
    var text: String,
    var createdBy: String,
    var createdAt: LocalDateTime? = null,
    var updatedAt: LocalDateTime? = null,
    var imagePath: String
) : RecordDto
