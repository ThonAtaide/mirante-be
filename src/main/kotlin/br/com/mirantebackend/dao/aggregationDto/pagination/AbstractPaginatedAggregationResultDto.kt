package br.com.mirantebackend.dao.aggregationDto.pagination

abstract class AbstractPaginatedAggregationResultDto<T> {
    var data: MutableList<T>? = null
    var total: Long = 0
}