package dev.cs.onlineshopping.models;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orderdetail")
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(OrderDetailIdClass.class)
@SequenceGenerator(name = "orderNumber_Seq", initialValue = 10500, allocationSize = 1)
public class OrderDetail implements Serializable {
    @Id
    Integer orderNumber;//  int(11) NOT NULL, FK from Orders
    @Id
    String productCode; // varchar(15) NOT NULL, FK from Product
    @Column(nullable = false)
    Short quantityOrdered;// int(11) NOT NULL,
    @Column(nullable = false, precision = 10, scale = 2)
    double priceEach;// decimal(10,2) NOT NULL,
    @Column(nullable = false)
    String orderLineNumber;// smallint(6) NOT NULL

}