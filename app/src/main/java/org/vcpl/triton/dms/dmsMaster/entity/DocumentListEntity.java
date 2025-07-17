package org.vcpl.triton.dms.dmsMaster.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="dms_document_list")
public class DocumentListEntity {
    @Column(name = "id", nullable = false, length = 10)
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

    @Column(name = "mandatory", nullable = false)
    private int mandatory;

    @Column(name = "status")
    private int status;

    @Column(name = "deferral")
    private int deferral;

    @Column(name = "deferral_time")
    private long deferralTime;

    @Column(name = "type")
    private int type;

    @Column(name = "version")
    private String version;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DocumentCategoryEntity documentCategoryEntity;

    public  DocumentListEntity(){
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

    public int getMandatory() {
        return mandatory;
    }

    public void setMandatory(int mandatory) {
        this.mandatory = mandatory;
    }

    public int getStatus() {
        return status;
    }

    public int getDeferral() {
        return deferral;
    }

    public void setDeferral(int deferral) {
        this.deferral = deferral;
    }

    public long getDeferralTime() {
        return deferralTime;
    }

    public void setDeferralTime(long deferralTime) {
        this.deferralTime = deferralTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setStatus(int status) {
        this.status = status;
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

    public Long getDocumentCategoryId(){return documentCategoryEntity.getId();}

    public String getDocumentCategoryName(){return documentCategoryEntity.getName();}

    @JsonIgnore
    public DocumentCategoryEntity getDocumentCategoryEntity() {
        return documentCategoryEntity;
    }

    @JsonIgnore
    public void setDocumentCategoryEntity(DocumentCategoryEntity documentCategoryEntity) {
        this.documentCategoryEntity = documentCategoryEntity;
    }
}
