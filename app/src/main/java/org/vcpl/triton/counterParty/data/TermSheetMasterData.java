package org.vcpl.triton.counterParty.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TermSheetMasterData {
    @NotNull
    @Valid
    List<TermSheetData> termSheetDataList;

    private String updatedBy;

    private String createdBy;

    public List<TermSheetData> getTermSheetDataList() {
        return termSheetDataList;
    }

    public void setTermSheetDataList(List<TermSheetData> termSheetDataList) {
        this.termSheetDataList = termSheetDataList;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
