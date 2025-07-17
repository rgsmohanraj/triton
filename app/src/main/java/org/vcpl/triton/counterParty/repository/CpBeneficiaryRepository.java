package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;
import org.vcpl.triton.counterParty.entity.CpBeneficiaryEntity;

import java.util.Collection;

public interface CpBeneficiaryRepository extends JpaRepository<CpBeneficiaryEntity,Long> {

    @Query(value = "SELECT * FROM cp_basic_details WHERE app_id = ?1",nativeQuery = true)
    Collection<CpBeneficiaryEntity> findByFId(Long id);
}
