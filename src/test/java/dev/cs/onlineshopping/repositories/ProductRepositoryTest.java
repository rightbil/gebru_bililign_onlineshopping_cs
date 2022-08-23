package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.devmodels.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {
     @Autowired
    private ProductRepository productRepository;
    Product product = new Product();
    @Test
    void itShouldFindProductByProductCode() {
        Short quantityInstoke = 900;
        String productCode = "T1000";
        //given
        Product product = new Product();
        product.setProductCode(productCode);
        product.setProductName("Toyota");
        product.setProductLine("");
        product.setProductScale("Scale");
        product.setProductVendor("Japan1");
        product.setProductDescription("Reliable");
        product.setQuantityInStock(quantityInstoke);
        product.setBuyPrice(1000);
        product.setMSRP(100.00);
        productRepository.save(product);
        //when
        Product exists = productRepository.findByProductCode(productCode);
        //then
        assertEquals(product, exists); // equals and hash code are implemented

    }
    @Test
    void itShouldDecreaseStockQuantity() {
        //given
        short quantityBeforeSold = product.getQuantityInStock();
        //when
        productRepository.decreaseStockQuantity(product.getProductCode());
        short expected = product.getQuantityInStock();
        //then
        assertEquals(expected, quantityBeforeSold + 1);
    }
    @Test
    void itShouldIncreaseStockQuantity() {
        //given
        short quantityBeforeSold = product.getQuantityInStock();
        //when
        productRepository.increaseStockQuantity(product.getProductCode());
        short expected = product.getQuantityInStock();
        //then
        assertEquals(expected, quantityBeforeSold - 1);
    }
    @Test
    void itShouldIncreaseStockQuantityBatch() {
        //given
        short quantityBeforeSold = product.getQuantityInStock();
        //when
        productRepository.increaseStockQuantityBatch(quantityBeforeSold,product.getProductCode());
        short expected = product.getQuantityInStock();
        //then
        assertEquals(expected, quantityBeforeSold + quantityBeforeSold);
    }
}