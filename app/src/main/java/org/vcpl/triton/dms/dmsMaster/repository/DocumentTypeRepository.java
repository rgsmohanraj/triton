package org.vcpl.triton.dms.dmsMaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentTypeEntity,Long> {
}
