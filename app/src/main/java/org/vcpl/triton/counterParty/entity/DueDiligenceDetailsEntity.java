package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_dd_details")
public class DueDiligenceDetailsEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "createdBy", nullable = true)
    private String createdBy;

    @Column(name = "updatedBy", nullable = true)
    private String updatedBy;


    @Column(name = "createdAt", nullable = true)
    private Timestamp createdAt;

    @Column(name = "updatedAt", nullable = true)
    private Timestamp updatedAt;


    @ManyToOne(targetEntity = DueDiligenceMasterEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "ddMaster_id")
    private DueDiligenceMasterEntity dueDiligenceMasterEntity;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;
}
