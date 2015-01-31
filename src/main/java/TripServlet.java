

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

        trip.setProperty("user", req.getParameter(""));
        trip.setProperty("eta", req.getParameter("")); // Estimated time of arrival
        trip.setProperty("etd", req.getParameter("")); // Estimated time of delivery
        trip.setProperty("restaurantTime", req.getParameter(""));
        trip.setProperty("restaurant", req.getParameter(""));
        trip.setProperty("flat",findFee(req));
        trip.setProperty("percentage",req.getParameter(""));
        trip.setProperty("maxOrder",req.getParameter(""));
        trip.setProperty("acceptUntil",req.getParameter());


    }
    private double findFee(HttpServletRequest req){
        if(req.getParameter("flat")){

        }
        else if(req.getParameter("perc")){

        }

    }

}

