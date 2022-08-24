package dev.cs.onlineshopping.utility;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public final class Util {
    public static LocalDate requiredDate() {
        LocalDate deliveryDate = LocalDate.now().plusDays(Util.generateDeliveryLagDate());
        return deliveryDate;

    }
    public static LocalDate orderDate() {
        LocalDate deliveryDate = LocalDate.now();
        return deliveryDate;

    }
    public static String shippingTime() {
        LocalDateTime deliveryTime = LocalDateTime.now().plusHours(generateDeliveryLagDate());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(" HH:00");
        return timeFormatter.format(deliveryTime);
    }
    private static int generateDeliveryLagDate() {
        return (int) (Math.random() * 5);

    }
    public static String getCookieValueByName(HttpServletRequest request, String cookieKey) {
        String emailValue = "";
        var cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieKey)) {
                    emailValue = cookies[i].getValue();

                }
            }
        }
        return emailValue;
    }
}