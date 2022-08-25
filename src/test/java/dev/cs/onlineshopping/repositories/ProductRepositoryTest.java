package dev.cs.onlineshopping.repositories;
import dev.cs.onlineshopping.models.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
/****
 * Will force the test to use the db mentioned in the properties file.
 */
@AutoConfigureTestDatabase(replace = Replace.NONE)
class ProductRepositoryTest {
    Product product = new Product();
    @Autowired
    private ProductRepository productRepository;
    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }
    @AfterAll
    static void tearDownAfterClass() throws Exception {
        // Delete the product from the database

    }
    @BeforeEach
    void setUp() {
    }
    @AfterEach
    void tearDown() {
    }
    @Test
    @Rollback(value = false)
    /****
     * This method tests Save / Edit and Finding product by productcode
     */
    void itShouldFindProductByProductCode() {
        Short quantityInstoke = 1900;
        String productCode = "IK2020";
        //given
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
    @Rollback(value = false)
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
    @Rollback(value = false)
    /****
     * This tests increasing stock quantiy by 1
     */
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
    @Rollback(value = false)
    /****
     * This tests increasing stock for numbers > 1, eg. removing an item
     * that has a quanity of 10 from virtual cart.
     */
    void itShouldIncreaseStockQuantityBatch() {
        //given
        short quantityBeforeSold = product.getQuantityInStock();
        //when
        productRepository.increaseStockQuantityBatch(quantityBeforeSold, product.getProductCode());
        short expected = product.getQuantityInStock();
        //then
        assertEquals(expected, quantityBeforeSold + quantityBeforeSold);
    }
}