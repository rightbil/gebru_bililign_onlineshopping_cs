package dev.cs.onlineshopping.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // make it JPA entity
@Table(name = "Student") // make table name "customers" in db
@FieldDefaults(level = AccessLevel.PRIVATE) //make all fields access specifier private
@SequenceGenerator(name = "customerNumber_Seq", initialValue = 1000, allocationSize = 1)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerNumber_Seq")
    @Setter(AccessLevel.PRIVATE)
    Integer StudentId;// int(11)NOT NULL,
    //TODo    @Getter(AccessLevel.PRIVATE)
    //TODO    @ToString.Exclude private OtherClassName otherClassName;
    @Column(length = 50)
    String FirstName; // Varchar(50) NULL
    @Column(nullable = false, length = 50)
    String MiddleName;// varchar(50)NOT NULL,
    @Column(nullable = false, length = 50)
    String LastName;// varchar(50)NOT NULL,
    @Column(nullable = false)
    LocalDate DateOfBirth;
    @Column(nullable = false, length = 1)
    String Gender;
    @Column(nullable = false, length = 4)
    Integer EnrolledYear;
    @Lob
    @Column(nullable = false, name = "Photo", columnDefinition = "LONGBLOB")
    byte[] Photo;
    @Column(nullable = false, length = 50)
    String KifleKetema;// varchar(50)NOT NULL,
    @Column(columnDefinition = "varchar(50) default NULL")
    String Kebele;// varchar(50)DEFAULT NULL,
    @Column(nullable = false, length = 50)
    String HouseNumber;// varchar(50)NOT NULL,
    @Column(nullable = false, length = 50)
    String phone;// varchar(50)NOT NULL,
    String Comment;

    @Transient
    public int getAge() {

        if (DateOfBirth == null) {
            return 0;//
        } else

            return Period.between(DateOfBirth, LocalDate.now()).getYears();

    }

}



