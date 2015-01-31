import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
/**
 * Created by compsci on 1/31/15.
 */
public class HomeServlet extends HttpServlet {
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

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
            req.setAttribute("isApproved", "1");
            req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
            Query q = new Query("trip");
            long curTime = System.currentTimeMillis();
            Query.FilterPredicate timeLeft = new Query.FilterPredicate("time", Query.FilterOperator.GREATER_THAN, curTime);
            PreparedQuery pq = datastore.prepare(q.setFilter(timeLeft));
            ArrayList<Trip> trips = new ArrayList<Trip>();
            for (Entity trip : pq.asIterable()){
                trips.add(new Trip((String)trip.getProperty("user"), (String)trip.getProperty("eta"),
                        (String)trip.getProperty("etd"), (String)trip.getProperty("restaurantTime"),
                        (String)trip.getProperty("restaurant"), (double)trip.getProperty("flat"),
                        (double)trip.getProperty("percentage"), (int)trip.getProperty("maxOrder"),
                        (String)trip.getProperty("acceptUntil")));
            }
            req.setAttribute("trips", new Gson().toJson(trips));
            try{
                req.getRequestDispatcher("Home1.jsp").forward(req, resp);
            } catch (Exception e) {e.printStackTrace();}
        }
    }
}
