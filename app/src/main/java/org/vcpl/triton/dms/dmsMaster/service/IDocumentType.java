package org.vcpl.triton.dms.dmsMaster.service;

import org.springframework.http.ResponseEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;

import java.util.List;

public interface IDocumentType {
    List<DocumentTypeEntity> getDocumentType();

    DocumentTypeEntity saveDocumentType(DocumentTypeEntity documentTypeEntity);

    DocumentTypeEntity updateDocumentType(Long dtId, DocumentTypeEntity documentTypeEntity);

    ResponseEntity<Object> deleteDocumentTypeById(long dtId);
}
