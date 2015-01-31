

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by compsci on 1/31/15.
 */
public class TripServlet extends HttpServlet {


    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        double currentTime = System.currentTimeMillis();
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        Entity trip = new Entity("trip", Double.toString(currentTime)); // Trips are Id'd by their timeStamp
        trip.setProperty("user", email);
        trip.setProperty("eta", req.getParameter("")); // Estimated time of arrival, use LUKES
        trip.setProperty("lastOrder", req.getParameter("")); // Estimated time of delivery, use LUKES
        trip.setProperty("restaurant", req.getParameter(""));
        trip.setProperty("flatFee",findFee(req));
        trip.setProperty("percentageFee",req.getParameter(""));
        trip.setProperty("maxOrder",req.getParameter(""));
    }
    private double findFee(HttpServletRequest req){
        if()

        return 2.0;

    }

}

