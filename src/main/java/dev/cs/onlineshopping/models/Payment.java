package dev.cs.onlineshopping.models;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    @Id
    Integer customerNumber;// int(11) NOT NULL,
    @Column(nullable = false, length = 50)
    String checkNumber;// varchar(50) NOT NULL,
    @Column(nullable = false)
    LocalDate paymentDate;// date NOT NULL,
    @Column(nullable = false, precision = 10, scale = 2)
    double amount;// decimal(10,2);  NOT NULL
}