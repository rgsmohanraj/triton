package org.vcpl.triton.dms.docManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;

import java.util.Collection;
import java.util.List;

@Repository
public interface OtherDocumentMasterRepository extends JpaRepository<OtherDocumentMasterEntity,Long> {

    @Query(value = "SELECT * FROM dms_other_document_master WHERE app_id=?1 order by seq_no",nativeQuery = true)
    Collection<OtherDocumentMasterEntity> getDeferralReport(Long appId);

    @Query(value = "SELECT * FROM dms_other_document_master WHERE app_id=?1 and deferral_type=1 order by seq_no",nativeQuery = true)
    Collection<OtherDocumentMasterEntity> getDeferralReports(Long appId);

    @Query(value = "SELECT * FROM dms_other_document_master as c WHERE app_id=?1 order by c.seq_no DESC LIMIT 1",nativeQuery = true)
    OtherDocumentMasterEntity getSeqNo(Long appId);

    @Query(value = "SELECT * FROM dms_other_document_master WHERE display_name=:displayName AND app_id=:appId",nativeQuery = true)
    OtherDocumentMasterEntity getMasterWithName(String displayName,Long appId);

    @Query(value = "SELECT * FROM dms_other_document_master WHERE app_id=:appId and status=:status",nativeQuery = true)
    Collection<OtherDocumentMasterEntity> getOtherDeferralDocument(Long appId, int status);

    @Query(value = "SELECT * FROM dms_other_document_master WHERE app_id=:appId and (status=0 OR status=1 OR status=3)",nativeQuery = true)
    Collection<OtherDocumentMasterEntity> getOtherDeferralDocumentOpsMaker(Long appId);

    @Query(value ="SELECT * FROM dms_other_document_master WHERE app_id=?1 AND deferral_type = 1 AND status=?2",nativeQuery = true)
    List<OtherDocumentMasterEntity> findPendingOtherDeferralDoc(Long appId, Long status);

    @Query(value = "SELECT * FROM dms_other_document_master WHERE app_id=?1 AND deferral_type = 1 order by seq_no",nativeQuery = true)
    Collection<OtherDocumentMasterEntity> getOtherDocsForOpsChecker(Long appId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM dms_other_document_master where id=?1",nativeQuery = true)
    void deleteMasterById(Long id);

}
