package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.DueDiligenceDetailsEntity;
import org.vcpl.triton.counterParty.entity.DueDiligenceMasterEntity;

import java.util.Collection;

@Repository
public interface DueDiligenceDetailsRepository extends JpaRepository<DueDiligenceDetailsEntity, Long> {

    @Query(value = "SELECT * FROM cp_dd_details WHERE app_id = ?1",nativeQuery = true)
    Collection<DueDiligenceDetailsEntity> findByFid(Long id);
}
