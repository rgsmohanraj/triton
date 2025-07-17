package org.vcpl.triton.dms.dmsMaster.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dms_document_types")
public class DocumentTypeEntity {
    @Column(name = "id", nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "seq_no", nullable = false)
    public int sequenceNo;

    @Column(name = "displayName", nullable = false)
    public String displayName;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "status", nullable = false)
    public int status;

    @Column(name = "type", nullable = false)
    public int type;

    @Column(name = "createdBy")
    public String createdBy;

    @Column(name = "createdAt")
    public Timestamp createdAt;

    @Column(name = "updatedBy")
    public String updatedBy;

    @Column(name = "updatedAt")
    public Timestamp updatedAt;

    @OneToMany(mappedBy = "documentTypeEntity", fetch = FetchType.LAZY)
    @OrderBy(value="seq_no")
    private Set<DocumentCategoryEntity> documentCategoryEntities = new HashSet<>();

    public DocumentTypeEntity(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(int sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Set<DocumentCategoryEntity> getDocumentCategoryEntities() {
        return documentCategoryEntities;
    }

    public void setDocumentCategoryEntities(Set<DocumentCategoryEntity> documentCategoryEntities) {
        this.documentCategoryEntities = documentCategoryEntities;
    }
}
