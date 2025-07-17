package org.vcpl.triton.counterParty.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.counterParty.entity.FundReqMasterEntity;

@Repository
public interface FundReqMasterRepository extends JpaRepository<FundReqMasterEntity,Long> {
}
