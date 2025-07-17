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
@Table(name ="keycloak_group")
public class KeycloakGroupsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "group_name", nullable = false)
    private String groupName;

    @Column(name = "group_path", nullable = false)
    private String groupPath;

    @Column(name = "sub_group", nullable = false)
    private String[] subGroup;


}
