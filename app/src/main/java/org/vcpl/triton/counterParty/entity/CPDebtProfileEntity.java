package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.counterParty.entity.CPBasicDetailsEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_debt_profile")
public class CPDebtProfileEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

//    @Column(name = "seq", nullable = true)
//    private String seq;

    @Column(name="bank_FI")
    private String bankFI;

    @Column(name = "facility_type", nullable = false)
    private String facilityType;

    @Column(name = "tenure", nullable = false)
    private String tenure;

    @Column(name = "sanction_date", nullable = false)
    private String sanctionDate;

    @Column(name = "sanction_limit", nullable = false)
    private String sanctionLimit;

    @Column(name = "outstanding_on_date", nullable = false)
    private String outstandingOnDate;

    @Column(name = "emi", nullable = false)
    private String emi;

    @Column(name = "interest_rate", nullable = false)
    private String interestRate;

    @Column(name = "security", nullable = false)
    private String security;

    @Column(name = "specific_limit", nullable = false)
    private String specificLimit;

    @Column(name = "cmpdInt", nullable = true)
    private String cmpdInt = "Yes";

    @Column(name = "cmpdOvdInt", nullable = true)
    private String cmpdOvdInt = "No";

    @Column(name = "remarks", nullable = false)
    private String remarks;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
