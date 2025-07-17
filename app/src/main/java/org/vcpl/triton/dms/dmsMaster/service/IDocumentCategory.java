package org.vcpl.triton.dms.dmsMaster.service;

import org.springframework.http.ResponseEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentCategoryEntity;

import java.util.List;
import java.util.Optional;

public interface IDocumentCategory {
    List<DocumentCategoryEntity> getAllCategory();

    Optional<DocumentCategoryEntity> getDocumentCategoryById(Long dcId);

    DocumentCategoryEntity createDocumentCategory(Long dtId, DocumentCategoryEntity documentCategoryEntity);

    DocumentCategoryEntity updateDocumentCategory(Long dstId, DocumentCategoryEntity documentCategoryEntity);

    ResponseEntity<Object> deleteDocumentCategoryById(long dcId);
}
