package org.vcpl.triton.counterParty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.counterParty.entity.AssignUnderwriterEntity;

import java.util.Collection;
import java.util.Optional;

public interface AssignUnderwriterRepository extends JpaRepository<AssignUnderwriterEntity,Long> {

    @Query(value = "SELECT * FROM assign_underwriter WHERE app_id = ?1",nativeQuery = true)
    Optional<AssignUnderwriterEntity> findByAppId(Long id);
}
