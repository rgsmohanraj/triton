package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.LimitEligibilityDetailsEntity;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;

import java.util.List;

@Repository

public interface LimitEligibilityDetailsRepository extends JpaRepository<LimitEligibilityDetailsEntity,Long> {
    @Query(value = "SELECT * FROM cp_limit_eligibility_details WHERE app_id = ?1",nativeQuery = true)
    List<LimitEligibilityDetailsEntity> findByAppId(Long id);
}
