package br.com.mirantebackend.controller.vo

class PageDto(
    var pageSize: Long,
    var pageNumber: Long,
    var records: Collection<SingleRecordDto>
)
