package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.model.documents.NewsDocument
import br.com.mirantebackend.model.dto.news.NewsDto

fun NewsDocument.toNewsDto() = NewsDto(
    id,
    title,
    text,
    createdBy,
    createdAt,
    updatedAt,
    imagePath
)