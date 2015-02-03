import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by compsci on 2/1/15.
 */
public class ManageOrdersServlet extends HttpServlet {
    private List<Order> myOrders = new ArrayList<Order>();
    private List<Trip> myTrips = new ArrayList<Trip>();
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "order_manager");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        String getFor = (String)req.getAttribute("for");
        myOrders = new ArrayList<>();
        myTrips = new ArrayList<>();
        String email = LoginStatus.getUserEmail();

        Query q = new Query("order");

        Query.Filter onlyMine = new Query.FilterPredicate("email",
                Query.FilterOperator.EQUAL, email);
        PreparedQuery pq = datastore.prepare(q.setFilter(onlyMine));
        for(Entity order:pq.asIterable()){
            System.out.println("cooking order");
            myOrders.add(new Order(order));
            try {
                Query q1 = new Query("trip");
                Query.Filter myTrip = new Query.FilterPredicate("time",
                        Query.FilterOperator.EQUAL, order.getProperty("tripId"));
                PreparedQuery pq1 = datastore.prepare(q1.setFilter(onlyMine));
                for(Entity trip:pq1.asIterable()){
                    myTrips.add(new Trip(trip));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("IN MANAGE Order");
        req.setAttribute("responseJson", new Gson().toJson(myOrders));
        System.out.println(new Gson().toJson(myOrders));
        try {
            req.getRequestDispatcher("ManageOrder.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}







    }
}
