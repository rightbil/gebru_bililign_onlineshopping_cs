package dev.cs.onlineshopping.jic;

/****
 *
 * @param name private variable
 * @param employeeNumber private variable
 */
public record EmployeeRecord(String name, int employeeNumber) {
    public static final String DEFAULT_EMPLOYEE_NAME="KESE";
    // private String instanceVariableArenotAllowed;
    // Never inherit anything
    // final class so not allowed to inherit them
    // they implement interfaces
    // you can override the connanical constructor if validation is needed
    public EmployeeRecord(String name , int employeeNumber){
        if(employeeNumber < 0){
          throw  new IllegalArgumentException("can not be negative");
        }
        this.name= name;
        this.employeeNumber = employeeNumber;
    }
    // anotther way is compact constructor equla to the above
//    public EmployeeRecord{
//        if(employeeNumber < 0){
//            throw  new IllegalArgumentException("can not be negative");
//        }

    //}
    /****
     * two private final fields
     * getters  each data members
     * note: getters dont have get in the front
     * No setters , records are immmutale by default
     * equals and hashcode tostring overriding methods
     * all argument constructor
     *
     */
public  String nameInUpperCase(){
    return name.toUpperCase();
}
public static void  whatEver(){

}
// main method
    //EmployeeRecord employeeRecord = new EmployeeRecord("Bililing", 12345);
    //employeeRecord.name;
    //employeeRecord.nameInUpperCase();
    //employeeRecord.whatEver();
}