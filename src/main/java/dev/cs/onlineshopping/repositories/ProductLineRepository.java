package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.devmodels.models.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductLineRepository extends JpaRepository<ProductLine,String> {
}