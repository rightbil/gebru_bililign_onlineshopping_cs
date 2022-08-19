package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.dtos.ProductCartDTO;
import dev.cs.onlineshopping.models.Product;
import dev.cs.onlineshopping.repositories.ProductRepository;
import dev.cs.onlineshopping.utility.Util;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class ProductService {
    /***
     * productcode (pk) and the number of quantities you are purchasing
     */
    private final short defaultCartQuantity = (short) 1;
    static Map<String, Short> mapOfCart = new HashMap<>();
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Page<Product> showAllProducts(PageRequest pageRequest) {
//TODO filter this based on quantity > 0
        List<Product> page = productRepository.findAll(pageRequest).stream()
                .filter(product -> product.getQuantityInStock() > 0).collect(Collectors.toList());
        return productRepository.findAll(pageRequest);
    }
    public Product getProductDetail(@Param("productcode") String productCode) {
        return productRepository.findByProductCode(productCode);
    }
    //TODO should be Orders,
    // Customer  information - Address and email are needed for information
    // Product Information- already here
    // Additional messages : Your Items are sent to 11548 Stewart Ln Apt A2. we also sent you
    // an email. let us know if you can give us a feed back on your purchase experiance.
    // Email
    // product quantiry shall be reduced by the item quantity but chek at the time of adding to orders too
    public List<ProductCartDTO> showCartItems() {
        List<ProductCartDTO> cartProduct = new ArrayList<>();
        for (String key : mapOfCart.keySet()) {
            ProductCartDTO cartItem = new ProductCartDTO();
            cartItem.setProductCode(productRepository.findByProductCode(key).getProductCode());
            cartItem.setProductName(productRepository.findByProductCode(key).getProductName());
            cartItem.setProductVendor(productRepository.findByProductCode(key).getProductVendor());
            cartItem.setBuyPrice(productRepository.findByProductCode(key).getBuyPrice());
            cartItem.setItemsQuantity(mapOfCart.get(key));
            cartItem.setDeliveryDate(Util.shippingDate());
            cartItem.setDeliveryTime(Util.shippingTime());
            cartItem.setTotalPrice(productRepository.findByProductCode(key).getBuyPrice() *
                    mapOfCart.get(key));
            cartProduct.add(cartItem);
        } // TODO include tax
        return cartProduct;
    }
    public void addItemToCart(Product product) {
        if (!mapOfCart.containsKey(product.getProductCode())) {
            mapOfCart.put(product.getProductCode(), defaultCartQuantity);
        } else {
            Short currentQuantity = mapOfCart.get(product.getProductCode());
            mapOfCart.put(product.getProductCode(), ++currentQuantity);
        }
    }
    // TODO
//    public static AtomicReference<Short> totalPrducts() {
//        AtomicReference<Short> atomicSum = new AtomicReference<>();
//        mapOfCart.entrySet().forEach(e -> e.setValue(atomicSum.accumulateAndGet(e.getValue(), (x, y) -> x + y)));
//        return atomicSum;
//    }
    //TODO -- come and fix this part
    public static double totalCharges() {
        double sumOfQuantityIncart = 9.00;
        return sumOfQuantityIncart;
    }
    public void clearCart() {
        mapOfCart.clear();
    }
    public void removeCartItem(String productCode) {
        mapOfCart.remove(productCode);
    }
    //    public void reduceQuantiryfromCart(String productCode) {
//        Short currentProductQuantity = mapOfCart.get(productCode);
//        if (currentProductQuantity > 1) {
//            mapOfCart.put(productCode, currentProductQuantity - defaultCartQuantity);
//        } else
//            removeItemfromCart(productCode);
//    }
//    public void addQuantityInCart(String productcode) {
//        Short q = mapOfCart.get(productcode);
//        mapOfCart.put(productcode,q);
//    }
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
    public void displayCartContent() {
        for (String key : mapOfCart.keySet()) {
            System.out.println("Cart: ProductCode :" + key + "quantity in cart :" + mapOfCart.get(key));
            Product p = findProductByProductCode(key);
            System.out.println("Database: ProductCode :" + p.getProductCode() + "quantity in cart :" + p.getQuantityInStock());
        }

    }
    public Short getQuantityInCart(String productCode) {
        return mapOfCart.get(productCode);
    }
    /****
     *
     * @param quantityInStock to reduce pass -ve value to add pass positive value
     */
    public void decreaseProductQuantityByCart(Short quantityInStock, String productcode) {
        System.out.println("Decrease Quantities is called with parameters :" + quantityInStock + "and " + productcode);
        productRepository.decreaseAvailableProductQuantity(quantityInStock, productcode);
    }



//    increaseAvailableProductQuantity

    public void returnQuantityByCart(Short quantityInStock, String productcode) {
        System.out.println("SDecrease Quantities is called with parameters :" + quantityInStock + "and " + productcode);
        productRepository.decreaseAvailableProductQuantity(quantityInStock, productcode);
    }
}