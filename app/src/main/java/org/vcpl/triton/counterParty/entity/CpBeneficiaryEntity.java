package org.vcpl.triton.counterParty.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.vcpl.triton.anchor.entity.ApplicationEntity;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="cp_beneficiary_details")

public class CpBeneficiaryEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "cp_beneficiary_type", nullable = false)
    private String cpBeneficiaryType;

    @Column(name="cp_beneficiary_name",nullable = false)
    private String cpBeneficiaryName;

    @Column(name="cp_beneficiary_acct",nullable = false)
    private long cpBeneficiaryAcct;

    @Column(name="cp_beneficiary_acct_type",nullable = false)
    private String cpBeneficiaryAcctType;

    @Column(name="cp_beneficiary_ifsc",nullable = false)
    private String cpBeneficiaryIfsc;

    @Column(name="cp_beneficiary_bank_name",nullable = false)
    private String cpBeneficiaryBankName;

    @Column(name="cp_beneficiary_branch",nullable = false)
    private String cpBeneficiaryBranch;

    @Column(name="cp_beneficiary_micr",nullable = false)
    private String cpBeneficiaryMicr;

    @Column(name="cp_beneficiary_swift_code",nullable = false)
    private String cpBeneficiarySwiftCode;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
