package dev.cs.onlineshopping.jic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSorting<T> {
    int recordCount;
    T response;
}