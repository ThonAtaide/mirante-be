package br.com.mirantebackend.dao.utils

import br.com.mirantebackend.dao.aggregationDto.pagination.AbstractPaginatedAggregationResultDto
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.data.mongodb.core.aggregation.ArrayOperators

class AggregationUtils {

    companion object {

        fun buildNewPaginatedAggregation(
            pageNumber: Int,
            pageSize: Int,
            aggregationOperationList: MutableList<AggregationOperation>
        ): Aggregation {
            addedFacet(pageNumber, pageSize, aggregationOperationList)
            addedTotalFieldAsObject(aggregationOperationList)
            addedProjectFields(aggregationOperationList)
            return Aggregation.newAggregation(aggregationOperationList)
        }

        private fun addedFacet(
            pageNumber: Int,
            pageSize: Int,
            aggregationOperationList: MutableList<AggregationOperation>
        ) = Aggregation.facet().and(
            Aggregation.skip(pageNumber.toLong() * pageSize),
            Aggregation.limit(pageSize.toLong())
        )
            .`as`(AbstractPaginatedAggregationResultDto.FIELD_DATA)
            .and(Aggregation.count().`as`(AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL)).`as`(
                AbstractPaginatedAggregationResultDto.FIELD_PAGINATION
            ).let { aggregationOperationList.add(it) }

        private fun addedTotalFieldAsObject(aggregationOperationList: MutableList<AggregationOperation>) {
            val elementAt =
                ArrayOperators.ArrayElemAt.arrayOf("\$${AbstractPaginatedAggregationResultDto.FIELD_PAGINATION}.${AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL}").elementAt(0)
            aggregationOperationList.add(Aggregation.addFields().addFieldWithValue(AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL, elementAt).build())
        }

        private fun addedProjectFields(
            aggregationOperationList: MutableList<AggregationOperation>
        ) =
            aggregationOperationList.add(
                Aggregation.project(
                    AbstractPaginatedAggregationResultDto.FIELD_PAGINATION_TOTAL,
                    AbstractPaginatedAggregationResultDto.FIELD_DATA
                )
            )

    }
}