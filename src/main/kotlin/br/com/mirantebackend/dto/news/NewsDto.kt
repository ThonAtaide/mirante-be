package br.com.mirantebackend.dto.news

import br.com.mirantebackend.dto.pageable.RecordDto
import java.time.LocalDateTime

data class NewsDto(
    var id: Long?,
    var title: String,
    var text: String,
    var createdBy: String?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var imagePath: String
) : RecordDto
