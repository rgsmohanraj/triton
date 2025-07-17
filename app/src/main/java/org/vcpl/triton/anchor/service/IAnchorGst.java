package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.AnchorGstMasterData;
import org.vcpl.triton.anchor.entity.AnchorGstEntity;

import java.util.Collection;
import java.util.List;

public interface IAnchorGst {

    List<AnchorGstEntity> getAllProduct();
    Collection<AnchorGstEntity> getanchorGstDetails(long id);
    List<Long>  saveAnchorGstDetails(AnchorGstMasterData anchorGstMasterData, long id);
    String deleteAnchorDetails(long id);
    List<Long>  updateAnchorGst (AnchorGstMasterData anchorGstMasterData);
    AnchorGstEntity getanchorGstDetailsById(long id);

}
