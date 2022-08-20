package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Product;
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

    //TODo modify this after it works
//    @Modifying
//    @Query("UPDATE Product p set " +
//              "p.productName=:productname"
//            + ",p.productLine=:productline"
//            + ",p.productScale=:productscale"
//            + ",p.productVendor=:productvendor"
//            + ",p.productDescription=:productDescription"
//            + ",p.quantityInStock=:quantityInStock"
//            + ",p.buyPrice=:buyPrice"
//            + ",p.MSRP=:MSRP")
//    void updateProductByFields(
//              @Param("productName") String productname
//            , @Param("productLine") String productline
//            , @Param("productScale") String productscale
//            , @Param("productVendor") String productvendor
//            , @Param("productDescription") String productDescription
//            , @Param("quantityInStock") double quantityInStock
//            , @Param("buyPrice") double buyPrice
//            , @Param("MSRP") double MSRP);

//    @Query("UPDATE Product p set p.quantityInStock= p.quantityInStock + :quantityInStock")
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