package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.Mentee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Mentee} entity.
 */
public interface MenteeSearchRepository extends ElasticsearchRepository<Mentee, Long> {
}
