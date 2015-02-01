import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by compsci on 1/31/15.
 */
public class HomeServlet extends HttpServlet {

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "null");
        System.out.println("GOING HOME");
        if (!LoginStatus.getStatus()){
            System.out.println("I dont belong");
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
            System.out.println("Printing trips");
            req.setAttribute("isApproved", "1");
            req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
            Query q = new Query("trip");
            long curTime = new Date().getTime();
            Query.FilterPredicate timeLeft = new Query.FilterPredicate("lastOrder",
                    Query.FilterOperator.GREATER_THAN, curTime-21600000);
            PreparedQuery pq = datastore.prepare(q.setFilter(timeLeft));
            ArrayList<Trip> trips = new ArrayList<Trip>();
            System.out.println("About to look at pq");
            int count = 0;
            for (Entity trip : pq.asIterable()){
                System.out.println("At least one");
                trips.add(new Trip(trip));
                trips.get(count).addRating(Integer.parseInt(new User((String)trip.getProperty("user")).rating));
                count++;
            }

            System.out.println("Done");
            req.setAttribute("trips", new Gson().toJson(trips));
            try{
                req.getRequestDispatcher("Home1.jsp").forward(req, resp);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
