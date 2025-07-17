package org.vcpl.triton.workflow.service;


import org.vcpl.triton.workflow.data.WFApprovalStatusData;
import org.vcpl.triton.workflow.entity.DeferralWorkflowEntity;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IDeferralWorkflow {

    List<DeferralWorkflowEntity> getHistoryOfWFStatus(long id);

    String getLastDeferralWFStatus(String email);

    DeferralWorkflowEntity saveDeferralWorkflow(WFApprovalStatusData deferralWorkflowData);

    Collection<DeferralWorkflowEntity> getRemarks(long appId);

    DeferralWorkflowEntity changeAssigne(Map<Long, String> idWithEmail, HttpServletResponse response);

    Collection<DeferralWorkflowEntity> findAnchorAsDescOrder();
}
