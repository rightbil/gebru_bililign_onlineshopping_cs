package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // TODO implement laster on  Customers could have same name
    // Optional<Customer> findCustomerByName(String customerName);
}