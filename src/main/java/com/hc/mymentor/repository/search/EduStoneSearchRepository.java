package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.EduStone;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EduStone} entity.
 */
public interface EduStoneSearchRepository extends ElasticsearchRepository<EduStone, Long> {
}
