import com.google.appengine.api.datastore.Entity;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by compsci on 2/5/15.
 */
public class ContactUser {
    public ContactUser(){

    }
    public boolean sendSMS(String type, Entity user, Entity related){
        if(type.equals("tripCancel") ){

        }
        else if(type.equals("orderCancel")){

        }
        return true;
    }
    public boolean sendEmail(String type, Entity user, Entity related){
        return true;
    }
    private void createMessage(){
        /*
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
        */
    }


}
