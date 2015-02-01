
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
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
public class CreateProfileServlet extends HttpServlet {

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{ // Redirects them to the makeProfile.jsp
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        if(checkValidEmail(email)) {
            if (checkUniqueEmail(email)) {
                try {
                    req.getRequestDispatcher("CreateProfile.jsp").forward(req, resp);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                req.setAttribute("Error", "You already have an account at" + email);
            }
        }
        else{
            req.setAttribute("Error","Please enter a valid @rice.edu email");
        }
        try {
            resp.sendRedirect("https://api.venmo.com/v1/oauth/authorize?client_id=2331&scope=make_payments%20access_profile");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        placeDatastore(req); // Adds their data to the DataStore
        try {
            resp.sendRedirect("/");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private boolean placeDatastore(HttpServletRequest req){

        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
                Entity profile = new Entity("profile", email);
                profile.setProperty("firstName", req.getParameter("firstName"));
                profile.setProperty("lastName", req.getParameter("lastName"));
                profile.setProperty("college", req.getParameter("college"));
                profile.setProperty("year", req.getParameter("year"));
                profile.setProperty("phoneNumber", req.getParameter("phoneNumber"));
                profile.setProperty("email",email);
                profile.setProperty("rating", 0);
                profile.setProperty("numReviews", 0);
                profile.setProperty("numTrips", 0); // If numTrips == 0, then we are fine
                datastore.put(profile);
                new ConfirmationEmail().sendWelcomeEmail(req.getParameter("firstName"), email);
                req.setAttribute("Error",null);
        return true;
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
    private boolean checkUniqueEmail(String email){
        Query q = new Query("profile");
        Filter uniqueEmail = new FilterPredicate("email", FilterOperator.EQUAL, email);

        boolean isUnique = true; // Checks it the email has already been placed in the dataStore

        PreparedQuery pq = datastore.prepare(q.setFilter(uniqueEmail));
        for (Entity e : pq.asIterable()) {
            isUnique = false;
        }
        return isUnique;
    }
}
