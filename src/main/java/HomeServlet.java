
import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.gson.Gson;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.ArrayList;

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
            long curTime = System.currentTimeMillis();
            //Query.FilterPredicate timeLeft = new Query.FilterPredicate("lastOrder", Query.FilterOperator.GREATER_THAN, curTime);
            //PreparedQuery pq = datastore.prepare(q.setFilter(timeLeft));
            PreparedQuery pq = datastore.prepare(q);
            ArrayList<Trip> trips = new ArrayList<Trip>();
            System.out.println("About to look at pq");
            for (Entity trip : pq.asIterable()){
                System.out.println("At least one");
                trips.add(new Trip(new User((String)trip.getProperty("user")).getName(),
                        (String)trip.getProperty("eta"),
                        (String)trip.getProperty("lastOrder")
                        ,(String)trip.getProperty("restaurant"),
                        (String)trip.getProperty("flatFee"),
                        (String)trip.getProperty("percentFee"),
                        (String)trip.getProperty("maxOrder"),
                        new User((String)trip.getProperty("user")).number,
                        (String)trip.getProperty("dropOffLocation"),
                        (String)trip.getKey().getName()));
            }
            System.out.println("Done");
            req.setAttribute("trips", new Gson().toJson(trips));
            try{
                req.getRequestDispatcher("Home1.jsp").forward(req, resp);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
