package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.AnchorAddressData;
import org.vcpl.triton.anchor.data.AnchorAddressMasterData;
import org.vcpl.triton.anchor.entity.AnchorAddressEntity;
import org.vcpl.triton.anchor.helper.AnchorAddress;

import java.util.Collection;
import java.util.List;

public interface IAnchorAddress
{

    List<AnchorAddressEntity> getAllProduct();

    Collection<AnchorAddressEntity> getanchorAddressDetailsById(long id);

    AnchorAddressEntity saveAnchorAddressDetails(AnchorAddressData anchorAddressData);

    AnchorAddressEntity updateAnchorAddress(AnchorAddressData anchorAddressData);

    String deleteAnchorAddress(long id);

    AnchorAddressEntity getAddressDetailsById(long id);
    String getPincodeDetails(String pinCode);

    List<Long>  updateMultipleAddress(AnchorAddressMasterData anchorAddressMasterData);
}
