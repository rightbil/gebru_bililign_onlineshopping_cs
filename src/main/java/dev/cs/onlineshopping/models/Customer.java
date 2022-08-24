package dev.cs.onlineshopping.models;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // make it JPA entity
@Table(name = "customer") // make table name "customers" in db
@FieldDefaults(level = AccessLevel.PRIVATE) //make all fields access specifier private
/***
 * the current max customer number is 496 so new once start form 500 , max is 999
 */
@SequenceGenerator(name = "customerNumber_Seq", initialValue = 500, allocationSize = 1)
public class Customer {
    //TODO    private static final Logger log = LoggerFactory.getLogger(LogExample.class);
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerNumber_Seq")
    /****
     * Id should not be set at all it should be generated and read
     */
    @Setter(AccessLevel.PRIVATE)
    Integer customerNumber;// int(11)NOT NULL,
    //TODo    @Getter(AccessLevel.PRIVATE)
    //TODO    @ToString.Exclude private OtherClassName otherClassName;
    @Column(length = 50)
    String customerName; // Varchar(50) NULL
    @Column(nullable = false, length = 50)
    String contactLastName;// varchar(50)NOT NULL,
    @Column(nullable = false, length = 50)
    String contactFirstName;// varchar(50)NOT NULL,
    @Column(nullable = false, length = 50)
    String phone;// varchar(50)NOT NULL,
    @Column(nullable = false, length = 50)
    String addressLine1;// varchar(50)NOT NULL,
    @Column(columnDefinition = "varchar(50) default NULL")
    String addressLine2;// varchar(50)DEFAULT NULL,
    @Column(nullable = false, length = 50)
    String city;// varchar(50)NOT NULL,
    @Column(columnDefinition = "varchar(50) default NULL")
    String state;// varchar(50)DEFAULT NULL,
    @Column(columnDefinition = "varchar(15) default NULL")
    String postalCode;// varchar(15)DEFAULT NULL,
    @Column(nullable = false, length = 50)
    String country;// varchar(50)NOT NULL,
    // TODO this will be replaced by relationship code R100 it is FK
    @Column(columnDefinition = "int default NULL")
    Integer salesRepEmployeeNumber;// int(11)DEFAULT NULL,
    @Column(columnDefinition = "decimal(10,2) default NULL")
    double creditLimit;// decimal(10,2)DEFAULT NULL
    String email;// Fk from user table
}