import com.google.appengine.api.datastore.*;

/* Safely deletes an Entity from datastore and all its dependencies. Sends a follow up text to all associated users
 */

public class SafeDelete {
    private QueryManager queryManager = new QueryManager();
    private static DatastoreService dataStore= DatastoreServiceFactory.getDatastoreService();
    public boolean deleteTrip(){
        return true;
    }
    /* Deletes an order from the datastore and sends a confirmation to the trip owner
     */
    public boolean deleteOrder(Key key){ // Must be the key of an order
        try {
            Entity order= dataStore.get(key);
            dataStore.get(order.getParent()).getProperty("user");


            return true;
        }catch (Exception e){
            return false;
        }
    }
    public boolean deleteOrder(Entity order){
        return true;
    }


    /* Safe deletes a given profile, then deletes all the trips and orders associated with this particular user. Finally
    it deletes the orders associated with its trip.
     */

    public boolean deleteProfile(){
        return true;
    }


}
