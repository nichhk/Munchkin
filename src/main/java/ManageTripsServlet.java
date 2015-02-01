import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by compsci on 1/31/15.
 */
public class ManageTripsServlet extends TripServlet {
    private List<Trip> myTrips = new ArrayList<Trip>();
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        Query q = new Query("trip");
        Query.Filter onlyMine = new Query.FilterPredicate("user",
                Query.FilterOperator.EQUAL, email);


        PreparedQuery pq = datastore.prepare(q.setFilter(onlyMine));
        for(Entity aTrip: pq.asIterable()){
            myTrips.add(new Trip("You",
                    (String)aTrip.getProperty("eta"),
                    (String)aTrip.getProperty("lastOrder")
                    ,(String)aTrip.getProperty("restaurant"),
                    (String)aTrip.getProperty("flatFee"),
                    (String)aTrip.getProperty("percentFee"),
                    (String)aTrip.getProperty("maxOrder"),
                    "Your number",
                    (String)aTrip.getProperty("dropOffLocation"),
                    (String)aTrip.getKey().getName()
                    ));
            
        }

    }





}
