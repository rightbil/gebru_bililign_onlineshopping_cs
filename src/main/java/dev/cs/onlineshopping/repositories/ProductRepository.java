package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.devmodels.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query(name = "sqlSearchProductByProductCode")
    Product findByProductCode(@Param("productcode") String productCode);
    @Modifying
    @Transactional
    @Query("UPDATE Product p set p.quantityInStock= p.quantityInStock - 1  Where p.productCode=:productcode")
    void decreaseStockQuantity(@Param("productcode") String productcode);
    @Modifying
    @Transactional
    @Query("UPDATE Product p set p.quantityInStock= p.quantityInStock + 1 Where p.productCode=:productcode")
    void increaseStockQuantity(@Param("productcode") String productcode);

    @Modifying
    @Transactional
    @Query("UPDATE Product p set p.quantityInStock= p.quantityInStock +:quantityInStock Where p.productCode=:productcode")
    void increaseStockQuantityBatch(@Param("quantityInStock") short quantityInStock, @Param("productcode") String productcode);

}