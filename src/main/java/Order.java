import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nich on 1/31/15.
 */
public class Order {
    List<Item> items = new ArrayList<Item>();
    String email;
    String totalPrice;
    String tripId;

    public Order(Entity order, String tripId, double totalPrice){
        this.email = (String)order.getProperty("email");
        this.totalPrice = Double.toString(totalPrice);
        this.tripId = tripId;
    }
    public class Item{
        public String foodItem;
        public String priceMax;
        public String comments;
        public Item alt;
        private boolean isAlt;
        public Item(Entity item){
            this.foodItem = (String)item.getProperty("foodItem");
            this.priceMax = (String)item.getProperty("priceMax");
            this.comments = (String)item.getProperty("comments");

        }
    }
}