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
@SequenceGenerator(name = "orderNumber_Seq", initialValue = 10500, allocationSize = 20000)
public class OrderDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_Seq")
    String orderNumber;//  int(11) NOT NULL, FK from Orders
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_Seq")
    String productCode; // varchar(15) NOT NULL, FK from Product
    @Column(nullable = false)
    Integer quantityOrdered ;// int(11) NOT NULL,
    @Column(nullable = false, precision = 10, scale = 2)
    String priceEach;// decimal(10,2) NOT NULL,
    @Column(nullable = false, length = 6)
    String orderLineNumber;// smallint(6) NOT NULL

}