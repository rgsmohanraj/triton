package org.vcpl.triton.anchor.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.counterParty.entity.CreditPolicyDetailsEntity;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="application_details_info")
public class ApplicationEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "seq_no", nullable = false)
    private int seqNo;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "app_type", nullable = false)
    private int appType;

    @Column(name = "wf_type", nullable = false)
    private int wfType;

    @Column(name = "created_at", nullable = true)
    private LocalDate createdAt;

    @Column(name = "created_by", nullable = true)
    private String createdBy;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

    @Column(name = "updated_at", nullable = true)
    private LocalDate updatedAt;

    @ManyToOne(targetEntity = CustomerInfoEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "cust_id")
    private CustomerInfoEntity customerInfoEntity;

    @OneToMany(targetEntity = AnchorAddressEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<AnchorAddressEntity> anchorAddressDetailsEntityList;

    @OneToMany(targetEntity = AnchorAuthorizedEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<AnchorAuthorizedEntity> anchorAuthorizedEntities;

    @OneToMany(targetEntity = AnchorKeyEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<AnchorKeyEntity> anchorKeyEntities;

    @OneToMany(targetEntity = ProgramNormsEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<ProgramNormsEntity> programDetailsEntities;

    @OneToMany(targetEntity = AnchorBasicEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<AnchorBasicEntity> anchorBasicDetails;

    @OneToMany(targetEntity = AnchorGstEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<AnchorGstEntity> anchorGstEntities;

    @OneToMany(targetEntity = BeneficiaryEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<BeneficiaryEntity> beneficiaryEntities;

    @OneToMany(targetEntity = TermSheetEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<TermSheetEntity> termSheetEntity;

    @OneToMany(targetEntity = ProposedProductsEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<ProposedProductsEntity> proposedProductsEntities;

    @OneToMany(targetEntity = CreditNormsDetailsEntity.class,fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<CreditPolicyDetailsEntity> counterPartyCreditPolicyEntities;


}
