import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class LoginStatus{
    static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    static UserService userService = UserServiceFactory.getUserService(); // Finds the user's email from OAuth

    public static boolean getStatus() {
        User user = userService.getCurrentUser();
        if (user == null){
            return false;
        }
        String email = user.getEmail();

        Query q = new Query("profile");
        Query.Filter uniqueEmail = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY, Query.FilterOperator.EQUAL,email);

        boolean isUser = false; // Checks it the email has already been placed in the dataStore

        PreparedQuery pq = datastore.prepare(q.setFilter(uniqueEmail));
        for(Entity e: pq.asIterable()){
            isUser = true;
        }
        return isUser;
    }

    public static String getLogOutUrl(String path){
        return userService.createLogoutURL(path);
    }

    public static String getLogInUrl(String path){
        return userService.createLoginURL(path);
    }
}
