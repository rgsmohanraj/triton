package org.vcpl.triton.rbac.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name ="rbac_user")
public class UserEntity {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @ManyToOne(targetEntity = GroupEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "group_id")
    private GroupEntity groupId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public GroupEntity getGroupId() {
        return groupId;
    }

    public void setGroupId(GroupEntity groupId) {
        this.groupId = groupId;
    }
}
