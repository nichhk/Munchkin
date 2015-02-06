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

public class CreateProfileServlet extends HttpServlet {

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    /* Called by a user to create a new profile on Munchin.com. Requires the user to have an @rice.edu email
    and that they have not previously made an account. Redirects them to CreateProfile.jsp
     */

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{ // Redirects them to the makeProfile.jsp

        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
        if(checkValidEmail(email)) { // Check if the email is a @rice.edu
            if (checkUniqueEmail(email)) { // Checks if the email has already been used
                try {
                    req.getRequestDispatcher("CreateProfile.jsp").forward(req, resp); //
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                req.setAttribute("Error", "You already have an account at" + email); // Writes the error message and redirects to the page
            }
        }
        else{
            req.setAttribute("Error","Please enter a valid @rice.edu email");
        }
        try {
            resp.sendRedirect("/");
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /* Takes an inputted form and creates a user account on Munchin.com
     */

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        placeDatastore(req); // Places
        try {
            resp.sendRedirect("/"); // Redirects to home page
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /* Places a user account in Google Datastore
     */
    private boolean placeDatastore(HttpServletRequest req){
        UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth
        User user = userService.getCurrentUser();
        String email = user.getEmail();
                Entity profile = new Entity("profile", email); // The key of each account is the users Rice email
                profile.setProperty("firstName", req.getParameter("firstName"));
                profile.setProperty("lastName", req.getParameter("lastName"));
                profile.setProperty("college", req.getParameter("college")); // College within Rice
                profile.setProperty("year", req.getParameter("year")); // Graduation year in college
                String phoneNumber = req.getParameter("phoneNumber").replaceAll("[^\\d.]", "");
                profile.setProperty("phoneNumber", phoneNumber);
                profile.setProperty("email",email);
                profile.setProperty("rating", 0); // Initial reviews start out as 0
                profile.setProperty("numReviews", 0);
                profile.setProperty("numTrips", 0); // Used to calculate the updated average ratings
                profile.setProperty("textNotification",true); // Whether the user wants to receive notifications from texts/email
                profile.setProperty("emailNotification",true);
                datastore.put(profile);
                new ConfirmationEmail().sendWelcomeEmail(req.getParameter("firstName"), email); // Sends a welcome email to the new user
                req.setAttribute("Error",null); // No error was raised in this process
        return true;
    }

    /* Checks if the email is a valid rice email
     */

    private boolean checkValidEmail(String email) {
        String[] splitEmail = email.split("@"); // Checks the ending of the email
        try {
            if (splitEmail[1].equals("rice.edu")) {
                return true;
            } else {
                return false;
            }
        }catch(Exception e){
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
