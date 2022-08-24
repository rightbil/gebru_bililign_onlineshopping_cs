package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.dtos.ProductVirtualCartDTO;
import dev.cs.onlineshopping.models.*;
import dev.cs.onlineshopping.repositories.CustomerRepository;
import dev.cs.onlineshopping.repositories.ProductRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private PaymentService paymentService;
    /****
     *
     * @param pageRequest the current page
     * @return return all products in the form of page
     */
    public Page<Product> listAllStoreProducts(PageRequest pageRequest) {
        //TODO filter this based on quantity > 0
        return productRepository.findAll(pageRequest);
    }
    /****
     *
     * @param productCode
     * @return product matching the productCode
     */
    public Product getProductByProductCode(@Param("productcode") String productCode) {
        return productRepository.findByProductCode(productCode);
    }
    /***
     * Process Orders for the Items in my virtualCart
     *  step 1:  Get email form Cookies
     *
     * @param request
     */
    public void processMyOrders(HttpServletRequest request) {
        // 1. Get email form Cookies
        String email = Util.getCookieValueByName(request, "userName");
        // 2. Find CustomerNumber using email
        Optional<Customer> customer = customerRepository.findCustomerByEmail(email);
        // 3. Save the order including the customer number from step 2 and return the order number generated
        Integer newOrderNumber = ordersService.saveMyOrders(customer.get().getCustomerNumber());
        // 4. save the order detail for every product in virtual cart including order number generated in step 3
        orderDetailService.saveOrderDetail(newOrderNumber);
        // 5.  Save the payment for the order including the customer number and total charges from virtualCart
        paymentService.savePayment(customer.get().getCustomerNumber());
        // 6 Clear virtual cart
        clearVirtualCart();

    }
    /***
     *
     * @param product saves a product to the database table
     */
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
    public void deleteProduct(String productcode) {
        productRepository.delete(findProductByProductCode(productcode));
    }
    /****
     *
     * @param quantityInStock products quantity being removed form cart
     * @param productcode
     */
    public void increaseStockQuantityBatch(short quantityInStock, String productcode) {
        productRepository.increaseStockQuantityBatch(quantityInStock, productcode);
    }
    /****
     * This will update the stock quantity (+1) when products quantity reduced in cart
     * @param productcode Key of the virtual cart map
     */
    public void increaseStockQuantity(String productcode) {
        productRepository.increaseStockQuantity(productcode);
    }
    /****
     * This will update the stock quantity (-1) when products quantity added in cart
     * @param productcode
     */
    public void decreaseStockQuantity(String productcode) {
        productRepository.decreaseStockQuantity(productcode);
    }
    /****
     * This ProductCartDTO cantains info from Product and virtualCart.
     * @return
     */
    public List<ProductVirtualCartDTO> listAllCartItems() {
        List<ProductVirtualCartDTO> listOfCartProducts = new ArrayList<>();
        for (String key : virtualCart.keySet()) {
            ProductVirtualCartDTO cartItem = new ProductVirtualCartDTO();
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
    /***
     *
     * @param productcode
     */
    public void reduceQuantityFromVirtualCart(String productcode) {
        short quantity = getQuantityFromVirtualCart(productcode);
        if (quantity > 1) {
//            reduce cart quantity by 1 if quantity in stock is more than one
            quantity--;
            virtualCart.put(productcode, quantity);
//            add the returned quantity back to stock
            increaseStockQuantity(productcode);

        } else {
//            remove the item from virtual cart
            removeItemFromVirtualCart(productcode);
//            increaseStockQuantityBatch();
            increaseStockQuantity(productcode);
        }
    }
    public void addQuantityInCart(String productcode) {
        Short q = virtualCart.get(productcode);
        virtualCart.put(productcode, q);
    }
    public void clearVirtualCart() {
        virtualCart.clear();
    }
    /****
     *
     * @return total charges towards the customer for the items checked out
     */
    public double totalCharges() {
        double totalCharge = 0.0;
        for (String p : virtualCart.keySet()) {
            totalCharge = totalCharge + virtualCart.get(p) * productRepository.findByProductCode(p).getBuyPrice();

        }
        Double truncatedDouble = BigDecimal.valueOf(totalCharge)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        return truncatedDouble;
    }
    /****
     *
     * @param productname
     * @return product(s) in pages matching product names ( case insensitive search)
     */
    public Page<Product> searchProductByName(String productname) {
        String productNameUpper = productname.toUpperCase();
        Pageable p = PageRequest.of(0, 5);
        List<Product> finalList = new ArrayList<>();
        List<Product> list = productRepository.findAll();
        if (productname != "")
            finalList = list
                    .stream()
                    .filter(pp -> pp.getProductName().toUpperCase()
                            .contains(productNameUpper))
                    .collect(Collectors.toList());
        else
            finalList = list;
        final int start = (int) p.getOffset();
        final int end = Math.min((start + p.getPageSize()), finalList.size());
        final Page<Product> page = new PageImpl<>(finalList.subList(start, end), p, finalList.size());
        return page;

    }

}