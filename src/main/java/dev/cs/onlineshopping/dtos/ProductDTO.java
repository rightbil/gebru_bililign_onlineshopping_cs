package dev.cs.onlineshopping.dtos;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    String productCode;
    String productName;
    String productVendor;
    double buyPrice;
    LocalDate deliveryDate;
    String deliveryTime;
    int itemsQuantity;
    double totalPrice;
//    double MSRP;
//    String Comment;
//    String ShippingAddress;
}