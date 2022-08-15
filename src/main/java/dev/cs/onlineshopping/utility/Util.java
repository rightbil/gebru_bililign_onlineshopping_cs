package dev.cs.onlineshopping.utility;
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

}