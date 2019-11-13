package com.hc.mymentor.repository;
import com.hc.mymentor.domain.Mentee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Mentee entity.
 */
@Repository
public interface MenteeRepository extends JpaRepository<Mentee, Long> {

    @Query(value = "select distinct mentee from Mentee mentee left join fetch mentee.possessedSkills",
        countQuery = "select count(distinct mentee) from Mentee mentee")
    Page<Mentee> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct mentee from Mentee mentee left join fetch mentee.possessedSkills")
    List<Mentee> findAllWithEagerRelationships();

    @Query("select mentee from Mentee mentee left join fetch mentee.possessedSkills where mentee.id =:id")
    Optional<Mentee> findOneWithEagerRelationships(@Param("id") Long id);

}
