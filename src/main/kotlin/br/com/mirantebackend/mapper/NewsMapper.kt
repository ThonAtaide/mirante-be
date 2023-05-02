package br.com.mirantebackend.mapper

import br.com.mirantebackend.model.documents.NewsDocument
import br.com.mirantebackend.model.dto.news.NewsDto

fun NewsDocument.toNewsDto() = NewsDto(
    id,
    title,
    text,
    createdBy = createdBy?.name!!,
    updatedBy = updatedBy?.name!!,
    createdAt!!,
    updatedAt!!,
    imagePath
)
