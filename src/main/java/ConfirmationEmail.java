import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class ConfirmationEmail {
    public void sendWelcomeEmail(String name, String email){
        String msgBody = "Welcome to Munchin!";
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("Munchin.app@gmail.com", "Image Mosaic Bot"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(email, name));
            msg.setSubject("Hello " + name + "," + "Welcome to Munchin!");
            //System.out.println("Reply to: " + email + "\n" +msgBody);
            msg.setText("Hello "+name+", \n" + msgBody);
            Transport.send(msg);
       }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
