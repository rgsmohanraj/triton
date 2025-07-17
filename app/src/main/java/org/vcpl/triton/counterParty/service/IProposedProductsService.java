package org.vcpl.triton.counterParty.service;

import org.vcpl.triton.counterParty.data.ProposedProductsData;
import org.vcpl.triton.counterParty.data.ProposedProductsMasterData;
import org.vcpl.triton.counterParty.entity.ProposedProductsEntity;
import org.vcpl.triton.workflow.entity.WFApprovalStatusEntity;

import java.util.Collection;
import java.util.List;


public interface IProposedProductsService {
    List<ProposedProductsEntity> getProposedProduct();
    ProposedProductsEntity getProposedProductById(Long id);
    Collection<ProposedProductsEntity> findProposedProductsByCiId(long id);
    String findCreditNormsId(long id);

    String gotoRiskOrCredit(long id);

    List<Long> saveProposedProduct(ProposedProductsMasterData proposedProductsMasterData, long id);

    List<Long> updateCreditProposedProduct(ProposedProductsMasterData proposedProductsMasterData);

    List<Long> updateProposedProduct(ProposedProductsMasterData proposedProductsMasterData, Long appId);

    String deleteProposedProduct(long id);

    String proposedProductDelete(ProposedProductsData proposedProductsData, Long custId);

    Collection<ProposedProductsEntity> findActiveProposedProducts(Long id);
}
