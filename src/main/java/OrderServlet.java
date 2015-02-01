import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by compsci on 1/31/15.
 */
public class OrderServlet extends HttpServlet {
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        int numItems = Integer.parseInt(req.getParameter("numItems"));
        Entity order = new Entity("order", KeyFactory.createKey("trip", req.getParameter("tripId")));
        order.setProperty("email", LoginStatus.getUserEmail());
        for (int i = 1; i <= numItems; i++){
            Entity item = new Entity("item", order.getKey());
            item.setProperty("foodItem", req.getParameter("foodItem" + i));
            item.setProperty("priceMin", req.getParameter("priceMin" + i));
            item.setProperty("priceMax", req.getParameter("priceMax" + i));
            item.setProperty("comments", req.getParameter("comments" + i));
            datastore.put(item);
            if (req.getParameter("altFoodItem"+i) != null){
                Entity alt = new Entity("alt", item.getKey());
                alt.setProperty("foodItem", req.getParameter("altFoodItem" + i));
                alt.setProperty("priceMin", req.getParameter("altPriceMin" + i));
                alt.setProperty("priceMax", req.getParameter("altPriceMax" + i));
                alt.setProperty("comments", req.getParameter("altComments" + i));
                datastore.put(alt);
            }
        }
        datastore.put(order);
    }
}
