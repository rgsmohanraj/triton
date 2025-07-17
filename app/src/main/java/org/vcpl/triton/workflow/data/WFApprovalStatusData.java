package org.vcpl.triton.workflow.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class WFApprovalStatusData {

    @NotNull
    private long appId;

    @NotNull
    private Long stageId;

    @NotNull
    private long status;

    @NotNull
    private String approverInfo;

    private String remarks;

    @NotNull
    private String nextApproverInfo;

    private Boolean approvedStatus;

    public long getAppId() {
        return appId;
    }

    public void setAppId(long appId) {
        this.appId = appId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getApproverInfo() {
        return approverInfo;
    }

    public void setApproverInfo(String approverInfo) {
        this.approverInfo = approverInfo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getNextApproverInfo() {
        return nextApproverInfo;
    }

    public void setNextApproverInfo(String nextApproverInfo) {
        this.nextApproverInfo = nextApproverInfo;
    }

    public Boolean getApprovedStatus() {
        return approvedStatus;
    }

    public void setApprovedStatus(Boolean approvedStatus) {
        this.approvedStatus = approvedStatus;
    }

    public String getLocalDateAndTime() {
        Date date = new Date();
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
