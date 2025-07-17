package org.vcpl.triton.anchor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "anchor_beneficiary")
public class BeneficiaryEntity {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "ben_type", nullable = false)
    private String benType;

    @Column(name = "ben_name", nullable = false)
    private String benName;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "bank_name", nullable = false)
    @NotNull(message = "bankName shouldn't be null")
    private String bankName;

    @Column(name = "bank_acct_number", nullable = false)
//    @Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Enter Valid bankAcctNumber")
    private String bankAcctNumber;

    @Column(name = "bank_ifsc_code", nullable = false)
//    @Pattern(regexp="^[A-Z]{4}0[A-Z0-9]{6}$",message = "Enter Valid bankIfscCode")
    private String bankifscCode;

    @Column(name = "bank_branch_code")
    private String bankBranchCode;

    @Column(name = "bank_branch_name", nullable = false)
    private String bankBranchName;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
