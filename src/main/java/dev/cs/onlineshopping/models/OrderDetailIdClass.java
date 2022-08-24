package dev.cs.onlineshopping.models;
import java.io.Serializable;
import java.util.Objects;
public class OrderDetailIdClass implements Serializable {
    private Integer orderNumber;
    private String productCode;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailIdClass that = (OrderDetailIdClass) o;
        return Objects.equals(orderNumber, that.orderNumber) && Objects.equals(productCode, that.productCode);
    }
    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productCode);
    }
    public OrderDetailIdClass() {
    }
}