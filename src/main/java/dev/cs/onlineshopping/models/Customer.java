package dev.cs.onlineshopping.models;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
//@Getter
//@Setter
//@ToString
//@ToString.Exclude for specific fields to be excluded in the output
//@EqualsAndHashCode
//@RequiredArgsConstructor
// All non-initialized final fields get a parameter,
// as well as any fields that are marked as @NonNull that
// aren’t initialized where they are declared.
//@Data // a shortcut for all of the above 5 Annotations
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//@NoArgsConstructor
// generate constructor with out args
//@Accessors(fluent = true) // read on this
//@AllArgsConstructor
//generates a constructor with 1 parameter for each field in your class.
//Fields marked with @NonNull result in null checks on those parameters.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // make it JPA entity
@Table(name = "customer" ) // make table name "customers" in db
//@Builder // read more on build
@FieldDefaults(level = AccessLevel.PRIVATE) //make all fields access specifier private
/***
 * the current max customer number is 496 so new once start form 500 , max is 999
 */
@SequenceGenerator(name = "customerNumber_Seq", initialValue = 500, allocationSize = 999)
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
    //    Relationships
    // with orders -- now
    // TODO with payments -- hold
    // TODO employees --- hold on R100
    //    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    //    @JoinColumn(name = "employeeNumber",
    //            nullable = false
    //            , foreignKey = @ForeignKey(name = "salesRepEmployeeNumber"))
    //    private Employee employee;

}