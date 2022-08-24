package dev.cs.onlineshopping.utility;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
@NamedQueries({
        @NamedQuery(name = "sqlSearchProductByProductCode", query = "FROM Product p where p.productCode=:productcode"),
        @NamedQuery(name = "sqlDeleteProductByProductCode", query = "DELETE FROM Product p where p.productCode =:productcode"),
})
public class HQL {
}