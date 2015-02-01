import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.*;
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
public class SendSMSServlet extends HttpServlet {

    private static final String ACCOUNT_SID = "AC17efb2344a3923029e884cdc1f8d674d";
    private static final String AUTH_TOKEN = "f4a40d8a8a872611d1cd106b4a91a7ec";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        com.google.appengine.api.users.User user = userService.getCurrentUser();
        String email = user.getEmail();
        request.getParameter("id");    // STU NEEDS TO SEND IN TRIP ID




        System.out.println("Trying out TWILLIO");
        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("To", customerNumber));
        //params.add(new BasicNameValuePair("From", "+18184854392"));
        //params.add(new BasicNameValuePair("Body", ));
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






