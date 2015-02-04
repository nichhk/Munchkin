import com.google.appengine.api.datastore.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * Created by compsci on 1/31/15.
 */
public class TransactionServlet extends HttpServlet {
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private static final String ACCOUNT_SID = "AC17efb2344a3923029e884cdc1f8d674d";
    private static final String AUTH_TOKEN = "f4a40d8a8a872611d1cd106b4a91a7ec";

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Query q1 = new Query("profile");
        //System.out.println("THE ID IN THIS IS"+id);
        Query.FilterPredicate q2 = new Query.FilterPredicate("phoneNumber",
                Query.FilterOperator.EQUAL, req.getParameter("phoneNumber"));

        PreparedQuery pq1 = datastore.prepare(q1.setFilter(q2));
        String email = "";
        for(Entity caller:pq1.asIterable()){
            email = (String)caller.getProperty("email");
        }



        double totalAmount = Double.parseDouble(req.getParameter("totalAmount"));
        long orderNumber = Long.parseLong(req.getParameter("orderNumber"));

        Query q = new Query("order");
        Query.FilterPredicate orderNum = new Query.FilterPredicate("trip",
                Query.FilterOperator.EQUAL, orderNumber);
        Query.FilterPredicate userNum = new Query.FilterPredicate("email",
                Query.FilterOperator.EQUAL, email);
        PreparedQuery pq = datastore.prepare(q.setFilter(orderNum).setFilter(userNum));
        Entity completeOrder = null;
        for(Entity order:pq.asIterable()){
            completeOrder=order;
        }
        double toCustomer = Double.parseDouble((String)completeOrder.getProperty("depositAmt")) - totalAmount;
        try{
            req.getRequestDispatcher("GetPaid.jsp").forward(req, resp);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}