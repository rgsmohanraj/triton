package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface TermSheetRepository extends JpaRepository<TermSheetEntity,Long> {

    @Query(value = "SELECT * FROM cp_term_sheet WHERE app_id = ?1",nativeQuery = true)
    List<TermSheetEntity> findByAppId(Long id);

    @Query(value = "SELECT MIN(renewal_period) FROM cp_term_sheet WHERE app_id = ?1", nativeQuery = true)
    String getMinFacilityTenure(Long id);
}
