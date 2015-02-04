import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by nich on 2/1/15.
 */
public class ReceiptServlet extends HttpServlet {
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        String email = (String)req.getParameter("email");
        String trip = (String)req.getParameter("tripId");
        Query q = new Query("order").setAncestor(KeyFactory.stringToKey(trip));
        Query.FilterPredicate emailFilter = new Query.FilterPredicate("email", Query.FilterOperator.EQUAL, email);
        Order order = null;
        PreparedQuery pq = datastore.prepare(q.setFilter(emailFilter));
        Entity thisTrip = null;
        for(Entity trip1 : pq.asIterable()){
            thisTrip = trip1;
        }
        req.setAttribute("flatFee", thisTrip.getProperty("flatFee") );
        req.setAttribute("percentFee",thisTrip.getProperty("percentFee"));
        System.out.println("EMAIL IS "+email);
        System.out.println("Trip is"+trip);
        try {
            req.setAttribute("responseJson", new Gson().toJson(order));
            req.getRequestDispatcher("ViewReceipt.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
