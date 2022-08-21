package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.dtos.ProductCartDTO;
import dev.cs.onlineshopping.models.Customer;
import dev.cs.onlineshopping.models.OrderDetail;
import dev.cs.onlineshopping.models.Orders;
import dev.cs.onlineshopping.models.Product;
import dev.cs.onlineshopping.repositories.CustomerRepository;
import dev.cs.onlineshopping.repositories.ProductRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
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
    @Autowired
    private OrderDetailService orderDetailService;
    public Page<Product> listAllStoreProducts(PageRequest pageRequest) {
        //TODO filter this based on quantity > 0
//       Page<Product> pp = productRepository.findAll(pageRequest).stream()
//                .filter(product -> product.getQuantityInStock() > 0).collect(Collectors.toList());
        return productRepository.findAll(pageRequest);
    }
    public Product getProductByProductCode(@Param("productcode") String productCode) {
        return productRepository.findByProductCode(productCode);
    }
    public void processMyOrders(HttpServletRequest request) {
        // 1. Get email form Cookies
        String email = Util.getCookieValueByName(request, "userName");
        // 2. Find CustomerNumber using email
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
//        // 3. Insert Order to Order table - use customer number from step 2
        Orders o = new Orders();
        o.setOrderDate(Util.orderDate());
        o.setRequiredDate(Util.requiredDate());
        o.setStatus("OK"); // refer values
        o.getComments();
        o.setCustomerNumber(customer.get().getCustomerNumber());
        Integer newOrderNumber = ordersService.saveMyOrders(o);
        // save order and get the saved id for orderDetails
        Double totalCharge = 0.0;
        for (String pc : virtualCart.keySet()) {
            var od = new OrderDetail();
            od.setOrderNumber(newOrderNumber);
            od.setProductCode(pc);
            od.setQuantityOrdered(virtualCart.get(pc));
            od.setPriceEach(productRepository.findByProductCode(pc).getBuyPrice());
            od.setOrderLineNumber(productRepository.findByProductCode(pc).getProductLine());
            orderDetailService.saveOrderDetail(od);
         }
        clearVirtualCart();

//        System.out.println("Dear Customer " + customer.get().getContactFirstName() + " " + customer.get().getContactLastName() +
//                "\n Your order is on the way and its reference is " );
//                + newOrderNumber + Util.requiredDate() +
//                "\n Product details are:\n " + virtualCart.keySet() +
//                "\nExpected Delivery date" + ordersService.findOrderByOrderNumber(newOrderNumber) +
//                "\nYour card on file is charged $ " + totalCharge + "\n Thank you");

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
        productRepository.increaseStockQuantity(productcode);
    }
    /****
     * Decrease product stock when Items are moved to virtual cart
     * @param productcode
     */
    public void decreaseStockQuantity(String productcode) {
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
            cartItem.setDeliveryDate(Util.requiredDate());
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
    public void clearVirtualCart() {
        virtualCart.clear();
    }
    public void testDisplayCartContent() {
        for (String key : virtualCart.keySet()) {
            Product p = findProductByProductCode(key);

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