import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nich on 1/31/15.
 */
public class Order {
    String email;
    String tripId;
    public Order(Entity order){
        this.email = (String)order.getProperty("email");
        this.tripId = (String)order.getProperty("trip");
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