import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by compsci on 1/31/15.
 */
public class SendSMSServlet extends HttpServlet {

    private static final String ACCOUNT_SID = "AC17efb2344a3923029e884cdc1f8d674d";
    private static final String AUTH_TOKEN = "f4a40d8a8a872611d1cd106b4a91a7ec";
    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    private QueryManager queryManager = new QueryManager();

    /* Sends out a text message to all customers associated with a trip
     */

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String id = (String)request.getParameter("id"); // The trip key
        List<Entity> myOrders = queryManager.query("order","trip",(String)request.getParameter("id"),10000,Query.FilterOperator.EQUAL);
        // Finds the orders associated with the trip
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        // List of the phone numbers to text
        /*
        if(request.getParameter("textType").equals("tripCancel")){

        }
        else if(request.getParameter("textType").equals("orderCancel")){

        }
        else if(request.getParameter("textType").equals("confirmation")){

        }
        else if(request.getParameter("textType").equals("pickup")){

        }
        */



        for(Entity order:myOrders){
            try {
                Entity person = datastore.get(KeyFactory.createKey("profile", (String) order.getProperty("email")));
                // Finds the users associated with each user and their phone numbers
                phoneNumbers.add((String)person.getProperty("phoneNumber"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account account = client.getAccount();
        Entity trip = null;
        try {
             trip = datastore.get(KeyFactory.stringToKey((String) request.getParameter("id"))); // Finds the trip referred to by the id
        }catch (Exception e){
            e.printStackTrace();
        }
        String resName = (String)trip.getProperty("restaurant"); // Name of the restaurant associated with the trip
        String shortCode = id.substring(id.length()-5,id.length()-1); // Last 4 chars of the trip key
        MessageFactory messageFactory = account.getMessageFactory();
        for(String number:phoneNumbers){ // Sends to each of the phone numbers
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To",number));
            params.add(new BasicNameValuePair("From", "+18184854392"));
            params.add(new BasicNameValuePair("Body", "Hello, your delivery from "+ resName +
                    " is coming soon. If you wish to cancel your delivery, please respond with "+shortCode+" within 10 minutes"));
            try {
                Message sms = messageFactory.create(params);
                System.out.println(sms.getSid());
            }catch(Exception e){
                e.printStackTrace();
            }
            response.sendRedirect("ViewMyCurrentTrips.jsp"); // Redirects to the viewMyCurrentTrips page
        }
    }
}






