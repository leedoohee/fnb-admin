package com.fnbadmin.controller.repository;

import com.fnbadmin.domain.Product;
import com.fnbadmin.domain.ProductOption;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductRepository {
    private final EntityManager em;

    public ProductRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public List<Product> findProducts(String startDate, String endDate, String status, int page, int pageLimit) {
        return this.em.createQuery("select p from Product p", Product.class)
                //.setParameter("startDate", startDate)
                //.setParameter("endDate", endDate)
                .getResultList();
    }

    public Product findProductById(int productId) {
        return this.em.createQuery("select p from Product p where p.id = :productId", Product.class)
                .setParameter("productId", productId)
                .getSingleResult();
    }

    public List<ProductOption> findAllProductOptions() {
        return this.em.createQuery("select po from ProductOption po", ProductOption.class)
                .getResultList();
    }


    public List<ProductOption> findProductOptions(int productIds) {
        return this.em.createQuery("select po from ProductOption po where po.productId in (:productIds)", ProductOption.class)
                .setParameter("productIds", productIds)
                .getResultList();
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

}
