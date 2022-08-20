package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.Orders;
import dev.cs.onlineshopping.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    public void saveMyOrders(Orders order) {
        ordersRepository.save(order);
    }
}