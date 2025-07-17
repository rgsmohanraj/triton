package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "soft_policy_master_sub_type")
public class SoftPolicyMasterSubTypeEntity {
    @Column(name = "ID", nullable = false, length = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "sequance_number")
    private String sequanceNo;

    @Column(name = "data_type")
    public String dataType;

    @Column(name = "regex")
    public String regex;

    @Column(name = "mandatory")
    public String mandatory;

    @Column(name = "createdBy")
    public String createdBy;

    @Column(name = "createdAt")
    public Timestamp createdAt;

    @Column(name = "updatedBy")
    public String updatedBy;

    @Column(name = "updatedAt")
    public Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soft_policy_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity;

    @OneToMany(mappedBy = "softPolicyMasterSubTypeEntity", fetch = FetchType.LAZY)
    private Set<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities = new HashSet<>();

    public SoftPolicyMasterSubTypeEntity(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSequanceNo() {
        return sequanceNo;
    }

    public void setSequanceNo(String sequanceNo) {
        this.sequanceNo = sequanceNo;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getSoftPolicyTypeId(){return softPolicyMasterTypeEntity.getId();}

    public  String getSoftPolicyTypeName(){ return softPolicyMasterTypeEntity.getName();}


    @JsonIgnore
    public SoftPolicyMasterTypeEntity getSoftPolicyMasterTypeEntity() {
        return softPolicyMasterTypeEntity;
    }

    @JsonIgnore
    public void setSoftPolicyMasterTypeEntity(SoftPolicyMasterTypeEntity softPolicyMasterTypeEntity) {
        this.softPolicyMasterTypeEntity = softPolicyMasterTypeEntity;
    }

    public Set<SoftPolicyFilterMasterEntity> getSoftPolicyFilterMasterEntities() {
        return softPolicyFilterMasterEntities;
    }

    public void setSoftPolicyFilterMasterEntities(Set<SoftPolicyFilterMasterEntity> softPolicyFilterMasterEntities) {
        this.softPolicyFilterMasterEntities = softPolicyFilterMasterEntities;
    }
}
