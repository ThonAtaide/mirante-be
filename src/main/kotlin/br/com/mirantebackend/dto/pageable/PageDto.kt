package br.com.mirantebackend.dto.pageable

class PageDto<T : RecordDto>(
    var pageSize: Int,
    var pageNumber: Int,
    var total: Long,
    var records: Collection<T>?
)
