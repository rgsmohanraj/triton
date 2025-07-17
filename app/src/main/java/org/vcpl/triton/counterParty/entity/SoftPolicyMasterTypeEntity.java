package org.vcpl.triton.counterParty.entity;


import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "soft_policy_master_type")
public class SoftPolicyMasterTypeEntity {
    @Column(name = "ID", nullable = false, length = 10)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "displayName")
    public String displayName;

    @Column(name = "sequanceNo")
    public String sequanceNo;

    @Column(name = "createdBy")
    public String createdBy;

    @Column(name = "createdAt")
    public Timestamp createdAt;

    @Column(name = "updatedBy")
    public String updatedBy;

    @Column(name = "updatedAt")
    public Timestamp updatedAt;

    @OneToMany(mappedBy = "softPolicyMasterTypeEntity", fetch = FetchType.LAZY)
    private Set<SoftPolicyMasterSubTypeEntity> softPolicyMasterSubTypeEntities = new HashSet<>();

    public SoftPolicyMasterTypeEntity(){
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

    public Set<SoftPolicyMasterSubTypeEntity> getSoftPolicyMasterSubTypeEntities() {
        return softPolicyMasterSubTypeEntities;
    }

    public void setSoftPolicyMasterSubTypeEntities(Set<SoftPolicyMasterSubTypeEntity> softPolicyMasterSubTypeEntities) {
        this.softPolicyMasterSubTypeEntities = softPolicyMasterSubTypeEntities;
    }
}
