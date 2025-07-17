package org.vcpl.triton.anchor.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "anchor_authorized")
public class AnchorAuthorizedEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name shouldn't be null in Authorized Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "Name should contain only Alphabets in Authorized Sheet")
    @NotNull(message = "Name shouldn't be Empty in Authorized Sheet")
    private String name;

    @Column(name = "type", nullable = false)
    @NotBlank(message = "type shouldn't be null in Authorized Sheet")
    @Pattern(regexp = "^[a-zA-Z ]*$",message = "type should contain only Alphabets in Authorized Sheet")
    @NotNull(message = "type shouldn't be null in Authorized Sheet")
    private String type;

    @Column(name = "mobile", nullable = false)
    @NotBlank(message = "mobile shouldn't be null in Authorized Sheet")
    @Pattern(regexp = "[0-9]{10}",message = "Name should contain only Alphabets in Authorized Sheet")
    private String mobile;

    @Column(name = "email_id", nullable = false)
    @NotBlank(message = "emailId shouldn't be null in Authorized Sheet")
    @Email(message = "Enter valid EmailId in Authorized Sheet")
    @Pattern(regexp = "^([a-zA-Z0-9_.+-]+)@([a-zA-Z0-9_.+-]+\\.(com|co.in|in)$)",message = "Please Enter valid Email-id in Authorized Sheet")
    private String emailId;

    @Column(name = "indemnity_doc", nullable = true)
//    @NotNull(message = "indemnityDoc shouldn't be null")
    private String indemnityDoc;

    @Column(name = "created_by",nullable = true)
    private String createdBy;

    @Column(name = "updated_by",nullable = true)
    private String updatedBy;

    @Column(name = "created_at",nullable = true)
    private Timestamp createdAt;

    @Column(name = "updated_at",nullable = true)
    private Timestamp updatedAt;

    @ManyToOne(targetEntity = ApplicationEntity.class, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    @JoinColumn(name = "app_id")
    private ApplicationEntity applicationEntity;

}
