package br.com.mirantebackend.dao.aggregationDto.pagination

abstract class AbstractPaginatedAggregationResultDto<T> {

    companion object {
        const val FIELD_DATA = "data"
        const val FIELD_PAGINATION = "pagination"
        const val FIELD_PAGINATION_TOTAL = "total"
    }

    var data: MutableList<T>? = null
    var total: Long = 0
}