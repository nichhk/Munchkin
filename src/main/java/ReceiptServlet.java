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
        String email = (String)req.getAttribute("email");
        String trip = (String)req.getParameter("tripId");
        Query q = new Query("trip")
                ;
        Query q1 = new Query("profile");
        Query.FilterPredicate emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Query.FilterPredicate tripFilter = new Query.FilterPredicate("time", Query.FilterOperator.EQUAL, Long.parseLong(trip));
        Order order = null;
        PreparedQuery pq = datastore.prepare(q.setFilter(tripFilter));
        Entity thisTrip = null;
        for(Entity trip1:pq.asIterable()){
            thisTrip=trip1;
        }
        req.setAttribute("flatFee", thisTrip.getProperty("flatFee") );
        req.setAttribute("percentFee",thisTrip.getProperty("percentFee"));
        try {


            PreparedQuery pq1 = datastore.prepare(q.setFilter(emailFilter).setFilter(tripFilter));
            for (Entity anOrder : pq1.asIterable()){
                order = new Order(anOrder);
            }
            req.setAttribute("responseJson", new Gson().toJson(order));
            System.out.println(new Gson().toJson(order));
            req.getRequestDispatcher("ViewReceipt.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
