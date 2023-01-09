package br.com.mirantebackend.dao.usecases.news

import br.com.mirantebackend.dao.aggregationDto.pagination.NewsPaginatedAggregationResultDto
import br.com.mirantebackend.dao.usecases.interfaces.news.FindNewsUseCase
import br.com.mirantebackend.dao.utils.AggregationUtils
import br.com.mirantebackend.model.documents.NewsDocument
import mu.KotlinLogging
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationOperation
import org.springframework.stereotype.Service
import java.util.*

@Service
class FindNewsUseCaseImpl : FindNewsUseCase {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    override fun findById(newsId: String, mongoTemplate: MongoTemplate): Optional<NewsDocument> =
        logger.info { "Finding news from id $newsId" }
            .let {
                Optional.ofNullable(mongoTemplate.findById(newsId, NewsDocument::class.java))
            }

    override fun findByAll(pageSize: Int, pageNumber: Int, mongoTemplate: MongoTemplate): Page<NewsDocument> {
        logger.info { "Finding all news" }
        return mutableListOf<AggregationOperation>()
            .also { it.add(Aggregation.sort(Sort.by(Sort.Direction.ASC, NewsDocument.FIELD_CREATED_AT))) }
            .let { AggregationUtils.buildNewPaginatedAggregation(pageNumber, pageSize, it) }
            .let { newAggregation ->
                Optional.ofNullable(
                    mongoTemplate.aggregate(
                        newAggregation,
                        NewsDocument::class.java,
                        NewsPaginatedAggregationResultDto::class.java
                    ).uniqueMappedResult
                ).map { result ->
                    val data = Optional.ofNullable(result.data)
                        .map { it.stream().toList() }
                        .orElse(emptyList())
                    PageImpl(data, PageRequest.of(pageNumber, pageSize), result.total)
                }.orElse(PageImpl(emptyList(), PageRequest.of(pageNumber, pageSize), 0))
            }
    }
}