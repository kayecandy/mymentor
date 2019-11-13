package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.PossessedSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link PossessedSkill} entity.
 */
public interface PossessedSkillSearchRepository extends ElasticsearchRepository<PossessedSkill, Long> {
}
