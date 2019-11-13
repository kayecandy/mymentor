package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.EduInstitution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link EduInstitution} entity.
 */
public interface EduInstitutionSearchRepository extends ElasticsearchRepository<EduInstitution, Long> {
}
