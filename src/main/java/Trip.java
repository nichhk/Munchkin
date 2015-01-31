/**
 * Created by nich on 1/31/15.
 */
public class Trip {
    String user;
    String eta;
    String etd;
    String restaurantTime;
    String restaurant;
    double flat;
    double percentage;
    int maxOrder;
    String acceptUntil;
    public Trip(String user, String eta, String etd, String restaurantTime, String restaurant,
                double flat, double percentage, int maxOrder, String acceptUntil){
        this.user = user;
        this.eta = eta;
        this.etd = etd;
        this.restaurantTime = restaurantTime;
        this.restaurant = restaurant;
        this.flat = flat;
        this.percentage = percentage;
        this.maxOrder = maxOrder;
        this.acceptUntil = acceptUntil;
    }
}
