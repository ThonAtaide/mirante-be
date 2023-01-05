package br.com.mirantebackend.dto.news

import org.springframework.web.multipart.MultipartFile

data class NewsRequestDto(
    var id: Long?,
    var title: String,
    var text: String,
    var image: MultipartFile?
)