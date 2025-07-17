package org.vcpl.triton.anchor.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="anchor_key")

public class AnchorKeyEntity {
    @Id
    @GeneratedValue
    @Column(name="id",nullable = false)
    private long id;

    @Column(name="type",nullable = false)
    @NotBlank(message = "type shouldn't be null in KeyPerson Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "type should contain only Alphabets in KeyPerson Sheet")
    private String type;

    @Column(name="name",nullable = false)
    @NotBlank(message = "name shouldn't be null in KeyPerson Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "name should contain only Alphabets in KeyPerson Sheet")
    private String name;

    @Column(name="email",nullable = false)
    @NotBlank(message = "emailId shouldn't be null in KeyPerson Sheet")
    @Email(message = "Enter valid mailId in KeyPerson Sheet")
    @Pattern(regexp = "^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\\.(com|co.in|in)$)",message = "Please Enter valid Email-id in KeyPerson Sheet")
    private String emailId;

    @Column(name="mobile",nullable = false)
    @NotBlank(message = "mobile shouldn't be null in KeyPerson Sheet")
    @Pattern(regexp = "[0-9]{10}",message = "Name should contain only Alphabets in KeyPerson Sheet")
    private String mobile;

    @Column(name = "created_by",nullable = true)
    private String createdBy;

    @Column(name = "updated_by",nullable = true)
    private String updatedBy;

    @Column(name = "created_at",nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at",nullable = true)
    private Timestamp updatedAt;

    @ManyToOne(targetEntity = ApplicationEntity.class,fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
