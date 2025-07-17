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
@Table(name ="cp_collateral_details")
public class CollateralDetailsEntity {

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

    @ManyToOne(targetEntity = CollateralMasterEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "cm_id")
    private CollateralMasterEntity collateralMasterEntity;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;
}
