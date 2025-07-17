package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.counterParty.entity.CPDebtProfileEntity;

import java.util.Collection;

@Repository
public interface CPDebtProfileRepository extends JpaRepository<CPDebtProfileEntity,Long> {

//    @Query(value = "SELECT * FROM cp_debt_profile WHERE ci_id = ?1",nativeQuery = true)
//    Collection<CPDebtProfileEntity> findByCiId(long id);

    @Query(value = "SELECT * FROM cp_debt_profile WHERE app_id = ?1 order by id",nativeQuery = true)
    Collection<CPDebtProfileEntity> findByFid(long id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM cp_debt_profile where id=?1",nativeQuery = true)
    void deleteDebtProfile(long id);
}
