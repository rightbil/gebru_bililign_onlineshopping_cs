package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.models.OrderDetail;
import dev.cs.onlineshopping.repositories.OrderDetailRepository;
import dev.cs.onlineshopping.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    /****
     *
     * @param orderNumber order number where we are storing its detail
     */
    public void saveOrderDetail(Integer orderNumber) {
        Map<String, Short> map = ProductService.virtualCart;
        for (String productCode : map.keySet()) {
            OrderDetail currentOrderDetail = new OrderDetail();
            currentOrderDetail.setOrderNumber(orderNumber);
            currentOrderDetail.setProductCode(productCode);
            currentOrderDetail.setQuantityOrdered(map.get(productCode));
            currentOrderDetail.setPriceEach(productRepository.findByProductCode(productCode).getBuyPrice());
            currentOrderDetail.setOrderLineNumber(productRepository.findByProductCode(productCode).getProductLine());
            orderDetailRepository.save(currentOrderDetail);
        }

    }

}