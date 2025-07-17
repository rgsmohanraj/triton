package org.vcpl.triton.anchor.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional
@Repository
public class DeleteAnchorFileUpload {
    @Autowired
    EntityManager entityManager;

    public void deleteByAppId(long cusId,long appId){
            entityManager.createNativeQuery("DELETE FROM anchor_basic WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM anchor_address WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM anchor_authorized WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM anchor_program WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM anchor_gst WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM anchor_key WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM anchor_authorized WHERE app_id=:appId").setParameter("appId",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM application_details_info where id=:id").setParameter("id",appId).executeUpdate();
            entityManager.createNativeQuery("DELETE FROM customer_info where id=:id").setParameter("id",cusId).executeUpdate();
    }
}
