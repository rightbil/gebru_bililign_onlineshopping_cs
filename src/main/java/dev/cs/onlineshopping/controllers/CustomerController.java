package dev.cs.onlineshopping.controllers;
import dev.cs.onlineshopping.models.Customer;
import dev.cs.onlineshopping.services.CustomerService;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    @GetMapping
    public String defaultAdminDashboard(HttpServletRequest request, Model model) {
        int page = 0;
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("customers", customerService.listAllCustomers(PageRequest.of(page, size)));
        return "admindb";
    }
    @GetMapping("/page")
    public String findAllCustomersByPage(HttpServletRequest request, @RequestParam("page") int page, Model model) {
        //int page = 0;
        int size = 5;
        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }
        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }
        model.addAttribute("customers", customerService.listAllCustomers(PageRequest.of(page, size)));
        return "admindb";
    }


    // CRUD Operations using postman
    // 1. get all
    // GET  http://localhost:8080/customers/
    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customerList = customerService.listAllCustomers();
        return ResponseEntity.ok(customerList);
    }
    // 2. get a single customer by using his id
    // GET  http://localhost:8080/customers/103
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable int id ) {
        Customer customerById = customerService.findCustomerById(id);
        return ResponseEntity.ok(customerById);
    }

    // 3. Edit a single customer by using his id
    // GET  http://localhost:8080/customers/edit/103
    //@GetMapping("/eidt/{id}")
   // public ResponseEntity<Customer> updateCustomerById(@PathVariable int id ) {
       // Customer customerById = customerService
     //   return ResponseEntity.ok(customerById);
    //}


    // 4. Add a single customer
    // GET  http://localhost:8080/customers/add
    @GetMapping("/add")
    public void addCustomer(@RequestBody Customer customer) {
        customerService.addStudent(customer);
    }



}