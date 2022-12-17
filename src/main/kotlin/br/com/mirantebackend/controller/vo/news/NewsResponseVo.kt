package br.com.mirantebackend.controller.vo.news

import br.com.mirantebackend.controller.vo.SingleRecordDto
import java.time.LocalDateTime

data class NewsResponseVo(
    var id: Long?,
    var text: String,
    var createdBy: String?,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var imagePath: String
) : SingleRecordDto
