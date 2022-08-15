package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Customer;
import dev.cs.onlineshopping.repositories.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    // CRUD- Select
    public Page<Customer> listAllCustomers(PageRequest pageRequest) {
        return customerRepository.findAll(pageRequest);
    }
    public Customer findCustomerById(String customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(NullPointerException::new);
    }
    //TODO late on check the query method
//    public List<Customer> findCustomerByName(String customerName) {
//        return customerRepository
//                .findCustomerByName(customerName)
//                .map(Customer::getCustomerNumber)
//                .orElse("Customer with Id :" + " do not exist in the system");
//
//    }
    // Input Employee jpa Entity , output DTO

}