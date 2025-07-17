package org.vcpl.triton.dms.dmsMaster.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;
import org.vcpl.triton.dms.dmsMaster.exception.ResourceNotFoundException;
import org.vcpl.triton.dms.dmsMaster.repository.DocumentTypeRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentTypeService implements  IDocumentType{
    @Autowired
    DocumentTypeRepository documentTypeRepository;

    @Override
    public List<DocumentTypeEntity> getDocumentType() {
        return documentTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "type"));
    }

    public Optional<DocumentTypeEntity> getDocumentTypeById(Long dtId) {
        if (!documentTypeRepository.existsById(dtId)) {
            throw new ResourceNotFoundException("Document Type with id " + dtId + " not found");
        }
        return documentTypeRepository.findById(dtId);
    }

    @Override
    public DocumentTypeEntity saveDocumentType(DocumentTypeEntity documentTypeEntity) {
        Date date = new Date();
        documentTypeEntity.setCreatedAt(new Timestamp(date.getTime()));
        return documentTypeRepository.save(documentTypeEntity);
    }

    @Override
    public DocumentTypeEntity updateDocumentType(Long dtId, DocumentTypeEntity documentTypeRequest) {
        if (!documentTypeRepository.existsById(dtId)) {
            throw new ResourceNotFoundException("Document with id " + dtId + " not found");
        }
        Optional<DocumentTypeEntity> documentTypeEntity = documentTypeRepository.findById(dtId);

        if (!documentTypeEntity.isPresent()) {
            throw new ResourceNotFoundException("Document with id " + dtId + " not found");
        }
        Date date = new Date();
        DocumentTypeEntity documentTypeEntity1 = documentTypeEntity.get();
        documentTypeEntity1.setSequenceNo(documentTypeRequest.getSequenceNo());
        documentTypeEntity1.setDisplayName(documentTypeRequest.getDisplayName());
        documentTypeEntity1.setName(documentTypeRequest.getName());
        documentTypeEntity1.setStatus(documentTypeRequest.getStatus());
        documentTypeEntity1.setType(documentTypeRequest.getType());
        documentTypeEntity1.setCreatedBy(documentTypeRequest.getCreatedBy());
        documentTypeEntity1.setCreatedAt(documentTypeRequest.getCreatedAt());
        documentTypeEntity1.setUpdatedBy(documentTypeRequest.getUpdatedBy());
        documentTypeEntity1.setUpdatedAt(new Timestamp(date.getTime()));

        return documentTypeRepository.save(documentTypeEntity1);

    }

    @Override
    public ResponseEntity<Object> deleteDocumentTypeById(long dtId) {
        if (!documentTypeRepository.existsById(dtId)) {
            throw new ResourceNotFoundException("Document Type with id " + dtId + " not found");
        }
        documentTypeRepository.deleteById(dtId);
        return ResponseEntity.ok().build();
    }
}
