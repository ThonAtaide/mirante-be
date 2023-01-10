package br.com.mirantebackend.model.dto.news

import org.springframework.web.multipart.MultipartFile

data class NewsRequestDto(
    var id: String?,
    var title: String,
    var text: String,
    var image: MultipartFile
)