import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginStatus{
    static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Finds the user's email from OAuth
    static UserService userService = UserServiceFactory.getUserService();

    public static boolean getStatus() {
        User user = userService.getCurrentUser();
        if (user == null){
            return false;
        }
        String email = user.getEmail();

        Query q = new Query("profile");
        Filter uniqueEmail = new FilterPredicate("email",
                Query.FilterOperator.EQUAL, email);

        boolean isUser = false; // Checks it the email has already been placed in the dataStore

        PreparedQuery pq = datastore.prepare(q.setFilter(uniqueEmail));
        for(Entity e: pq.asIterable()){
            isUser = true;
        }
        return isUser;
    }
    public static String getUserEmail(){
        User user = userService.getCurrentUser();
        if (user == null){
            return null;
        }
        return user.getEmail();
    }
    public static String getLogOutUrl(String path){
        return userService.createLogoutURL(path);
    }

    public static String getLogInUrl(String path){
        return userService.createLoginURL(path);
    }
}
