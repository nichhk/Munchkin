

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/**
 * Created by compsci on 1/31/15.
 */
public class CreateProfileServlet extends HttpServlet {

    private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //String[] status = CheckLoginStatus.getStatus().split(" ");

        try {
            //req.setAttribute("isApproved", status[0]);
            //req.setAttribute("log", status[1]);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        placeDataStore(req); // Adds their data to the DataStore

        resp.sendRedirect("/"); // Sends them to the home page, no matter what

    }
    private boolean placeDataStore(HttpServletRequest req){

        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        if(checkValidEmail(email)) {
            Query q = new Query("profile");
            Filter uniqueEmail = new FilterPredicate(Entity.KEY_RESERVED_PROPERTY, FilterOperator.EQUAL, email);

            boolean isUnique = true; // Checks it the email has already been placed in the dataStore

            PreparedQuery pq = datastore.prepare(q.setFilter(uniqueEmail));
            for (Entity e : pq.asIterable()) {
                isUnique = false;
            }

            if (isUnique) {  // Then the user's email has not been used yet
                Entity profile = new Entity("profile", email);
                profile.setProperty("firstName", req.getParameter("firstName"));
                profile.setProperty("lastName", req.getParameter("lastName"));
                profile.setProperty("college", req.getParameter("college"));
                profile.setProperty("year", req.getParameter("year"));
                profile.setProperty("phoneNumber", req.getParameter("phoneNumber"));
                profile.setProperty("rating", -1); // Default rating is -1
                profile.setProperty("numTrips", 0); // If numTrips == 0, then we are fine
                datastore.put(profile);
                new ConfirmationEmail().sendWelcomeEmail(req.getParameter("firstName"), email);
                req.setAttribute("Error",null);
                return true;
            } else {
                req.setAttribute("Error", "You have already created an account");
                return false;
            }
        }
        else{
            req.setAttribute("Error","Please input a valid @rice.edu email");
            return false;
        }
    }
    private boolean checkValidEmail(String email) {
        String[] splitEmail = email.split("@");
        try {
            if (splitEmail[1].equals("rice.edu")) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){ // If an error occurs, return false
            return false;
        }
    }
}
