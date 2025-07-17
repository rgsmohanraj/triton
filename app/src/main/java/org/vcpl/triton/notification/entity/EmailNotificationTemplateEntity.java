package org.vcpl.triton.notification.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "email_notification_tamplate")
public class EmailNotificationTemplateEntity {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "stage",nullable = false)
    private String stage;
    @Column(name = "action",nullable = false)
    private String action;
    @Column(name = "subject",nullable = false)
    private String subject;
    @Column(name = "content",nullable = false)
    private String content;
}

