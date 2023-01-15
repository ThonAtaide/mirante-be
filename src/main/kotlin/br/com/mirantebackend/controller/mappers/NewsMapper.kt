package br.com.mirantebackend.controller.mappers

import br.com.mirantebackend.controller.vo.NewsRequestVo
import br.com.mirantebackend.controller.vo.NewsVo
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
    createdAt,
    updatedAt,
    imagePath
)
