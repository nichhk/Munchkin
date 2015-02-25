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

    private QueryManager queryManager = new QueryManager();

    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "order_manager");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        List<Order> myOrders = new ArrayList<>();
        List<Trip> myTrips = new ArrayList<>();
        String email = LoginStatus.getUserEmail();
        List<Entity> orders = queryManager.query("order","email",email,1000,Query.FilterOperator.EQUAL);
        for(Entity order:orders){
            System.out.println("printing another");
            myOrders.add(new Order(order));
            myTrips.add(new Trip(queryManager.getParent(order.getParent())));
        }

        req.setAttribute("orders", new Gson().toJson(myOrders));
        req.setAttribute("trips", new Gson().toJson(myTrips));
        System.out.println(req.getAttribute("orders"));
        System.out.println(req.getAttribute("trips"));
        try {
            req.getRequestDispatcher("ManageOrder.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}

    }
}
