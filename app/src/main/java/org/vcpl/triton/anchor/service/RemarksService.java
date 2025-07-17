package org.vcpl.triton.anchor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vcpl.triton.anchor.data.RemarksData;
import org.vcpl.triton.anchor.entity.RemarksEntity;
import org.vcpl.triton.anchor.repository.CustomerInfoRepository;
import org.vcpl.triton.anchor.repository.RemarksRepository;

import java.util.Collection;

@Service
public class RemarksService implements IRemarks {
    @Autowired
    private RemarksRepository remarksRepository;
    @Autowired
    private CustomerInfoRepository customerInfoRepository;

    @Override
    public RemarksEntity saveRemarks(RemarksData remarksData) {
        RemarksEntity remarksEntity = remarksRepository.save(transform(remarksData));
        return remarksEntity;
    }

    private RemarksEntity transform(RemarksData remarksData) {
        RemarksEntity remarksEntity = new RemarksEntity();
        remarksEntity.setDescription(remarksData.getDescription());
        remarksEntity.setStepperTab(remarksData.getStepperTab());
        remarksEntity.setCustomerInfoEntity(customerInfoRepository.getById(remarksData.getCustId()));

        return remarksEntity;
    }

    @Override
    public Collection<RemarksEntity> getRemarks (long id) {
        return  remarksRepository.findByCiId(id);
    }
}
