package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ProductRepository extends JpaRepository<Product,String> {
    String productByCode = "FROM Product p where p.productCode=:productcode";
    @Query(productByCode)
    Product findByProductCode(@Param("productcode") String productCode);
}