package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.FundReqDetailsEntity;

import java.util.List;

@Repository
public interface FundReqDetailsRepository extends JpaRepository<FundReqDetailsEntity,Long> {

    @Query(value = "SELECT * FROM cp_fund_requirement_details where app_id = ?1",nativeQuery = true)
    List<FundReqDetailsEntity> findByFId(long appId);
}
