package org.vcpl.triton.dms.docManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.anchor.entity.ApplicationEntity;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.dms.docManagement.service.DeferralReportsService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DeferralReportsRepository extends JpaRepository<DeferralReportsEntity,Long> {

    @Query(value = "SELECT * FROM dms_deferral_document_reports WHERE app_id=?1",nativeQuery = true)
    Collection<DeferralReportsEntity> getDeferralReport(Long appId);

    @Query(value = "SELECT * FROM dms_deferral_document_reports WHERE app_id=:appId and status=:status",nativeQuery = true)
    Collection<DeferralReportsEntity> getDeferralReport(Long appId,int status);

    @Query(value = "SELECT * FROM dms_deferral_document_reports WHERE app_id=:appId and (status=3 OR status=1 OR status=0)",nativeQuery = true)
    Collection<DeferralReportsEntity> getDeferralReportsOpsMaker(Long appId);

    @Query(value ="SELECT * FROM dms_deferral_document_reports WHERE app_id=?1 AND status IN (0,1)",nativeQuery = true)
    public List<DeferralReportsEntity> getCompeletedDeferralDataByAppIdAndStatus(Long appId);

    //@Query(value = "SELECT * FROM application_details_info where type=:type AND (id in (select app_id from triton.deferral_document_reports where status=1));",nativeQuery = true)
    @Query(value = "SELECT def.*,app.id AS applicationId FROM dms_deferral_document_reports AS def,application_details_info AS app" +
            " WHERE app.id = def.app_id AND def.status=:status AND app.type=:type"+
            " group by app.id", nativeQuery=true)
    Collection<DeferralReportsEntity> getDeferralDocs(Integer type, Integer status);

    @Query(value = "SELECT def.* FROM dms_deferral_document_reports AS def,application_details_info AS app " +
            "WHERE app.id = def.app_id AND (def.status=0 OR def.status=1 OR def.status=2) AND app.type=:type group by app.id", nativeQuery=true)
    Collection<DeferralReportsEntity> getDeferralDetails(Integer type);

    @Modifying
    @Transactional
    @Query(value ="UPDATE dms_deferral_document_reports set status = 2 WHERE id=?1",nativeQuery = true)
    void updateStatus(Long id);

    @Query(value = "SELECT * FROM dms_deferral_document_reports WHERE app_id=:appId and status=:status",nativeQuery = true)
    List<DeferralReportsEntity> getDeferralPendingReport(Long appId,int status);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM dms_deferral_document_reports where id=:id",nativeQuery = true)
    void deletedeferralById(Long id);
}
