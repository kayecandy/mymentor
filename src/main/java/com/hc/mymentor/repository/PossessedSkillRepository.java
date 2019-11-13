package com.hc.mymentor.repository;
import com.hc.mymentor.domain.PossessedSkill;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PossessedSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PossessedSkillRepository extends JpaRepository<PossessedSkill, Long> {

}
