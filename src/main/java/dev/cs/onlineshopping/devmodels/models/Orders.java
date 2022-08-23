package dev.cs.onlineshopping.devmodels.models;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // make it JPA entity
@Table(name = "orders") // make table name "customers" in db
@FieldDefaults(level = AccessLevel.PRIVATE) //make all fields access specifier private
@SequenceGenerator(name = "orderNumber_Seq", initialValue = 10500, allocationSize = 1)
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_Seq")
    @Setter(AccessLevel.PRIVATE)
    Integer orderNumber; //int(11) NOT NULL,
    @Basic
   // @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    LocalDate orderDate;// date NOT NULL,
    @Basic
   // @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    LocalDate requiredDate;// date NOT NULL,
    @Basic
    //I@Temporal(TemporalType.DATE)
    //@Column( columnDefinition = "Date Local")
    LocalDate shipdDate;// date DEFAULT NULL,
    @Column(nullable = false, length = 10)
    String status;// varchar(15) NOT NULL,
    @Column(columnDefinition = "text")
    String comments;// text,
    //TODO FK
    @Column(nullable = false, length = 50)
    Integer customerNumber;// int(11) NOT NULL
//    @Transient //TODO this is for testing and can be deleted
//    String DeveloperNames;
}