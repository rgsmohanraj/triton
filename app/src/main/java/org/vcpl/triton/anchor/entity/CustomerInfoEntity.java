package org.vcpl.triton.anchor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="customer_info")
public class CustomerInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "customer_name", nullable = false,length = 50)
    @NotNull(message = "customerName shouldn't be null in CustomerInfo Sheet")
    @NotBlank(message = "customerName shouldn't be null in CustomerInfo Sheet")
//    @Size(min=1, max=40, message="Customer Name should be between 2 to 20 characters")
//    @Pattern(regexp = "^[a-zA-Z ]*$",message = "customerName should contain only Alphabets")
    private String customerName;

    @Column(name = "product", nullable = false)
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "product should contain only Alphabets ")
    private String product;

    @Column(name = "pan",unique = true,nullable = false,length =10)
    @NotBlank(message = "pan shouldn't be null in CustomerInfo Sheet")
    @Pattern(regexp = "([a-zA-Z]){5}([0-9]){4}([a-zA-Z]){1}$",message = "Please Enter valid Pan Number")
    private String pan;

    @Column(name = "cin",nullable = true)
//    @NotBlank(message = "cin shouldn't be null in CustomerInfo Sheet")
//    @Pattern(regexp = "([L|U]{1})([0-9]{5})([A-Za-z]{2})([0-9]{4})([A-Za-z]{3})([0-9]{6})$",message = "Please Enter valid Cin Number ")
    private String cin;

    @Column(name ="dedupe_status",nullable = false)
    private String dedupeStatus;

    @Column(name ="status",nullable = false)
    private Boolean status;

    @Column(name ="business_expiry",nullable = true)
    private Date businessExpiry;

    @Column(name = "created_by",nullable = true)
    private String createdBy;

    @Column(name = "updated_by",nullable = true)
    private String updatedBy;

    @Column(name = "created_at",nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at",nullable = true)
    private Timestamp updatedAt;

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

//    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
//    @JoinColumn(name = "app_id")
//    private ApplicationEntity applicationEntity;


}
