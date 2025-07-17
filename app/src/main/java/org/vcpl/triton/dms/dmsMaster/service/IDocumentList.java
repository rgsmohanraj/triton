package org.vcpl.triton.dms.dmsMaster.service;

import org.springframework.http.ResponseEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;

import java.util.List;

public interface IDocumentList {
    List<DocumentListEntity> getAllDocumentList();

    DocumentListEntity findByDocumentListId(Long dlId);

    DocumentListEntity saveDocumentList(Long dcId,DocumentListEntity documentListEntity);

    DocumentListEntity updateDocumentList(DocumentListEntity documentListEntity, Long id);

    ResponseEntity<Object> deleteDocumentList(long dlId);
}
