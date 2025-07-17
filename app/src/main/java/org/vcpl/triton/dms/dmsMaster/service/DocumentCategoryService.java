package org.vcpl.triton.dms.dmsMaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentCategoryEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;
import org.vcpl.triton.dms.dmsMaster.exception.ResourceNotFoundException;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentCategoryRepository;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentTypeRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DocumentCategoryService implements IDocumentCategory{
    @Autowired
    DocumentCategoryRepository documentCategoryRepository;

    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public List<DocumentCategoryEntity> getAllCategory() {
        return documentCategoryRepository.findAll();
    }

    @Override
    public Optional<DocumentCategoryEntity> getDocumentCategoryById(Long dcId) {
        if (!documentCategoryRepository.existsById(dcId)) {
            throw new ResourceNotFoundException("Document Category " + dcId + " not found");
        }
        return documentCategoryRepository.findById(dcId);
    }

    @Override
    public DocumentCategoryEntity createDocumentCategory(Long dtId, DocumentCategoryEntity documentCategoryEntity) {
        Set<DocumentCategoryEntity> documentCategoryEntities = new HashSet<>();
        DocumentTypeEntity documentTypeEntity = new DocumentTypeEntity();

        Optional<DocumentTypeEntity> byId = documentTypeRepository.findById(dtId);
        if (!byId.isPresent()) {
            throw new ResourceNotFoundException("Document Type with Id :" + dtId + " does not exist");
        }
        DocumentTypeEntity documentTypeEntity1 = byId.get();

        documentCategoryEntity.setDocumentTypeEntity(documentTypeEntity1);

        DocumentCategoryEntity documentCategoryEntity1 = documentCategoryRepository.save(documentCategoryEntity);

        documentCategoryEntities.add(documentCategoryEntity1);

        documentTypeEntity.setDocumentCategoryEntities(documentCategoryEntities);

        return documentCategoryEntity;
    }

    @Override
    public DocumentCategoryEntity updateDocumentCategory(Long dcId, DocumentCategoryEntity documentCategoryRequest) {
        if (!documentCategoryRepository.existsById(dcId)) {
            throw new ResourceNotFoundException("Document Category with id " + dcId + " not found");
        }
        Optional<DocumentCategoryEntity> documentCategoryEntity = documentCategoryRepository.findById(dcId);
        if (!documentCategoryEntity.isPresent()) {
            throw new ResourceNotFoundException("Document Category with id " + dcId + " not found");
        }

        DocumentCategoryEntity documentCategoryEntity1 = documentCategoryEntity.get();
        documentCategoryEntity1.setSequenceNo(documentCategoryRequest.getSequenceNo());
        documentCategoryEntity1.setDisplayName(documentCategoryRequest.getDisplayName());
        documentCategoryEntity1.setName(documentCategoryRequest.getName());
        documentCategoryEntity1.setCategory(documentCategoryRequest.getCategory());
        documentCategoryEntity1.setStatus(documentCategoryRequest.getStatus());

        return documentCategoryRepository.save(documentCategoryEntity1);
    }

    @Override
    public ResponseEntity<Object> deleteDocumentCategoryById(long dcId) {
        if (!documentCategoryRepository.existsById(dcId)) {
            throw new ResourceNotFoundException("Document Category with id " + dcId + " not found");
        }
        documentCategoryRepository.deleteById(dcId);
        return ResponseEntity.ok().build();
    }
}

