package com.fnbadmin.controller.repository;

import com.fnbadmin.controller.request.OrderRequest;
import com.fnbadmin.controller.request.ProductRequest;
import com.fnbadmin.domain.Order;
import com.fnbadmin.domain.OrderAdditionalOption;
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

    public Product findProductById(int productId) {
        CriteriaBuilder cb           = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq    = cb.createQuery(Product.class);
        Root<Product> root           = cq.from(Product.class);

        cq = cq.where(cb.and(cb.equal(root.get("id"), productId)));
        TypedQuery<Product> typedQuery = em.createQuery(cq);

        return typedQuery.getSingleResult();
    }

    public List<ProductOption> findProductOptions(int productId) {
        CriteriaBuilder cb               = em.getCriteriaBuilder();
        CriteriaQuery<ProductOption> cq  = cb.createQuery(ProductOption.class);
        Root<ProductOption> root         = cq.from(ProductOption.class);

        cq = cq.where(cb.and(cb.equal(root.get("id"), productId)));

        TypedQuery<ProductOption> typedQuery = em.createQuery(cq);

        return typedQuery.getResultList();
    }

    public int insertProduct(Product product) {
        this.em.persist(product);
        return product.getId();
    }

    public int insertProductOptions(List<ProductOption> productOptions) {
        for (ProductOption po : productOptions) {
            this.em.persist(po);
        }

        return productOptions.size();
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
