package com.hc.mymentor.repository;
import com.hc.mymentor.domain.EduStone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the EduStone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EduStoneRepository extends JpaRepository<EduStone, Long> {

}
