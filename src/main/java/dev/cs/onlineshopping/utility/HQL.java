package dev.cs.onlineshopping.utility;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
@NamedQueries({
//        @NamedQuery(name = "FindCoursesByStudentEmail", query = "FROM Student s inner join s.studentCourses where s.email=:email"),
//        @NamedQuery(name = "ListAllCourses", query = "FROM Course"),
//        @NamedQuery(name = "ListAllStudents", query = "FROM Student s order by s.name "),
//        @NamedQuery(name = "FindCourseById", query = "FROM Course c WHERE c.id=:id"),
//        @NamedQuery(name = "FindStudentByEmail", query = "FROM Student s WHERE s.email=:email"),
//        @NamedQuery(name = "FindStudentByEmailAndPassword", query = "FROM Student s WHERE s.email=:email and s.password=:password"),
//        @NamedQuery(name = "ShowStudentsAndCourse", query = "FROM Student s inner join s.studentCourses")
//search products by productcode
        @NamedQuery(name="sqlSearchProductByProductCode" , query= "FROM Product p where p.productCode=:productcode"),
        @NamedQuery(name ="sqlDeleteProductByProductCode", query="DELETE FROM Product p where p.productCode =:productcode")
})
public class HQL {
}