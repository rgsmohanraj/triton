package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;
import org.vcpl.triton.counterParty.entity.CollateralDetailsEntity;

import java.util.Collection;

import java.util.Collection;

@Repository
public interface CPBasicDetailsRepository extends JpaRepository<CPBasicDetailsEntity,Long> {

    @Query(value = "SELECT * FROM cp_basic_details WHERE app_id = ?1",nativeQuery = true)
    Collection<CPBasicDetailsEntity> findByFId(Long id);

    @Query(value = "SELECT * FROM cp_basic_details WHERE app_id = ?1",nativeQuery = true)
    CPBasicDetailsEntity findByFIdDetails(Long id);

    @Query(value = "SELECT * FROM cp_basic_details WHERE app_id  in (SELECT id FROM application_details_info WHERE cust_id =?1)",nativeQuery = true)
    CPBasicDetailsEntity findCpBasicDetails(Long id);


}
