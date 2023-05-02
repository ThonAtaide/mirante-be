package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.news.NewsRequestVo
import br.com.mirantebackend.controller.vo.news.NewsVo
import br.com.mirantebackend.model.dto.news.NewsDto
import br.com.mirantebackend.model.dto.news.NewsRequestDto

fun NewsRequestVo.toNewsRequestDto() = NewsRequestDto(
    id,
    title!!,
    text!!
)

fun NewsDto.toNewsVo() = NewsVo(
    id,
    title,
    text,
    createdBy,
    updatedBy,
    createdAt,
    updatedAt,
    imagePath
)
