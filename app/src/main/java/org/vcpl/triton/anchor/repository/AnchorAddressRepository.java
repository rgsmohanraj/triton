package org.vcpl.triton.anchor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;

import java.util.Collection;
import java.util.List;

@Repository

public interface AnchorAddressRepository extends JpaRepository<AnchorAddressEntity,Long> {

    @Query(value = "SELECT * FROM anchor_address WHERE app_id = ?1",nativeQuery = true)
    Collection<AnchorAddressEntity> findByCiId(Long id);

    @Query(value="DELETE FROM anchor_address WHERE app_id=?1",nativeQuery = true)
    void deleteByAppId(long id);

    @Query(value = "SELECT * FROM pin_code WHERE pincode = ?1",nativeQuery = true)
    List<Object[]> findByPinCode(String pinCode);
}
