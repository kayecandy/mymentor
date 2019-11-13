package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.Language;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Language} entity.
 */
public interface LanguageSearchRepository extends ElasticsearchRepository<Language, Long> {
}
