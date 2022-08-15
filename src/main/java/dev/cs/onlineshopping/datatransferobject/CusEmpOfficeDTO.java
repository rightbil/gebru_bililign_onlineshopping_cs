package dev.cs.onlineshopping.datatransferobject;

import lombok.Data;

@Data
// this is to send data from server to client
// ? to avoid fields that should not send over
// DTO and Entity mapping needs types to be the same
public class CusEmpOfficeDTO {
    // employee
    private int employeeNumber;// int(11)NOT NULL,
    private String lastName;// varchar(50)NOT NULL,
    private String firstName;// varchar(50)NOT NULL,
    // customer
    String customerNumber;// int(11)NOT NULL,
    String customerName;// varchar(50)NOT NULL,

    // office
    String officeCode;
}
