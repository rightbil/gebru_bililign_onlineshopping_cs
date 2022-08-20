package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    // TODO implement laster on  Customers could have same name

     @Query("From Customer c where c.email=:email")
     Optional<Customer> findCustomerByEmail(@Param("email") String email);
}