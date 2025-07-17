package org.vcpl.triton.anchor.service;

import org.springframework.data.domain.Page;
import org.vcpl.triton.anchor.data.CustomerInfoData;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsPage;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsSearchCriteria;

import java.util.List;

public interface ICustomerInfo {
    List<CustomerInfoEntity> getAllProduct();
    CustomerInfoEntity saveCustomerInfo (CustomerInfoData customerInfoData);
    Page<CustomerInfoEntity> getCustomer (CustomerInfoDetailsPage customerInfoDetailsPage,
                                          CustomerInfoDetailsSearchCriteria customerInfoDetailsSearchCriteria);
    CustomerInfoEntity getCustomerInfoById(long id);
    CustomerInfoEntity updateCustomerInfo(CustomerInfoData customerInfoData);
    String searchCustomerInfo(String query,Integer type,String stage,String status);
    List<CustomerInfoEntity>deDupeCustomerInfo(String query);
}
