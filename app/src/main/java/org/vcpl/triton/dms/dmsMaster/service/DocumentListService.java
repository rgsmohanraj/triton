package org.vcpl.triton.dms.dmsMaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentCategoryEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;
import org.vcpl.triton.dms.dmsMaster.exception.ResourceNotFoundException;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentCategoryRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentListRepository;

import java.sql.Timestamp;
import java.util.*;

@Service
public class DocumentListService implements IDocumentList{
    @Autowired
    DocumentListRepository documentListRepository;

    @Autowired
    DocumentCategoryRepository documentCategoryRepository;

    @Override
    public List<DocumentListEntity> getAllDocumentList() {
        return documentListRepository.findAll();
    }

    @Override
    public DocumentListEntity findByDocumentListId(Long dlId) {
        return documentListRepository.findById(dlId).orElse(null);
    }

    @Override
    public DocumentListEntity saveDocumentList(Long dcId,DocumentListEntity documentListEntity) {
        Set<DocumentListEntity> documentListEntities = new HashSet<>();
        DocumentCategoryEntity documentCategoryEntity = new DocumentCategoryEntity();

        Optional<DocumentCategoryEntity> byId = documentCategoryRepository.findById(dcId);
        if (!byId.isPresent()) {
            throw new ResourceNotFoundException("Document Category with Id :" + dcId + " does not exist");
        }
        DocumentCategoryEntity documentCategoryEntity1 = byId.get();

        documentListEntity.setDocumentCategoryEntity(documentCategoryEntity1);
        Date date = new Date();
        documentListEntity.setCreatedAt(new Timestamp(date.getTime()));

        DocumentListEntity documentListEntity1 = documentListRepository.save(documentListEntity);

        documentListEntities.add(documentListEntity1);

        documentCategoryEntity.setDocumentListEntities(documentListEntities);

        return documentListEntity;
    }

    @Override
    public DocumentListEntity updateDocumentList(DocumentListEntity documentListRequest, Long dlId) {
        if (!documentListRepository.existsById(dlId)) {
            throw new ResourceNotFoundException("Document List with id " + dlId + " not found");
        }
        Optional<DocumentListEntity> documentListEntity = documentListRepository.findById(dlId);
        if (!documentListEntity.isPresent()) {
            throw new ResourceNotFoundException("Document List with id " + dlId + " not found");
        }

        Date date = new Date();
        DocumentListEntity documentListEntity1 = documentListEntity.get();
        documentListEntity1.setSequenceNo(documentListRequest.getSequenceNo());
        documentListEntity1.setDisplayName(documentListRequest.getDisplayName());
        documentListEntity1.setName(documentListRequest.getName());
        documentListEntity1.setCategory(documentListRequest.getCategory());
        documentListEntity1.setMandatory(documentListRequest.getMandatory());
        documentListEntity1.setStatus(documentListRequest.getStatus());
        documentListEntity1.setVersion(documentListRequest.getVersion());
        documentListEntity1.setUpdatedAt(new Timestamp(date.getTime()));

        return documentListRepository.save(documentListEntity1);
    }

    @Override
    public ResponseEntity<Object> deleteDocumentList(long dlId) {
        if (!documentListRepository.existsById(dlId)) {
            throw new ResourceNotFoundException("Document List with id " + dlId + " not found");
        }
        documentListRepository.deleteById(dlId);
        return ResponseEntity.ok().build();
    }
}
