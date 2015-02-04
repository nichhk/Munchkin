import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


/**
 * Created by compsci on 1/31/15.
 */
public class TripServlet extends HttpServlet {

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /* Redirects users to the makeTrip page
     */

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        req.setAttribute("page", "trip");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogInUrl("/create_profile"));
        try {
            req.getRequestDispatcher("MakeTrip.jsp").forward(req, resp);
        } catch (Exception e) { e.printStackTrace(); }
    }

    /* Stores a trip in the datastore
     */

    public void doPost(HttpServletRequest req, HttpServletResponse resp){

        long currentTime = new Date().getTime();
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        Entity trip = new Entity("trip"); // Trips are Id'd by a random key
        trip.setProperty("user", email);
        trip.setProperty("time", currentTime); // Time the trip was made
        trip.setProperty("dropOffLocation",req.getParameter("dropOffLocation"));
        System.out.println("maxOrder is"+req.getParameter("maxOrder"));
        System.out.println("restaurant is"+req.getParameter("restaurant"));
        trip.setProperty("restaurant", req.getParameter("restaurant"));
        trip.setProperty("maxOrder", Long.parseLong(req.getParameter("maxOrder")));
        findFee(trip, req);
        trip.setProperty("eta",getMilliTime(req,"eta"));
        trip.setProperty("lastOrder",getMilliTime(req,"lastOrder"));
        datastore.put(trip);
        try {
            resp.sendRedirect("/");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void findFee(Entity trip, HttpServletRequest req){
        if(req.getParameter("flat")==null || req.getParameter("flatFee")==null){
           trip.setProperty("flatFee", "0");
        }
        else{
            trip.setProperty("flatFee",req.getParameter("flatFee"));
        }
        if((req.getParameter("percentage")==null)||req.getParameter("percentFee")==null){
            trip.setProperty("percentFee","0");
        }
        else{
            trip.setProperty("percentFee",req.getParameter("percentFee"));
        }
    }
    private Long getMilliTime(HttpServletRequest req, String property){
        String dateTerm = property +"Date";
        System.out.println("Printing date"+req.getParameter(dateTerm));
        String[] splitDate = req.getParameter(dateTerm).split("-");
        int[] timeElements = new int[5];
        for(int i =0;i<3;i++){
            timeElements[i] = Integer.parseInt(splitDate[i]);
        }
        String timeTerm = property + "Time";
        String[] splitTime = req.getParameter(timeTerm).split(":");
        timeElements[3]=Integer.parseInt(splitTime[0]);
        timeElements[4]=Integer.parseInt(splitTime[1]);

        System.out.println(new DateToMilliseconds().timeToDate(new DateToMilliseconds().dateToTime(timeElements)));
        return new DateToMilliseconds().dateToTime(timeElements);
    }

}

