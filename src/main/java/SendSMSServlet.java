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
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        long id = Long.parseLong(request.getParameter("id"));    // STU NEEDS TO SEND IN TRIP ID
        Query q = new Query("order");
        //System.out.println("THE ID IN THIS IS"+id);
        Query.FilterPredicate myOrder = new Query.FilterPredicate("trip",
                Query.FilterOperator.EQUAL, Long.toString(id) );
        PreparedQuery pq = datastore.prepare(q.setFilter(myOrder));

        ArrayList<String> phoneNumbers = new ArrayList<String>();
        for(Entity order:pq.asIterable()){
            try {
                //System.out.println("Is IS IS"+(String) order.getProperty("email"));
                Entity person = datastore.get(KeyFactory.createKey("profile", (String) order.getProperty("email")));
                System.out.println("There id is"+order.getProperty("trip"));
                System.out.println(person.getProperty("email"));
                System.out.println(person.getProperty("firstName"));
                phoneNumbers.add((String)person.getProperty("phoneNumber"));
            }catch (Exception e){
                e.printStackTrace();
            }


        }

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();

        Query q1 = new Query("trip");
        //System.out.println("THE ID IN THIS IS"+id);
        Query.FilterPredicate q2 = new Query.FilterPredicate("time",
                Query.FilterOperator.EQUAL, id );
        PreparedQuery pq1 = datastore.prepare(q1.setFilter(q2));
        String resName ="";
        for(Entity trip:pq1.asIterable()){
            System.out.println(trip.getProperty("restaurant"));
            resName = (String)trip.getProperty("restaurant");
        }
        String tripId=request.getParameter("id");
        String shortCode = tripId.substring(tripId.length()-5,tripId.length()-1);

        MessageFactory messageFactory = account.getMessageFactory();
        for(String number:phoneNumbers){
            System.out.println("number is "+number);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("To",number));
            params.add(new BasicNameValuePair("From", "+18184854392"));
            params.add(new BasicNameValuePair("Body", "Hello, your delivery from "+ resName +
                    " is coming soon. If you wish to cancel your delivery, please respond with  "+shortCode+" within 10 minutes"));
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






