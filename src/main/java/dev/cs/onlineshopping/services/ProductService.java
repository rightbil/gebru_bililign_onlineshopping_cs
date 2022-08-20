package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.dtos.ProductCartDTO;
import dev.cs.onlineshopping.models.Customer;
import dev.cs.onlineshopping.models.OrderDetail;
import dev.cs.onlineshopping.models.Orders;
import dev.cs.onlineshopping.models.Product;
import dev.cs.onlineshopping.repositories.CustomerRepository;
import dev.cs.onlineshopping.repositories.OrdersRepository;
import dev.cs.onlineshopping.repositories.ProductRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
@Service
public class ProductService {
    private final short defaultCartQuantity = (short) 1;
    static Map<String, Short> virtualCart = new HashMap<>();
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrdersService ordersService;

//    private OrderDetailService orderDetailService;
    public Page<Product> listAllStoreProducts(PageRequest pageRequest) {
//TODO filter this based on quantity > 0
        List<Product> page = productRepository.findAll(pageRequest).stream()
                .filter(product -> product.getQuantityInStock() > 0).collect(Collectors.toList());
        return productRepository.findAll(pageRequest);
    }
    public Product getProductByProductCode(@Param("productcode") String productCode) {
        return productRepository.findByProductCode(productCode);
    }
    /// Order
    //TODO should be Orders,
    // 1. get the user email form session
    // 2. join step 1 with customer and get customer number
    // 3. save customer number to order table
    // 4. get all product code and quantity from virtual cart
    // 5. for each item in step 5 add to order detail table
    // 6. generate receipt using customer name , address and email signature about delivery date and time and DTO information Customer
    // Additional messages : Your Items are sent to 11548 Stewart Ln Apt A2. we also sent you
    // an email. let us know if you can give us a feed back on your purchase experiance.
    // Email
    public void processOrders(HttpServletRequest request) {
        // 1. Get email form Cookies
        String email = Util.getCookieValueByName(request, "userName");
        System.out.println("tesing email" + email);
        // 2. Find CustomerNumber using email
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        System.out.println("testing customer number" + customer.get().getCustomerNumber());
        System.out.println("testing Customer" + customer);
//        // 3. Insert Order to Order table - use customer number from step 2
//
        Orders o = new Orders();
        o.setOrderDate(Util.shippingDate());
        o.setRequiredDate(Util.shippingDate());
//        o.getShipdDate(Util.shippingDate());
        o.setStatus("OK"); // refer values
        o.getComments();
        o.setCustomerNumber(customer.get().getCustomerNumber());
//
        System.out.println("Order " + o);
        Integer newOrderNumber = ordersService.saveMyOrders(o);
        System.out.println("testing order saved and its id is " + newOrderNumber);
        // save order and get the saved id for orderDetails
        // for each code and quantity  // and fixed ordernumber
//        for (String pc : virtualCart.keySet()) {
//            var od = new OrderDetail();
//            od.setOrderNumber(newOrderNumber);
//            od.setProductCode(pc);
//            od.setQuantityOrdered(virtualCart.get(pc));
//            od.setPriceEach(productRepository.findByProductCode(pc).getBuyPrice());
//            od.setOrderLineNumber(productRepository.findByProductCode(pc).getProductLine());
//            //orderDetailService.saveOrderDetail(od);
//            System.out.println("testing order details : " + od);
//       }
//        // 4. get all product code and quantity from virtual cart
//        // 5. for each item in step 5 add to order detail table
//        // 6. generate receipt using customer name , address and email signature about delivery date and time and DTO information Customer
//        // Additional messages : Your Items are sent to 11548 Stewart Ln Apt A2. we also sent you
//        // an email. let us know if you can give us a feed back on your purchase experiance.
//        // Email


    }
    // CRUD prodcuts
    public void saveProduct(Product product) {
        productRepository.save(product);
    }
    // TODO explored
    public void updateProduct(String productCode, Product product) {
        productRepository.save(product);
    }
    public Product findProductByProductCode(String productcode) {
        return productRepository.findByProductCode(productcode);
    }
    //@Modifying
    //@Query("DELETE FROM Product p where p.productCode =:productcode")
    public void deleteProduct(String productcode) {
        productRepository.delete(findProductByProductCode(productcode));
    }
    //    public void updateProduct(
//              String productname
//            , String productline
//            , String productscale
//            , String productvendor
//            , String productDescription
//            , double quantityInStock
//            , double buyPrice
//            , double MSRP) {
//        productRepository.updateProductByFields(productname, productline, productscale, productvendor, productDescription, quantityInStock, buyPrice, MSRP);
//
//    }
    // CART METHODS CART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODSCART METHODS
    public void increaseStockQuantityBatch(short quantityInStock, String productcode) {
        productRepository.increaseStockQuantityBatch(quantityInStock, productcode);
    }
    /****
     * This will update the stock quantity when Items are returned from the cart
     * @param productcode Key of the virtual cart map
     */
    public void increaseStockQuantity(String productcode) {
        System.out.println("Test Decrease Quantities is called with parameters :" + "and " + productcode);
        productRepository.increaseStockQuantity(productcode);
    }
    /****
     * Decrease product stock when Items are moved to virtual cart
     * @param productcode
     */
    public void decreaseStockQuantity(String productcode) {
        System.out.println("Test Decrease Quantities is called with parameters :" + "and " + productcode);
        productRepository.decreaseStockQuantity(productcode);
    }
    public List<ProductCartDTO> listAllCartItems() {
        List<ProductCartDTO> listOfCartProducts = new ArrayList<>();
        for (String key : virtualCart.keySet()) {
            ProductCartDTO cartItem = new ProductCartDTO();
            cartItem.setProductCode(productRepository.findByProductCode(key).getProductCode());
            cartItem.setProductName(productRepository.findByProductCode(key).getProductName());
            cartItem.setProductVendor(productRepository.findByProductCode(key).getProductVendor());
            cartItem.setBuyPrice(productRepository.findByProductCode(key).getBuyPrice());
            cartItem.setItemsQuantity(virtualCart.get(key));
            cartItem.setDeliveryDate(Util.shippingDate());
            cartItem.setDeliveryTime(Util.shippingTime());
            cartItem.setTotalPrice(productRepository.findByProductCode(key).getBuyPrice() * virtualCart.get(key));
            listOfCartProducts.add(cartItem);
        } // TODO include tax at purchase
        return listOfCartProducts;
    }
    public void addItemToVirtualCart(String productcode) {
        if (!virtualCart.containsKey(productcode)) {
            virtualCart.put(productcode, defaultCartQuantity);
        } else {
            Short currentQuantity = virtualCart.get(productcode);
            virtualCart.put(productcode, ++currentQuantity);
        }
    }
    public void removeItemFromVirtualCart(String productCode) {
        virtualCart.remove(productCode);
    }
    public Short getQuantityFromVirtualCart(String productcode) {
        //TODO Error handling
        Short orderedQuantity = virtualCart.get(productcode);
        return orderedQuantity;
    }
    public void reduceQuantityFromVirtualCart(String productcode) {
        short quantity = getQuantityFromVirtualCart(productcode);
        if (quantity > 1) {
            quantity--;
            virtualCart.put(productcode, quantity);
            increaseStockQuantity(productcode);

        } else
            removeItemFromVirtualCart(productcode);
        increaseStockQuantity(productcode);
    }
    public void addQuantityInCart(String productcode) {
        Short q = virtualCart.get(productcode);
        virtualCart.put(productcode, q);
    }
    public void clearCart() {
        virtualCart.clear();
    }
    public void testDisplayCartContent() {
        for (String key : virtualCart.keySet()) {
            System.out.println("Cart: ProductCode :" + key + "quantity in cart :" + virtualCart.get(key));
            Product p = findProductByProductCode(key);
            System.out.println("Database: ProductCode :" + p.getProductCode() + "quantity in cart :" + p.getQuantityInStock());
        }
        // TODO Attempt
//    public static AtomicReference<Short> totalPrducts() {
//        AtomicReference<Short> atomicSum = new AtomicReference<>();
//        mapOfCart.entrySet().forEach(e -> e.setValue(atomicSum.accumulateAndGet(e.getValue(), (x, y) -> x + y)));
//        return atomicSum;
//    }
        //TODO
//    public static double totalCharges() {
//        double sumOfQuantityIncart = 9.00;
//        return sumOfQuantityIncart;
//    }
    }
}