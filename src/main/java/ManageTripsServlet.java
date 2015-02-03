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

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "trip_manager");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        List<Trip> myTrips = new ArrayList<Trip>();
        int count = 0;
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        Query q = new Query("trip");
        Query.Filter onlyMine = new Query.FilterPredicate("user",
                Query.FilterOperator.EQUAL, email);

        PreparedQuery pq = datastore.prepare(q.setFilter(onlyMine));
        System.out.println("IN MANAGE");

        for(Entity aTrip: pq.asIterable()){
            //System.out.println("ONETRIP");
            myTrips.add(new Trip(aTrip));
            long key = myTrips.get(count).time ;
            Query q1 = new Query("order");
            Query.Filter thisTrip = new Query.FilterPredicate("trip",Query.FilterOperator.EQUAL, Long.toString(key));
            System.out.println("key is"+key);
            PreparedQuery pq1 = datastore.prepare(q1.setFilter(thisTrip));
            //PreparedQuery pq1 = datastore.prepare(q1);
            for(Entity order: pq1.asIterable()){
                //System.out.println("ONE FRIED");
                System.out.println(order.getProperty("trip"));
                String cusEmail = (String)order.getProperty("email");

                System.out.println(cusEmail);
                try {
                    Entity customer = datastore.get(KeyFactory.createKey("profile",cusEmail));
                    myTrips.get(count).addCustomer(customer.getProperty("firstName") + " " +
                            customer.getProperty("lastName")+ " " + customer.getProperty("email") +",");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            count++;
        }
        req.setAttribute("responseJson", new Gson().toJson(myTrips));
        try {
            req.getRequestDispatcher("ViewMyCurrentTrips.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
