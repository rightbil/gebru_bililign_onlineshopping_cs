package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}