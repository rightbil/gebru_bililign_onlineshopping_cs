package dev.cs.onlineshopping.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.lang.module.FindException;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "product")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable()
//@NamedQueries({
//@NamedQuery(name = "sqlSearchProductByProductCode", query = "FROM Product p where p.productCode=:productcode")
//})
public class Product{
    @Id
//    @Setter(AccessLevel.PRIVATE)
    String productCode;// varchar(15) NOT NULL, composite key
    @Column(nullable = false, length = 70)
    String productName;// varchar(70) NOT NULL,
//    @Id
//    @Setter(AccessLevel.PRIVATE)
    String productLine;// varchar(50) NOT NULL, Foreign key
    @Column(nullable = false, length = 50)
    String productScale;// varchar(10) NOT NULL,
    @Column(nullable = false, length = 50)
    String productVendor;// varchar(50) NOT NULL,
    @Column(columnDefinition = "text")
    String productDescription;// text NOT NULL,
    @Column(nullable = false, length = 6)
    Short quantityInStock;// smallint(6) NOT NULL,
    @Column(nullable = false, precision = 10, scale = 2)
    double buyPrice;// decimal(10,2) NOT NULL,
    @Column(nullable = false, precision = 10, scale = 2)
    double MSRP;// decimal(10,2) NOT NULL

//    Relationships
//    @JsonIgnore
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "productLine")
//    private ProductLine productLine;

}