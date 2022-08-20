package dev.cs.onlineshopping.utility;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public final class Util {
    /****
     *
     * @return the formatted date and time of delivery with random extended date
     */
    public static LocalDate shippingDate() {
        LocalDate deliveryDate = LocalDate.now().plusDays(Util.calculateDeliveryDuration());
        // DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY", Locale.US);
        return deliveryDate;

    }
    public static String shippingTime() {
        LocalDateTime deliveryTime = LocalDateTime.now().plusHours(calculateDeliveryDuration());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(" HH:00");
        return timeFormatter.format(deliveryTime);
    }
    private static int calculateDeliveryDuration() {
        return (int) (Math.random() * 5);

    }
    public static String getCookieValueByName(HttpServletRequest request, String cookieKey) {
        String value = "";
        var cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals(cookieKey)) {
                    value = cookies[i].getValue();
                    System.out.println(value);
                }
            }
        }
        return value;
    }
}