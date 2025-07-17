package org.vcpl.triton.workflow.data;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkflowstageData {

    @NotNull
    private Long groupId;

    @NotNull
    private Long stageId;

    @NotNull
    private Long clientType;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getStageId() {
        return stageId;
    }

    public void setStageId(Long stageId) {
        this.stageId = stageId;
    }

    public Long getClientType() {
        return clientType;
    }

    public void setClientType(Long clientType) {
        this.clientType = clientType;
    }
}
