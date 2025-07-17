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
@Table(name ="cp_basic_details")
public class CPBasicDetailsEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "PAN", nullable = false)
    private String panNumber;

    @Column(name="company_name",nullable = false)
    private String companyName;

    @Column(name="GST_number",nullable = true)
    private String gstNumber;

    @Column(name="CIN_number",nullable = true)
    private String cinNumber;

    @Column(name="constitution",nullable = false)
    private String constitution;

    @Column(name="city",nullable = false)
    private String city;

    @Column(name="state",nullable = false)
    private String state;

    @Column(name="source",nullable = false)
    private String source;

    @Column(name="sub_source",nullable = false)
    private String subSource;

    @Column(name="rm_name",nullable = false)
    private String rmName;

    @Column(name="cus_contact_name",nullable = false)
    private String cusContName;

    @Column(name="cus_contact_number",nullable = false)
    private String cusContactNumber;

    @Column(name="cus_contact_email",nullable = false)
    private String cusContactEmail;

    @Column(name = "activity",nullable = false)
    private String activity;

    @Column(name = "assessment_type",nullable = true)
    private String assessmentType;

    @Column(name = "anchor_relationShip",nullable = true)
    private String anchorRelationShip;

    @Column(name = "total_inward_cheques",nullable = true)
    private String totalInwardCheques;

    @Column(name = "counter_party_type",nullable = true)
    private String counterPartyType;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
