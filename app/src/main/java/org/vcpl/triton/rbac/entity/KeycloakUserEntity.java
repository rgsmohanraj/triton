///*
//package org.vcpl.triton.rbac.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//import java.sql.Timestamp;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//@Table(name ="keycloak_user")
//public class KeycloakUserEntity {
//
//    @Id
//    @GeneratedValue
//    @Column(name = "id", nullable = false)
//    private long id;
//
//    @Column(name = "user_id", nullable = false)
//    private String userId;
//
//    @Column(name = "email_verified", nullable = false)
//    private boolean isVerified;
//
//    @Column(name = "username", nullable = false)
//    private String username;
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "user_creation_timestamp")
//    private Timestamp userCreationTimestamp;
//
//    @ManyToOne(targetEntity = KeycloakGroupsEntity.class,fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "group_id")
//    private KeycloakGroupsEntity groupId;
//
//}
//*/
