package org.vcpl.triton.counterParty.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name ="soft_policy_filters")
public class SoftPolicyFilterMasterEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

//    @Column(name = "soft_policy_sub_type_id", nullable = false)
//    private Long softPolicySubTypeId;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "formula", nullable = false,length = 500)
    private String formula;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "soft_policy_sub_type_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getSoftPolicyTypeId(){return softPolicyMasterSubTypeEntity.getSoftPolicyMasterTypeEntity().getId();}

    public  String getSoftPolicyTypeDisplayName(){ return softPolicyMasterSubTypeEntity.getSoftPolicyMasterTypeEntity().getDisplayName();}

    public String getSoftPolicyTypeName(){return softPolicyMasterSubTypeEntity.getSoftPolicyMasterTypeEntity().getName();}

    public Long getSoftPolicySubTypeId(){ return softPolicyMasterSubTypeEntity.getId();}

    public  String getSoftPolicySubTypeDisplayName(){ return softPolicyMasterSubTypeEntity.getDisplayName();}

    public String getSoftPolicySubTypeName(){return  softPolicyMasterSubTypeEntity.getName();}

    public String getSoftPolicyDataType(){return softPolicyMasterSubTypeEntity.getDataType();}

    @JsonIgnore
    public SoftPolicyMasterSubTypeEntity getSoftPolicyMasterSubTypeEntity() {
        return softPolicyMasterSubTypeEntity;
    }

    @JsonIgnore
    public void setSoftPolicyMasterSubTypeEntity(SoftPolicyMasterSubTypeEntity softPolicyMasterSubTypeEntity) {
        this.softPolicyMasterSubTypeEntity = softPolicyMasterSubTypeEntity;
    }
}
