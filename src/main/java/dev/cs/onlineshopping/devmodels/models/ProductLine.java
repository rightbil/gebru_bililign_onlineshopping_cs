package dev.cs.onlineshopping.devmodels.models;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productline")
@FieldDefaults(level = AccessLevel.PRIVATE)
@SequenceGenerator(name = "orderNumber_Seq", initialValue = 10500, allocationSize = 1)
public class ProductLine {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_Seq")
    String productLine;//varchar(50) NOT NULL,
    @Column(columnDefinition = "varchar(4000) default NULL")
    String textDescription;// varchar(4000) DEFAULT NULL,
    //TODO remove the following
//    @ColumnDefault("This is my default name")
    @Column(columnDefinition = "mediumtext")
    String htmlDescription;// mediumtext,
//    @Lob
    @Column(columnDefinition = "mediumBLOB")
    String image;// mediumblob,

}