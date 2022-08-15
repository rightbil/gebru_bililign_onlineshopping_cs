package dev.cs.onlineshopping.devmodels;
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity // make it JPA entity
//@Table(name = "employee")
public class Employee {
    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11, nullable = false)
    private int employeeNumber;// int(11)NOT NULL,

    @Column(nullable = false, length = 50)
    private String lastName;// varchar(50)NOT NULL,

    @Column(nullable = false, length = 50)
    private String firstName;// varchar(50)NOT NULL,

    @Column(nullable = false, length = 50)
    private String extension;// varchar(10)NOT NULL,

    @Column(nullable = false, length = 50)
    private String email;// varchar(100)NOT NULL,
// TODO as it is a foreign key we many not need it here
//    @Column(nullable = false, length = 50)
//    private String officeCode;// varchar(10)NOT NULL,

    @Column(nullable = false, length = 50)
    private String reportsTo;// int(11)DEFAULT NULL,
    @Column(nullable = false, length = 50)
    private String jobTitle;// varchar(50)NOT NULL

    // Relations
// A self join on reportsTo field 0..1 r/sh
    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "employeeNumber")
    private Customer customer;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    private Customer employee;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "officeCode")
    private Office office;*/
}