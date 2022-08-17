package dev.cs.onlineshopping.services;
import dev.cs.onlineshopping.dtos.ProductDTO;
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
import java.util.concurrent.atomic.AtomicReference;
@Service
public class ProductService {
   static Map<String, Integer> mapOfCart = new HashMap<>();
    private final ProductRepository productRepository;
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    public Page<Product> showAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }
    public Product showProductDetail(@Param("productcode") String productCode) {
        return productRepository.findByProductCode(productCode);
    }
    //TODO should be Orders,
    // Customer  information - Address and email are needed for information
    // Product Information- already here
    // Additional messages : Your Items are sent to 11548 Stewart Ln Apt A2. we also sent you
    // an email. let us know if you can give us a feed back on your purchase experiance.
    // Email
    // product quantiry shall be reduced by the item quantity but chek at the time of adding to orders too
    public List<ProductDTO> showCartItems() {
        List<ProductDTO> cartProduct = new ArrayList<>();
        //double static totalCharges=1000;
        for (String key : mapOfCart.keySet()) {
            ProductDTO cartItem = new ProductDTO();
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
            mapOfCart.put(product.getProductCode(), 1);
        } else {
            Integer v = mapOfCart.get(product.getProductCode());
            mapOfCart.put(product.getProductCode(), v + 1);

        }
    }
    /***
     *
     * @return
     */
    public static AtomicReference<Integer> totalPrducts() {
        AtomicReference<Integer> atomicSum = new AtomicReference<>(0);
        mapOfCart.entrySet().forEach(e -> e.setValue(atomicSum.accumulateAndGet(e.getValue(), (x, y) -> x + y)));
        return atomicSum;
    }
    //TODO -- come and fix this part
    public static double totalCharges() {
        double sumOfQuantityIncart = 9.00;
        return sumOfQuantityIncart;
    }
    public void clearCart() {
        mapOfCart.clear();
    }
    public void removeItemfromCart(String productCode) {
        mapOfCart.remove(productCode);
    }
    public void reduceQuantiryfromCart(String productCode) {
        int currentProductQuantity = mapOfCart.get(productCode);
        if (currentProductQuantity > 1) {
            mapOfCart.put(productCode, currentProductQuantity - 1);
        } else
            removeItemfromCart(productCode);
    }
    public void addQuantityInCart(String productcode) {
        mapOfCart.put(productcode, mapOfCart.get(productcode) + 1);
    }

    // CRUD prodcuts
    public void saveProduct(Product product){
        productRepository.save(product);
    }
    // TODO explored
    public void updateProduct(String productCode , Product product){
        productRepository.save(product);
    }
    public void deleteProduct(String productCode){

        productRepository.deleteById(productCode);
    }

}