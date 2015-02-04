import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by compsci on 1/31/15.
 */
public class ManageTripsServlet extends TripServlet {
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private QueryManager queryManager = new QueryManager();
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "trip_manager");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        int count = 0;
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        List<Entity> myTrips = queryManager.query("trip","user",email,100000,Query.FilterOperator.EQUAL);
        ArrayList<Trip>returnTrips = new ArrayList<Trip>();
        for(Entity aTrip: myTrips){
            returnTrips.add(new Trip(aTrip));
            Query orders = new Query("order").setAncestor(aTrip.getKey());
            PreparedQuery pq = datastore.prepare(orders);
            for(Entity order: pq.asIterable()){
                String cusEmail = (String)order.getProperty("email");
                try {
                    Entity customer = datastore.get(KeyFactory.createKey("profile",cusEmail));
                    returnTrips.get(count).addCustomer(customer.getProperty("firstName") + " " +
                            customer.getProperty("lastName")+ " " + customer.getProperty("email") +",");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            count++;
        }
        req.setAttribute("responseJson", new Gson().toJson(returnTrips));
        System.out.println(new Gson().toJson(returnTrips));
        try {
            req.getRequestDispatcher("ViewMyCurrentTrips.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
