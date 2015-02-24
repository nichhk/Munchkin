import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by compsci on 1/31/15.
 */
public class HomeServlet extends HttpServlet {

    private QueryManager queryManager = new QueryManager();

    /* Called by users that do not have an account on Munchin.com. Redirects them to the CreateProfile.jsp
     */

    public void doGet(HttpServletRequest req, HttpServletResponse resp){

        req.setAttribute("page", "null");
        if (!LoginStatus.getStatus()){ // True if the user has not logged in to an account
            req.setAttribute("isApproved", "0");
            req.setAttribute("log", LoginStatus.getLogInUrl("/create_profile"));
            try {
                req.setAttribute("logIn", LoginStatus.getLogInUrl("/"));
                req.setAttribute("createProf", LoginStatus.getLogInUrl("/create_profile"));
                req.getRequestDispatcher("Home2.jsp").forward(req, resp);
            } catch(Exception e) {e.printStackTrace();}
        }
        else{ // Runs if the user has logged in to an account
            UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
            com.google.appengine.api.users.User user = userService.getCurrentUser();
            String email = user.getEmail();
            req.setAttribute("isApproved", "1");
            req.setAttribute("log", LoginStatus.getLogOutUrl("/"));

            long curTime = new Date().getTime(); // Calculates the current time as a milliseconds timestamp


            List<Entity> queriedTrips = queryManager.query("trip","lastOrder", curTime-21600000, 1000,Query.FilterOperator.GREATER_THAN);
            // Queries the trips to find those that are still available for orders
            ArrayList<Trip> trips = new ArrayList<Trip>(); // Valid trips
            HashSet<Key> unique = new HashSet<Key>(); // Checks to see if a user has already joined a trip
            List<Entity> myOrders = queryManager.query("order", "email", email ,1000, Query.FilterOperator.EQUAL);
            // Checks the orders that belong to this user
            for(Entity order:myOrders){

                unique.add(order.getParent()); // Adds the keys of the trips the users have joined

            }

            for (Entity trip : queriedTrips){
                if(!unique.contains(trip.getKey())) { // True if the user has not gone on said trip
                    trips.add(new Trip(trip)); // Serializes the trip entity to a trip object in the ArrayList
                }
            }
            req.setAttribute("trips", new Gson().toJson(trips)); // Returns a Json representation of the trips
            try{
                req.getRequestDispatcher("Home1.jsp").forward(req, resp); // Sends the user back to the home page
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
