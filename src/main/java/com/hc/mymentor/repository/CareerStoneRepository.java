package com.hc.mymentor.repository;
import com.hc.mymentor.domain.CareerStone;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CareerStone entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerStoneRepository extends JpaRepository<CareerStone, Long> {

}
