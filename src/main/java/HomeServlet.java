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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "null");

        if (!LoginStatus.getStatus()){
            req.setAttribute("isApproved", "0");
            req.setAttribute("log", LoginStatus.getLogInUrl("/create_profile"));
            try {
                System.out.println("going to home2.jsp");
                req.setAttribute("logIn", LoginStatus.getLogInUrl("/"));
                req.setAttribute("createProf", LoginStatus.getLogInUrl("/create_profile"));
                req.getRequestDispatcher("Home2.jsp").forward(req, resp);
            } catch(Exception e) {e.printStackTrace();}
        }
        else{
            UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
            com.google.appengine.api.users.User user = userService.getCurrentUser();
            String email = user.getEmail();
            req.setAttribute("isApproved", "1");
            req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
            long curTime = new Date().getTime();

            List<Entity> queriedTrips = queryManager.query("trip","lastOrder", curTime-21600000, 1000,Query.FilterOperator.GREATER_THAN);
            ArrayList<Trip> trips = new ArrayList<Trip>();
            HashSet<Key> unique = new HashSet<Key>();
            List<Entity> myOrders = queryManager.query("order", "email", email ,1000, Query.FilterOperator.EQUAL);
            System.out.println("these must be ignored"+myOrders.size());
            for(Entity order:myOrders){
                unique.add(order.getParent());
            }
            for (Entity trip : queriedTrips){
                if(!unique.contains(trip.getKey())) {
                    trips.add(new Trip(trip));
                }
            }
            req.setAttribute("trips", new Gson().toJson(trips));
            try{
                req.getRequestDispatcher("Home1.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
