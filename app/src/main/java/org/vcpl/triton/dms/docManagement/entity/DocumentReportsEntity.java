package org.vcpl.triton.dms.docManagement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentCategoryEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentTypeEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "dms_document_reports")
@Builder
public class DocumentReportsEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

    @ManyToOne(targetEntity = DocumentTypeEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "doc_type_id")
    private DocumentTypeEntity documentTypeEntity;

    @ManyToOne(targetEntity = DocumentCategoryEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "doc_category_id")
    private DocumentCategoryEntity documentCategoryEntity;

    @ManyToOne(targetEntity = DocumentListEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "doc_list_id")
    private DocumentListEntity documentListEntity;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "version")
    private String version;
}
