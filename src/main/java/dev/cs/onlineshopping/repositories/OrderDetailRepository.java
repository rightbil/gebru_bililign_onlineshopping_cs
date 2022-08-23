package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.devmodels.models.OrderDetail;
import dev.cs.onlineshopping.devmodels.models.OrderDetailIdClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailIdClass> {
}