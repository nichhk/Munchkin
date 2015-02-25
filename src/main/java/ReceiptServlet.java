import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.google.appengine.api.datastore.FetchOptions.Builder.withLimit;

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
        PreparedQuery pq = datastore.prepare(q.setFilter(emailFilter));
        Order order = new Order(pq.asList(withLimit(1)).get(0));
        Entity thisTrip = null;
        try{
            thisTrip = datastore.get(KeyFactory.stringToKey(trip));
        }
        catch (Exception e) { e.printStackTrace(); }
        System.out.println("flatfee is "+thisTrip.getProperty("flatFee") );
        req.setAttribute("flatFee", thisTrip.getProperty("flatFee") );
        req.setAttribute("percentFee",thisTrip.getProperty("percentFee"));
        System.out.println("EMAIL IS "+email);
        System.out.println("Trip is"+trip);
        try {
            req.setAttribute("items", new Gson().toJson(order));
            req.getRequestDispatcher("ViewReceipt.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}
