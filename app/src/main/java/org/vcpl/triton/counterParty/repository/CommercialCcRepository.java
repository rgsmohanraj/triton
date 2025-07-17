package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.BeneficiaryEntity;
import org.vcpl.triton.counterParty.entity.CommercialEntity;

import java.util.Collection;

@Repository
public interface CommercialCcRepository extends JpaRepository<CommercialEntity, Long>{
    @Query(value = "SELECT * FROM cp_commercial_table WHERE app_id = ?1",nativeQuery = true)
    Collection<CommercialEntity> findByCiId(Long id);
}
