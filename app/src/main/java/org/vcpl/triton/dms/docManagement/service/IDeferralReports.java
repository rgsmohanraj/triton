package org.vcpl.triton.dms.docManagement.service;

import org.vcpl.triton.dms.docManagement.data.DeferralReportsData;
import org.vcpl.triton.dms.docManagement.data.DeferralReportsMasterData;
import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IDeferralReports {

    Collection<DeferralReportsEntity> getDeferralReport(Long appId);

    String getDeferralReports(Long appId);

    Collection<DeferralReportsEntity> getDeferralReport(Long appId, int status);

    List<Long> saveDeferralReports(DeferralReportsMasterData deferralReportsMasterData);

    public List<DeferralReportsEntity> getCompeletedDeferralDataByAppIdAndStatus(Long appId);
    List<Long> updateDeferralDoc(DeferralReportsMasterData deferralReportsMasterData);

    Collection<DeferralReportsEntity> getDeferralDocuments(Integer type, Integer status);

    Collection<DeferralReportsEntity> getDeferralDetails(Integer type);

    void updateDeferralStatus(Long appId);

     List<Long> updateDeferral(DeferralReportsMasterData deferralReportsMasterData);

    List<Long> saveNewDeferralDate(DeferralReportsMasterData deferralReportsMasterData);

    List<DeferralReportsEntity> getDeferralPendingReport(Long appId,Integer status);
}
