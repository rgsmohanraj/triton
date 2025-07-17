package org.vcpl.triton.scheduler.data;

import com.amazonaws.annotation.Immutable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.dms.dmsMaster.entity.DocumentListEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;



@Entity
@Data
@Immutable
@NamedStoredProcedureQuery(
        name = "myStoredProcedure",
        procedureName = "view_deferral_document_report")
public class DeferralReportData {
        @Id
        @GeneratedValue
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
        @JoinColumn(name = "app_id")
        private ApplicationEntity applicationEntity;

        @ManyToOne(targetEntity = DocumentListEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
        @JoinColumn(name = "doc_list_id")
        private DocumentListEntity documentListEntity;

//        @Column(name = "document_name")
//        private String documentName;

        @Column(name = "initial_time")
        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate initialTime;

        @Column(name = "revised_time")
        @DateTimeFormat(pattern = "yyyy/MM/dd")
        private LocalDate revisedTime;

        @Column(name = "status")
        private int status;

        @Column(name = "document_id")
        private String documentId;

        @Column(name = "doc_completion_date")
        private LocalDate docCompletionDate;

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

        @Column(name = "data_view")
        private String dataView;

}
