package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductRepository {
    private final EntityManager em;

    public ProductRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Long getTotalProductCount(ProductRequest productRequest) {
        CriteriaBuilder cb      = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq  = cb.createQuery(Long.class);
        Root<Product> root      = cq.from(Product.class);

        cq = cq.where(cb.and(this.buildConditions(productRequest, cb, root).toArray(new Predicate[0])));
        cq = cq.select((cb.count(root)));

        return  em.createQuery(cq).getSingleResult();
    }

    public List<Product> findProducts(ProductRequest productRequest) {
        CriteriaBuilder cb           = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq    = cb.createQuery(Product.class);
        Root<Product> root           = cq.from(Product.class);

        cq = cq.where(cb.and(this.buildConditions(productRequest, cb, root).toArray(new Predicate[0])));

        TypedQuery<Product> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult(productRequest.getPage() - 1);
        typedQuery.setMaxResults(productRequest.getPageLimit());

        return typedQuery.getResultList();
    }

    public Product findProduct(int productId) {
        CriteriaBuilder cb           = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq    = cb.createQuery(Product.class);
        Root<Product> root           = cq.from(Product.class);

        root.fetch("productOption", JoinType.LEFT);
        root.fetch("productAttachFile", JoinType.INNER);

        cq = cq.select(root)
                .where(cb.and(cb.equal(root.get("productId"), productId)))
                .distinct(true);

        TypedQuery<Product> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
    }

    @Transactional
    public int insertProduct(Product product) {
        this.em.persist(product);
        return product.getProductId();
    }

    @Transactional
    public int insertProductOptions(List<ProductOption> productOptions) {
        this.em.persist(productOptions);
        return productOptions.size();
    }

    @Transactional
    public int insertAttachFile(List<ProductAttachFile> attachFiles) {
        this.em.persist(attachFiles);
        return attachFiles.size();
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
                searchConditions.add(cb.equal(root.get("productId"), searchWord));
            }
        }

        return  searchConditions;
    }

}
