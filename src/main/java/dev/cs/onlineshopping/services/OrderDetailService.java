package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.devmodels.models.OrderDetail;
import dev.cs.onlineshopping.repositories.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
     public void saveOrderDetail(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }
}