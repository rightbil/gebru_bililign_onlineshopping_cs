package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Orders;
import dev.cs.onlineshopping.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Transactional
    public Integer saveMyOrders(Orders order) {
        Orders ord = ordersRepository.save(order);
        Integer orderNumber = ord.getOrderNumber();
        return orderNumber;
    }
    public Optional<Orders> findOrderByOrderNumber(Integer ordernumber) {
        return ordersRepository.findById(ordernumber);

    }
}