package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Customer;
import dev.cs.onlineshopping.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public Page<Customer> listAllCustomers(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest);
    }
    public Customer findCustomerById(int customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(NullPointerException::new);
    }
    public Optional<Customer> findCustomerEmail(String email) {
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        return customer;
    }

    public List<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    public void addStudent(Customer customer){
        customerRepository.save(customer);
    }

}