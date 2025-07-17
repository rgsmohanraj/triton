package org.vcpl.triton.dms.docManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="dms_other_document_master")
public class OtherDocumentMasterEntity {
    @Column(name = "id", nullable = false, length = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

    @ManyToOne(targetEntity = DocumentListEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "doc_list_id")
    private DocumentListEntity documentListEntity;

    @Column(name = "seq_no")
    private int sequenceNo;

    @Column(name = "deferralType")
    private int deferralType;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "name")
    private String name;

    @Column(name = "initial_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate initialTime;

    @Column(name = "revised_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate revisedTime;

    @Column(name = "new_revised_time")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate newRevisedTime;

    @Column(name = "status")
    private int status;

    @Column(name = "rm_name")
    private String rmName;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
