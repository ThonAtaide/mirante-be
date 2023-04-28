package br.com.mirantebackend.controller.vo.news

import br.com.mirantebackend.model.dto.pageable.RecordDto
import java.time.LocalDateTime

data class NewsVo(

    var id: String?,

    var title: String?,

    var text: String?,

    var createdBy: String?,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null,

    var imagePath: String
) : RecordDto
