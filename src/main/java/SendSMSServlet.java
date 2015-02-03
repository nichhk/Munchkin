import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        System.out.println("good job joseph");
        System.out.println("Id is"+request.getParameter("id"));
        String id = (String)request.getParameter("id");    // STU NEEDS TO SEND IN TRIP Id
        List<Entity> myOrders = queryManager.query("order","trip",(String)request.getParameter("id"),10000,Query.FilterOperator.EQUAL);
        ArrayList<String> phoneNumbers = new ArrayList<String>();
        for(Entity order:myOrders){
            try {
                Entity person = datastore.get(KeyFactory.createKey("profile", (String) order.getProperty("email")));
                phoneNumbers.add((String)person.getProperty("phoneNumber"));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
        Account account = client.getAccount();
        Entity trip = queryManager.queryByKey("trip",KeyFactory.stringToKey((String)request.getParameter("id")),1).get(0);
        String resName = (String)trip.getProperty("restaurant");
        String shortCode = id.substring(id.length()-5,id.length()-1);
        MessageFactory messageFactory = account.getMessageFactory();
        for(String number:phoneNumbers){
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To",number));
            params.add(new BasicNameValuePair("From", "+18184854392"));
            params.add(new BasicNameValuePair("Body", "Hello, your delivery from "+ resName +
                    " is coming soon. If you wish to cancel your delivery, please respond with "+shortCode+" within 10 minutes"));
            try {
                Message sms = messageFactory.create(params);
                System.out.println(sms.getSid());
                System.out.println("message sent");
            }catch(Exception e){
                e.printStackTrace();
            }
            response.sendRedirect("/");
        }
    }
}






