package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Orders;
import dev.cs.onlineshopping.repositories.OrdersRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    /****
     * save the order for the current customer
     * @param customerNumber logged in customer nubmer
     * @return the new order number generated
     */
    @Transactional
    public Integer saveMyOrders(Integer customerNumber) {
        Orders orderOnProgress = new Orders();
        orderOnProgress.setOrderDate(Util.orderDate());
        orderOnProgress.setRequiredDate(Util.requiredDate());
        orderOnProgress.setStatus("OK");
        orderOnProgress.getComments();
        orderOnProgress.setCustomerNumber(customerNumber);
        Integer newOrderNumber = ordersRepository.save(orderOnProgress).getOrderNumber();
        return newOrderNumber;
    }
 }