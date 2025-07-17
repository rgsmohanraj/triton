package org.vcpl.triton.dms.dmsMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;

@Repository
public interface DocumentListRepository  extends JpaRepository<DocumentListEntity,Long> {
}
