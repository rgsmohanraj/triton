package org.vcpl.triton.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.vcpl.triton.notification.entity.EmailNotificationTemplateEntity;

import java.util.List;
import java.util.Optional;

public interface EmailNotificationTemplateRepository extends JpaRepository<EmailNotificationTemplateEntity,Long> {

    @Query(value = "SELECT * FROM email_notification_tamplate WHERE action=?1",nativeQuery = true)
    Optional<EmailNotificationTemplateEntity> getTemplateByAction(Long action);
    @Query(value = "SELECT * FROM email_notification_tamplate WHERE action=:action AND stage=:stage",nativeQuery = true)
    EmailNotificationTemplateEntity findNotificationBasedOneActionAndStatus(String action,String stage);

    @Query(value = "SELECT * FROM email_notification_tamplate WHERE action IN ?2 AND stage IN ?1",nativeQuery = true)
    List<EmailNotificationTemplateEntity> findNotificationBasedOneActionAndStatus(String[] action, String[] stage);
}
