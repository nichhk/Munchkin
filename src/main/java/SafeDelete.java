import com.google.appengine.api.datastore.*;

import java.util.List;

/* Safely deletes an Entity from datastore and all its dependencies. Sends a follow up text to all associated users
 */


public class SafeDelete {

    private QueryManager queryManager = new QueryManager();
    private static DatastoreService dataStore= DatastoreServiceFactory.getDatastoreService();
    private ContactUser contactUser = new ContactUser();
    private CreateNotification createNotification = new CreateNotification();
    public boolean deleteTrip(Key key){
        List<Entity> myOrders = queryManager.getChildren(key,"order");
        Entity trip = queryManager.queryByKey(key);
        for(Entity order:myOrders){
            try {
               Entity customer = dataStore.get(KeyFactory.createKey("profile", (String) order.getProperty("email")));
               contactUser("cancelTrip", customer,order, false);
               dataStore.delete(order.getKey());
                createNotification.create();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        dataStore.delete(trip.getKey());
        return true;
    }
    /* Deletes an order from the datastore and sends a confirmation to the trip owner
     */
    public boolean deleteOrder(Key key){ // Must be the key of an order
        try {
            Entity order= dataStore.get(key);
            String email = (String)dataStore.get(order.getParent()).getProperty("user"); // Returns the courier's email
            Entity courier = dataStore.get(KeyFactory.createKey("profile",email));
            contactUser("cancelOrder",courier,order,false);
            dataStore.delete(order.getKey());
            createNotification.create();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /* Safe deletes a given profile, then deletes all the trips and orders associated with this particular user. Finally
    it deletes the orders associated with its trip.
     */

    public boolean deleteProfile(){
        return true;
    }
    /* Contacts the proper user about the update. There are 3 types:
    cancelTrip
    cancelOrder
    cancelProfile

    override is used to override the emailNotification parameter for cancelProfile

     */
    private void contactUser(String type, Entity user, Entity related, boolean override){
        try {
            if ((boolean) user.getProperty("textNotification")) {
                contactUser.sendSMS(type, user, related);
            }
            if ((boolean) user.getProperty("emailNotification") || override) {
                contactUser.sendEmail(type, user, related);
            }
        }catch (Exception e){
            new CreateNotification();
        }
    }
}
