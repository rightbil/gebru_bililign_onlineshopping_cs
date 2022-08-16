package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Product;
import dev.cs.onlineshopping.utility.HQL;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ProductRepository extends JpaRepository<Product,String> {
//    String sqlSearchProductByProductCode = "FROM Product p where p.productCode=:productcode";
//    @Query(sqlSearchProductByProductCode)
    @Query(name= "sqlSearchProductByProductCode")
    Product findByProductCode(@Param("productcode") String productCode);


}