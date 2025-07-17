package org.vcpl.triton.workflow.service;

import org.vcpl.triton.dms.docManagement.entity.DeferralReportsEntity;
import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IWFApprovalStatus {

    public  Collection<DeferralReportsEntity> findDeferralValue(WFApprovalStatusData wfApprovalStatusData,WFApprovalStatusEntity wfApprovalStatusEntity,String anchorName) throws UnsupportedEncodingException;

    public List<WFApprovalStatusEntity> getHistoryOfWFStatus(long id);

    public String getLastWFStatus(String email);

    public String getLeadLastWFStatus(String email,String stageName);

    public WFApprovalStatusEntity saveWFApproval(WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException;

    public WFApprovalStatusEntity saveWFRenewalFlow(Boolean genType,WFApprovalStatusData wfApprovalStatusData) throws UnsupportedEncodingException;

    public String sendMail(String email);

    Collection<WFApprovalStatusEntity> findExistingAnchor(String stage,String status);

    WFApprovalStatusEntity changeAssigne(Map<Long,String> idWithEmail, HttpServletResponse response);

    List<WFApprovalStatusEntity> returnWF(WFApprovalStatusData wfApprovalStatusData, WFApprovalStatusEntity wfApprovalStatusEntity,String customerInfoName) throws UnsupportedEncodingException;
    Collection<WFApprovalStatusEntity> findAnchorAsDescOrder();

    String getWFStatus(Long id);

    List<WFApprovalStatusEntity> findOnboardedCustomers(int customerType);
}
