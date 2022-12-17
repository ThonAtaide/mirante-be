package br.com.mirantebackend.controller.vo.news

import java.io.File

data class NewsRequestVo(
    var id: Long?,
    var text: String,
    var image: File
)