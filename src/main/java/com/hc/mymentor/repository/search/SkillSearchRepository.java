package com.hc.mymentor.repository.search;
import com.hc.mymentor.domain.Skill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Skill} entity.
 */
public interface SkillSearchRepository extends ElasticsearchRepository<Skill, Long> {
}
