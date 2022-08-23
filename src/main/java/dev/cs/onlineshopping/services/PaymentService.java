package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Payment;
import dev.cs.onlineshopping.repositories.CustomerRepository;
import dev.cs.onlineshopping.repositories.PaymentRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductService productService;
    public void savePayment(Integer customernumber) {
        Payment p = new Payment();
        p.setCustomerNumber(customernumber);
        p.setCheckNumber("56565656");
        p.setPaymentDate(Util.orderDate());
        p.setAmount(productService.totalCharges());
        paymentRepository.save(p);
    }
}