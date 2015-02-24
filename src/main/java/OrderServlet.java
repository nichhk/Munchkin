import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by compsci on 1/31/15.
 */
public class OrderServlet extends HttpServlet {
    private QueryManager queryManager = new QueryManager();
    private static DatastoreService dataStore= DatastoreServiceFactory.getDatastoreService();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "null");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        req.setAttribute("tripId", req.getParameter("id"));
        try {
            req.getRequestDispatcher("MakeOrder.jsp").forward(req, resp);
        } catch (Exception e) { e.printStackTrace(); }
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        int numItems = Integer.parseInt(req.getParameter("numItems"));
        System.out.println("okay, now we're tryng to make an order element");
        Entity trip = queryManager.query("trip","time",Long.parseLong((String)req.getParameter("tripId")),1, Query.FilterOperator.EQUAL).get(0);
        Entity order = new Entity("order", trip.getKey());
        order.setProperty("email", email);
        double depositAmt = 0.0;
        System.out.println("okay, now I'm adding each item");
        ArrayList<EmbeddedEntity> items = new ArrayList<EmbeddedEntity>();
        for (int i = 1; i <= numItems; i++){
            EmbeddedEntity item = new EmbeddedEntity();
            item.setProperty("foodItem", req.getParameter("foodItem" + i));
            item.setProperty("priceMax", req.getParameter("priceMax" + i));
            item.setProperty("comments", req.getParameter("comments" + i));

            if (req.getParameter("altFoodItem"+i) != null){
                System.out.println("Adding an alternate item a foodItem");
                EmbeddedEntity alt = new EmbeddedEntity();
                alt.setProperty("foodItem", req.getParameter("altFoodItem" + i));
                alt.setProperty("priceMax", req.getParameter("altPriceMax" + i));
                alt.setProperty("comments", req.getParameter("altComments" + i));
                item.setProperty("alt",alt);
            }
            items.add(item);
            System.out.println("price max is " + req.getParameter("priceMax"+i));
            System.out.println("alt price max is " + req.getParameter("altPriceMax" + i));
            if (req.getParameter("altPriceMax" + i) == null){
                depositAmt += Double.parseDouble(req.getParameter("priceMax"+i));
            }
            else {
                depositAmt += Math.max(Double.parseDouble(req.getParameter("priceMax" + i)),
                        Double.parseDouble(req.getParameter("altPriceMax" + i)));
            }

        }
        order.setProperty("items",items);
        order.setProperty("delivered",false);
        try {
            System.out.println("Okay, trying to get the trip entity for this order");
            //max tax rate for local and state sales tax is 8.25%
            System.out.println("got the trip entity");
            depositAmt *= (Double.parseDouble((String)trip.getProperty("percentFee")) + 8.25 + 100.0) / 100.0;
            depositAmt += Double.parseDouble((String)trip.getProperty("flatFee"));
        } catch (Exception e) {
            System.out.println("trying to get the trip entity failed");
            e.printStackTrace();}
        dataStore.put(order);
        req.setAttribute("depositAmt", new DecimalFormat("#.##").format(depositAmt));
        req.setAttribute("email", LoginStatus.getUserEmail());
        req.setAttribute("note", "Placing an order with Munchin");
        try {
            req.getRequestDispatcher("Deposit.jsp").forward(req, resp);
        } catch (Exception e) {e.printStackTrace();}
    }
}