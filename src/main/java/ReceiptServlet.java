import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nich on 2/1/15.
 */
public class ReceiptServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String email = (String)req.getParameter("email");
        System.out.println("tripId is"+(String)req.getParameter("tripId"));
        String trip = (String)req.getParameter("tripId");
        Query q = new Query("trip");

        Query q1 = new Query("order");
        Query.FilterPredicate emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query.FilterPredicate tripFilter = new Query.FilterPredicate("time", Query.FilterOperator.EQUAL, Long.parseLong(trip));
        Query.FilterPredicate orderFilter = new Query.FilterPredicate("trip", Query.FilterOperator.EQUAL,trip);
        Order order = null;
        PreparedQuery pq = datastore.prepare(q.setFilter(tripFilter));
        Entity thisTrip = null;
        for(Entity trip1:pq.asIterable()){
            thisTrip=trip1;
            System.out.println("SHOUDL BE ME");
        }
        req.setAttribute("flatFee", thisTrip.getProperty("flatFee") );
        req.setAttribute("percentFee",thisTrip.getProperty("percentFee"));
        System.out.println("EMAIL IS "+email);
        System.out.println("Trip is"+trip);
        try {


            PreparedQuery pq1 = datastore.prepare(q1.setFilter(emailFilter).setFilter(orderFilter));
            for (Entity anOrder : pq1.asIterable()){

                System.out.println("SHOUDL BE ME TOOOOO!!!!!!");
            }
            req.setAttribute("number", "6616451987");
            req.setAttribute("name","Joseph Hwang" );
            req.setAttribute("responseJson", new Gson().toJson(order));
            System.out.println(new Gson().toJson(order));
            req.getRequestDispatcher("ViewReceipt.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
