package org.vcpl.triton.anchor.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;

import javax.persistence.EntityManager;
import java.util.Collection;

@Repository
public interface AnchorBasicRepository extends JpaRepository<AnchorBasicEntity,Long> {


    @Query(value = "SELECT * FROM anchor_basic WHERE app_id = ?1",nativeQuery = true)
    Collection<AnchorBasicEntity> findByCiId(Long id);

    @Query(value="DELETE FROM anchor_basic WHERE app_id=?1",nativeQuery = true)
    void deleteByAppId(long id);


}
