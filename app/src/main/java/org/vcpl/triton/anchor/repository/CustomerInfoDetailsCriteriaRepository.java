package org.vcpl.triton.anchor.repository;


import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.vcpl.triton.anchor.entity.CustomerInfoEntity;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsPage;
import org.vcpl.triton.anchor.page.CustomerInfoDetailsSearchCriteria;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomerInfoDetailsCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public CustomerInfoDetailsCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<CustomerInfoEntity> findAllFilter(CustomerInfoDetailsPage customerInfoPage, CustomerInfoDetailsSearchCriteria
            customerInfoSearchCriteria) {
        CriteriaQuery<CustomerInfoEntity> criteriaQuery = criteriaBuilder.createQuery((CustomerInfoEntity.class));
        Root<CustomerInfoEntity> customerInfoEntityRoot = criteriaQuery.from(CustomerInfoEntity.class);
        Predicate predicate = getPredicate(customerInfoSearchCriteria, customerInfoEntityRoot);
        criteriaQuery.where(predicate);
        setOrder(customerInfoPage, criteriaQuery, customerInfoEntityRoot);


        TypedQuery<CustomerInfoEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(customerInfoPage.getPageNumber() * customerInfoPage.getPageSize());
        typedQuery.setMaxResults(customerInfoPage.getPageSize());

        Pageable pageable = getPageable(customerInfoPage);
        Pageable pageable1 = getPageable1(customerInfoPage);
        Pageable pageable2 = getPageable2(customerInfoPage);


        long CustomerInfoEntityCount = getCustomerInfoEntityCount(predicate);
        return new PageImpl<>(typedQuery.getResultList(), pageable, CustomerInfoEntityCount);



    }


    private Predicate getPredicate(CustomerInfoDetailsSearchCriteria customerInfoSearchCriteria, Root<CustomerInfoEntity> customerInfoEntityRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(customerInfoSearchCriteria.getCustomerName())) {
            predicates.add(criteriaBuilder.like(customerInfoEntityRoot.get("anchorName"), "%" + customerInfoSearchCriteria.getCustomerName() + "%"));

        }
        if (Objects.nonNull(customerInfoSearchCriteria.getPan())) {
            predicates.add(criteriaBuilder.like(customerInfoEntityRoot.get("pan"), "%" + customerInfoSearchCriteria.getPan() + "%"));

        }
        if (Objects.nonNull(customerInfoSearchCriteria.getCin())) {
            predicates.add(criteriaBuilder.like(customerInfoEntityRoot.get("cin"), "%" + customerInfoSearchCriteria.getCin() + "%"));

        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }

    private void setOrder(CustomerInfoDetailsPage customerInfoPage, CriteriaQuery<CustomerInfoEntity> criteriaQuery, Root<CustomerInfoEntity> customerInfoEntityRoot) {

        if (customerInfoPage.getSortDirection().equals(Sort.Direction.ASC) && customerInfoPage.getSortByCustomerName().equals("anchorName")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(customerInfoEntityRoot.get(customerInfoPage.getSortByCustomerName())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(customerInfoEntityRoot.get(customerInfoPage.getSortByCustomerName())));
        }

        if (customerInfoPage.getSortDirection().equals(Sort.Direction.ASC) && customerInfoPage.getSortByCin().equals("cinNumber")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(customerInfoEntityRoot.get(customerInfoPage.getSortByCin())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(customerInfoEntityRoot.get(customerInfoPage.getSortByCin())));
        }

        if (customerInfoPage.getSortDirection().equals(Sort.Direction.ASC) && customerInfoPage.getSortByPan().equals("panNumber")) {
            criteriaQuery.orderBy(criteriaBuilder.asc(customerInfoEntityRoot.get(customerInfoPage.getSortByPan())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(customerInfoEntityRoot.get(customerInfoPage.getSortByPan())));
        }

    }

    private Pageable getPageable(CustomerInfoDetailsPage customerInfoPage) {
        Sort sortCustomerName = Sort.by(customerInfoPage.getSortDirection(), customerInfoPage.getSortByCustomerName());
        return PageRequest.of(customerInfoPage.getPageNumber(), customerInfoPage.getPageSize(), sortCustomerName);
    }

    private Pageable getPageable1(CustomerInfoDetailsPage customerInfoPage) {
        Sort sortCinNumber = Sort.by(customerInfoPage.getSortDirection(), customerInfoPage.getSortByCin());
        return PageRequest.of(customerInfoPage.getPageNumber(), customerInfoPage.getPageSize(), sortCinNumber);
    }

    private Pageable getPageable2(CustomerInfoDetailsPage customerInfoPage) {
        Sort sortCinNumber = Sort.by(customerInfoPage.getSortDirection(), customerInfoPage.getSortByCin());
        return PageRequest.of(customerInfoPage.getPageNumber(), customerInfoPage.getPageSize(), sortCinNumber);
    }

    private long getCustomerInfoEntityCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<CustomerInfoEntity> countRoot = countQuery.from(CustomerInfoEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}

