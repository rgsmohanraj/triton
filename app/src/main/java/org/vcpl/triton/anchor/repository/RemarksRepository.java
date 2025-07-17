package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.RemarksEntity;

import java.util.Collection;

@Repository
public interface RemarksRepository extends JpaRepository<RemarksEntity,Long> {
    @Query(value = "SELECT * FROM remarks WHERE ci_id = ?1",nativeQuery = true)
    Collection<RemarksEntity> findByCiId(Long id);
}
