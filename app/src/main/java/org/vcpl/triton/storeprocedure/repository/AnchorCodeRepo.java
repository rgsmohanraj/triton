package org.vcpl.triton.storeprocedure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.vcpl.triton.storeprocedure.entity.AnchorCodeEntity;

public interface AnchorCodeRepo extends JpaRepository<AnchorCodeEntity,Long > {
}
