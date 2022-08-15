package dev.cs.onlineshopping.devmodels;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "payment_Seq", initialValue = 10500, allocationSize = 1)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_Seq")
    //@Column(name = "STOCK_ID", unique = true, nullable = false)
    Integer customerNumber;// int(11) NOT NULL,
    @Column(nullable = false, length = 50)
    String checkNumber;// varchar(50) NOT NULL,
    @Column(nullable = false)
    @Basic
    @Temporal(TemporalType.DATE)
    Date paymentDate;// date NOT NULL,
    @Column(nullable = false, precision = 10, scale = 2)
    double amount;// decimal(10,2);  NOT NULL
}