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

    /* Finds the trips that have been created by this user
    */

    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        req.setAttribute("page", "trip_manager");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        int count = 0;
        ArrayList<Trip>returnTrips = new ArrayList<Trip>(); // ArrayList of the trips to be returned
        List<Entity> myTrips = queryManager.query("trip","user",email,100000,Query.FilterOperator.EQUAL);
        // Finds the trip that have been created by the OAuth'd email
        for(Entity aTrip: myTrips){
            returnTrips.add(new Trip(aTrip)); // Serializes the trip in an object
            long numResults =(long)aTrip.getProperty("maxOrder"); // Finds the maxOrders that a trip can take
            List<Entity>tripOrders = queryManager.query("order","trip", KeyFactory.keyToString(aTrip.getKey()), (int)numResults,Query.FilterOperator.EQUAL);
            // Finds the orders associated with a trip
            for(Entity order: tripOrders){
                String customerEmail = (String)order.getProperty("email"); // Email of the customer associated with an order
                try {
                    // Serializes the customer data for a given trip
                    Entity customer = datastore.get(KeyFactory.createKey("profile",customerEmail));
                    returnTrips.get(count).addCustomer(customer.getProperty("firstName") + " " +
                            customer.getProperty("lastName")+ " " + customer.getProperty("email") +" ");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            count++;
        }
        req.setAttribute("responseJson", new Gson().toJson(returnTrips)); // Serializes the trip information
        System.out.println(new Gson().toJson(returnTrips));
        try {
            req.getRequestDispatcher("ViewMyCurrentTrips.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
