package org.vcpl.triton.dms.docManagement.service;

import org.vcpl.triton.dms.docManagement.data.OtherDocumentMasterData;
import org.vcpl.triton.dms.docManagement.entity.OtherDocumentMasterEntity;

import java.util.Collection;
import java.util.List;

public interface IOtherDocumentMaster {
    Collection<OtherDocumentMasterEntity> getOtherDocumentMaster();

    Collection<OtherDocumentMasterEntity> getOtherDocumentMaster(Long appId);

    List<Long> saveOtherDocumentMaster(OtherDocumentMasterData otherDocumentMasterData);

    List<Long> updateOtherDocumentMaster(OtherDocumentMasterData otherDocumentMasterData);

    Collection<OtherDocumentMasterEntity> getOtherDeferralDocuments(Long appId, int status);

    void updateOtherDocsStatus(Long appId);

    List<OtherDocumentMasterEntity> findPendingOtherDoc(Long appId, Long status);

    Boolean workflowDecisionDefOpsChecker(Long appId);

    Collection<OtherDocumentMasterEntity> getOtherDocsForOpsChecker(Long appId);

    String deleteOtherDocRecord(Long appId,Long id);
}
