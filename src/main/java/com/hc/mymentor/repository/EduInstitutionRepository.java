package com.hc.mymentor.repository;
import com.hc.mymentor.domain.EduInstitution;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EduInstitution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EduInstitutionRepository extends JpaRepository<EduInstitution, Long> {

}
