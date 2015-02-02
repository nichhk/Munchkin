import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.appengine.api.datastore.*;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by compsci on 1/31/15.
 */
public class ReceiveSMSServlet extends HttpServlet {

    private static final String ACCOUNT_SID = "AC17efb2344a3923029e884cdc1f8d674d";
    private static final String AUTH_TOKEN = "f4a40d8a8a872611d1cd106b4a91a7ec";


    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String texter = req.getParameter("From");

        String key = Long.toString(System.currentTimeMillis()); // Sets the TimeStamp as the key
        String confirmation = (String)req.getParameter("body");

        req.getParameter("From");

        Query q1 = new Query("profile");
        //System.out.println("THE ID IN THIS IS"+id);
        Query.FilterPredicate q2 = new Query.FilterPredicate("phoneNumber",
                Query.FilterOperator.EQUAL, req.getParameter("From"));

        PreparedQuery pq1 = datastore.prepare(q1.setFilter(q2));
        String email = "";
        for(Entity caller:pq1.asIterable()){
            email = (String)caller.getProperty("email");
        }
        Query q3 = new Query("order");
        //System.out.println("THE ID IN THIS IS"+id);
        Query.FilterPredicate q4 = new Query.FilterPredicate("email",
                Query.FilterOperator.EQUAL, email);

        PreparedQuery pq2 = datastore.prepare(q1.setFilter(q2));
        boolean canceled = false;
        if(confirmation.length()>4){
            for(Entity order:pq2.asIterable()){
                String shortCode = (String)(order.getProperty("time"));
                if (confirmation.equals(shortCode.substring(shortCode.length()-5, shortCode.length()-1))) {
                    canceled = true;
                    datastore.delete(order.getKey());
                    // Send them money
                }
            }
        }


        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();






        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", req.getParameter("From"))); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "+18184854392")); // Replace with a valid phone number for your account.
                                                                    // Refund them
        if(canceled){
            params.add(new BasicNameValuePair("Body", "Thank you, your order has been canceled"));
        }
        try {
            Message sms = messageFactory.create(params);
            System.out.println(sms.getSid());
            System.out.println("message sent");

        }catch(Exception e){
            e.printStackTrace();
        }




        try {
            resp.sendRedirect("/");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
