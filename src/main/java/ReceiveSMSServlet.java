import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
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
        String from = req.getParameter("From");
        boolean confirmed = false;

        if(confirmation.length()!=0){

            confirmed = confirmation.charAt(0)==1?true:false;
        }

        Entity responseText = new Entity("Text",key); // Uses timeStamp as key

        responseText.setProperty("IncomingNumber", texter);

        responseText.setProperty("Reply", confirmed);

        datastore.put(responseText);

        System.out.println("successfully got message");

        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", from)); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "+18184854392")); // Replace with a valid phone number for your account.
        if(confirmed){
            params.add(new BasicNameValuePair("Body", "Thank you, your driver is on his way"));
        }
        else{
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
