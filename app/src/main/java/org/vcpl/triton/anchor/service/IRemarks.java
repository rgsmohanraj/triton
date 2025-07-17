package org.vcpl.triton.anchor.service;

import org.vcpl.triton.anchor.data.RemarksData;
import org.vcpl.triton.anchor.entity.RemarksEntity;

import java.util.Collection;

public interface IRemarks {

    Collection<RemarksEntity> getRemarks (long id);
    RemarksEntity saveRemarks(RemarksData remarksData);
}
