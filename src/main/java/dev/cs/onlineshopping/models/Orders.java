package dev.cs.onlineshopping.models;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "orderNumber_Seq", initialValue = 10500, allocationSize = 1)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_Seq")
    @Setter(AccessLevel.PRIVATE)
    Integer orderNumber; //int(11) NOT NULL,
    @Basic
    @Column(nullable = false)
    LocalDate orderDate;// date NOT NULL,
    @Basic
    @Column(nullable = false)
    LocalDate requiredDate;// date NOT NULL,
    @Basic
    LocalDate shipdDate;// date DEFAULT NULL,
    @Column(nullable = false, length = 10)
    String status;// varchar(15) NOT NULL,
    @Column(columnDefinition = "text")
    String comments;// text,
    //TODO FK
    @Column(nullable = false, length = 50)
    Integer customerNumber;// int(11) NOT NULL

}