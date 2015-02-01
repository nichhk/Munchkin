import com.google.appengine.api.datastore.*;

/**
 * Created by compsci on 1/31/15.
 */
public class User {
    String email;
    String firstName;
    String lastName;
    String number;
    String rating;
    static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public User(String email){
        Query q = new Query("profile");
        Query.Filter uniqueEmail = new Query.FilterPredicate("email",
                Query.FilterOperator.EQUAL, email);
        PreparedQuery pq = datastore.prepare(q.setFilter(uniqueEmail));
        Entity user = new Entity("profile","null");
        for(Entity e: pq.asIterable()){
            user = e;
        }
        this.firstName = (String)user.getProperty("firstName");
        this.lastName = (String)user.getProperty("lastName");
        this.number = (String)user.getProperty("phoneNumber");
        this.rating = Long.toString((long)user.getProperty("rating"));

    }
    public User(String email, String firstName, String lastName, String number){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.number = number;
    }
    public String getName(){
        return firstName + " " +lastName;
    }

}
