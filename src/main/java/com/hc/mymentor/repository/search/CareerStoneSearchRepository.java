package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.CareerStone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CareerStone} entity.
 */
public interface CareerStoneSearchRepository extends ElasticsearchRepository<CareerStone, Long> {
}
