        import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
        import com.google.appengine.api.blobstore.BlobstoreService;
        import com.google.appengine.api.users.UserService;
        import com.google.appengine.api.users.UserServiceFactory;
        import com.google.appengine.api.users.User;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
        import java.lang.Override;
        import java.util.Properties;
        import javax.mail.Message;
        import javax.mail.MessagingException;
        import javax.mail.Session;
        import javax.mail.Transport;
        import javax.mail.internet.AddressException;
        import javax.mail.internet.InternetAddress;
        import javax.mail.internet.MimeMessage;


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
            msg.setSubject("Hello" + name + "," + "Welcome to Munchin!");
            //System.out.println("Reply to: " + email + "\n" +msgBody);
            msg.setText("Hello "+name+", \n" + msgBody);
            Transport.send(msg);
       }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
