package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.AnchorBasicDetailsData;
import org.vcpl.triton.anchor.entity.AnchorBasicEntity;

import java.util.Collection;
import java.util.List;

public interface IAnchorBasic {

    List<AnchorBasicEntity> getAllProduct();
    Collection<AnchorBasicEntity> getanchorBasicDetails(long id);
    AnchorBasicEntity saveBasicDetails(AnchorBasicDetailsData anchorBasicDetailsData);
    AnchorBasicEntity getAnchorDetailsById(long id);
    AnchorBasicEntity updateAnchorDetails(AnchorBasicDetailsData anchorBasicDetailsData);

}
