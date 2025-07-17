package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.anchor.entity.CreditNormsDetailsEntity;

import java.util.List;

public interface CreditNormsDetailsRepository extends JpaRepository<CreditNormsDetailsEntity,Long> {

    @Query(value = "SELECT * FROM  credit_norms_details WHERE app_id = ?1",nativeQuery = true)
    List<CreditNormsDetailsEntity> findByCiIdArray(long id);

    @Query(value = "SELECT * FROM  credit_norms_details WHERE app_id = (Select id from application_details_info where cust_id = ?1 order by seq_no desc limit 1)",nativeQuery = true)
    List<CreditNormsDetailsEntity> findByCreditNorms(long id);
}
