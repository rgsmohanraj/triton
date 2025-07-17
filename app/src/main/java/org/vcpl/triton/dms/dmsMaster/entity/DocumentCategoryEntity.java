package org.vcpl.triton.dms.dmsMaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dms_document_category")
public class DocumentCategoryEntity {
    @Column(name = "ID", nullable = false, length = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "seq_no")
    private int sequenceNo;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "status")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DocumentTypeEntity documentTypeEntity;

    @OneToMany(mappedBy = "documentCategoryEntity", fetch = FetchType.LAZY)
    @OrderBy(value="seq_no")
    private Set<DocumentListEntity> documentListEntities = new HashSet<>();


    public DocumentCategoryEntity(){
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getDocumentTypeId(){return documentTypeEntity.getId();}

    public String getDocumentTypeName(){return documentTypeEntity.getName();}

    @JsonIgnore
    public DocumentTypeEntity getDocumentTypeEntity() {
        return documentTypeEntity;
    }

    @JsonIgnore
    public void setDocumentTypeEntity(DocumentTypeEntity documentTypeEntity) {
        this.documentTypeEntity = documentTypeEntity;
    }

    public Set<DocumentListEntity> getDocumentListEntities() {
        return documentListEntities;
    }

    public void setDocumentListEntities(Set<DocumentListEntity> documentListEntities) {
        this.documentListEntities = documentListEntities;
    }
}
