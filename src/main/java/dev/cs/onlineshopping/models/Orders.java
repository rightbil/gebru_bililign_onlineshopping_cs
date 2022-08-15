package dev.cs.onlineshopping.models;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // make it JPA entity
@Table(name = "orders") // make table name "customers" in db
@FieldDefaults(level = AccessLevel.PRIVATE) //make all fields access specifier private
@SequenceGenerator(name = "orderNumber_Seq", initialValue = 10500, allocationSize = 20000)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_Seq")
    @Setter(AccessLevel.PRIVATE)
    Integer orderNumber; //int(11) NOT NULL,
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    Date orderDate;// date NOT NULL,
    @Basic
    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    Date requiredDate;// date NOT NULL,
    @Basic
    @Temporal(TemporalType.DATE)
    //@Column( columnDefinition = "Date Local")
    Date shippedDate;// date DEFAULT NULL,
    @Column(nullable = false, length = 10)
    String status;// varchar(15) NOT NULL,
    @Column(columnDefinition = "text")
    String comments;// text,
    //TODO FK
    @Column(nullable = false, length = 50)
    Integer customerNumber;// int(11) NOT NULL
    @Transient //TODO this is for testing and can be deleted
    String DeveloperNames;
}