package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.domain.Option;
import com.fnbadmin.domain.OptionGroup;
import com.fnbadmin.domain.Product;
import com.fnbadmin.domain.ProductOption;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptionRepository {
    private final EntityManager em;

    public OptionRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<OptionGroup> findOptionGroups(String optionType) {
        CriteriaBuilder cb               = em.getCriteriaBuilder();
        CriteriaQuery<OptionGroup> cq    = cb.createQuery(OptionGroup.class);
        Root<OptionGroup> root           = cq.from(OptionGroup.class);

        cq = cq.where(cb.and(cb.equal(root.get("optionType"), optionType)));

        TypedQuery<OptionGroup> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    public OptionGroup findOptionGroup(String optionType, String optionGroupId) {
        CriteriaBuilder cb               = em.getCriteriaBuilder();
        CriteriaQuery<OptionGroup> cq    = cb.createQuery(OptionGroup.class);
        Root<OptionGroup> root           = cq.from(OptionGroup.class);

        cq = cq.where(cb.and(cb.equal(root.get("optionType"), optionType)));
        cq = cq.where(cb.and(cb.equal(root.get("optionGroupId"), optionGroupId)));

        TypedQuery<OptionGroup> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
    }

    public List<Option> findOptions(String optionGroupId) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Option> cq    = cb.createQuery(Option.class);
        Root<Option> root           = cq.from(Option.class);

        cq = cq.where(cb.and(cb.equal(root.get("optionGroupId"), optionGroupId)));

        TypedQuery<Option> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    public Option findOption(String optionGroupId, String optionId) {
        CriteriaBuilder cb          = em.getCriteriaBuilder();
        CriteriaQuery<Option> cq    = cb.createQuery(Option.class);
        Root<Option> root           = cq.from(Option.class);

        cq = cq.where(cb.and(cb.equal(root.get("optionGroupId"), optionGroupId)));
        cq = cq.where(cb.and(cb.equal(root.get("optionId"), optionId)));

        TypedQuery<Option> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
    }

    private List<Predicate> buildConditions(ProductRequest productRequest, CriteriaBuilder cb, Root<Product> root) {
        List<Predicate> searchConditions    = new ArrayList<>();
        String searchType                   = productRequest.getSearchType();
        String searchWord                   = productRequest.getSearchWord();

        if(productRequest.getRegisterStartDate() != null && productRequest.getRegisterEndDate() != null){
            searchConditions.add(cb.between(root.get("createdAt"), productRequest.getRegisterStartDate(), productRequest.getRegisterEndDate()));
        }

        if(productRequest.getStatus() != null && !productRequest.getStatus().isEmpty()){
            searchConditions.add(cb.equal(root.get("status"), productRequest.getStatus()));
        }

        if (searchWord != null && !searchWord.trim().isEmpty()) {
            if ("productName".equals(searchType)) {
                searchConditions.add(cb.like(root.get("name"), "%" + searchWord + "%"));
            } else if ("productId".equals(searchType)) {
                searchConditions.add(cb.equal(root.get("id"), searchWord));
            }
        }

        return  searchConditions;
    }

}
