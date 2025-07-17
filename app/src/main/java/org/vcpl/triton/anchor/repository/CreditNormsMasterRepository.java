package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.CreditNormsMasterEntity;

@Repository
public interface CreditNormsMasterRepository extends JpaRepository<CreditNormsMasterEntity,Long> {
}
