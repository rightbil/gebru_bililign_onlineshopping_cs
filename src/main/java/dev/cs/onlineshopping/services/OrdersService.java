package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Customer;
import dev.cs.onlineshopping.models.Orders;
import dev.cs.onlineshopping.repositories.OrdersRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    // use the jpa
    // save method
    @Transactional
    public Integer saveMyOrders(Orders order) {
        System.out.println("Save order method is called");
    // Inset into order table
    Orders ord = ordersRepository.save(order);
    Integer orderNumber = ord.getOrderNumber();
        System.out.println("Save order returned :" + orderNumber);
    return orderNumber;
    }
}