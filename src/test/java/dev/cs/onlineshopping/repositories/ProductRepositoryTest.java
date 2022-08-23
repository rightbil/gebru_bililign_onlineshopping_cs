package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.devmodels.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProductRepositoryTest {
//    @Test
//    void deleteInBatch() {
//    }
    @Autowired
    private ProductRepository productRepository;
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
         assertEquals(product,exists); // equals and hash code are implemented

    }
    @Test
    void itShouldDecreaseStockQuantity() {
        //given
        //when
        //then
    }
    @Test
    void itShouldIncreaseStockQuantity() {
        //given
        //when
        //then
    }
    @Test
    void itShouldIncreaseStockQuantityBatch() {
        //given
        //when
        //then
    }
}