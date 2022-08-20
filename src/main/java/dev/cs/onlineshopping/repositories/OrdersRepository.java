package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}