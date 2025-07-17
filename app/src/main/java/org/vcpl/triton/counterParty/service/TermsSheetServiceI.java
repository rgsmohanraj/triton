package org.vcpl.triton.counterParty.service;


import org.vcpl.triton.counterParty.data.TermSheetData;
import org.vcpl.triton.counterParty.data.TermSheetMasterData;
import org.vcpl.triton.counterParty.entity.TermSheetEntity;

import java.util.Collection;
import java.util.List;

public interface TermsSheetServiceI {

    List<TermSheetEntity> getAllProduct();
    List<Long> saveTermSheet(TermSheetMasterData termSheetMasterData,long id);
    String deleteTermSheet(long id);
    TermSheetEntity getAddressDetailsById(long id);
    Collection<TermSheetEntity> getTermSheetById(long id);
    List<Long> updateTermSheet(TermSheetMasterData termSheetMasterData);
}
