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

    private QueryManager queryManager = new QueryManager();

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "order_manager");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        myOrders = new ArrayList<>();
        myTrips = new ArrayList<>();
        String email = LoginStatus.getUserEmail();
        List<Entity> orders = queryManager.query("order","email",email,1,Query.FilterOperator.EQUAL);
        ArrayList<EmbeddedEntity> items= (ArrayList<EmbeddedEntity>)(orders.get(0).getProperty("items"));


        for(Entity order:orders){
            System.out.println("cooking order");
            myOrders.add(new Order(order));
            try {
                List<Entity> trips= queryManager.query("trip","time",(String)order.getProperty("tripId"),1,Query.FilterOperator.EQUAL);
                for(Entity trip:trips){
                    myTrips.add(new Trip(trip));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        req.setAttribute("responseJson", new Gson().toJson(myOrders));
        System.out.println(new Gson().toJson(myOrders));
        try {
            req.getRequestDispatcher("ManageOrder.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}

    }
}
