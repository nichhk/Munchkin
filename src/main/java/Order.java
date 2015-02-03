import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nich on 1/31/15.
 */
public class Order {
    List<String> primaryItems = new ArrayList<String>();
    List<String> altItems = new ArrayList<String>();
    List<String> primaryMax = new ArrayList<String>();
    List<String> altMax = new ArrayList<String>();
    List<String> primaryComments = new ArrayList<String>();
    List<String> altComments = new ArrayList<String>();;
    String name, number;

    private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    public Order(Entity order){
        Query q1 = new Query("item");
        Query.Filter thisOrder = new Query.FilterPredicate("order",Query.FilterOperator.EQUAL, order.getProperty("trip"));
        System.out.println("I am "+  order.getProperty("trip"));
        PreparedQuery pq1 = datastore.prepare(q1.setFilter(thisOrder));
        // PreparedQuery pq1 = datastore.prepare(q1);
        int count = 0;
        System.out.println("here i am");
        for(Entity item: pq1.asIterable()){
            System.out.println("yummy item" + item.getProperty("order"));
            primaryItems.add((String)item.getProperty("foodItem"));
            primaryMax.add((String)item.getProperty("priceMax"));
            primaryComments.add((String)item.getProperty("comments"));
            try{
                Entity altItem=datastore.get(item.getKey());
                altItems.add((String)altItem.getProperty("foodItem"));
                altMax.add((String)altItem.getProperty("priceMax"));
                altComments.add((String)altItem.getProperty("comments"));
            }catch (Exception e){
                altItems.add(null);
                altMax.add(null);
                altComments.add(null);
                e.printStackTrace();
            }
            count++;
            System.out.println("ordering"+item.getProperty("foodItem"));
        }
    }
    /*
    public Order(Entity order, String name, String number){
        Query q1 = new Query("item");
        order.getProperty("trip");
        Query.Filter thisOrder = new Query.FilterPredicate("order",Query.FilterOperator.EQUAL, order.getProperty("trip"));
        System.out.println("I am "+  order.getProperty("trip"));
        PreparedQuery pq1 = datastore.prepare(q1.setFilter(thisOrder));
        // PreparedQuery pq1 = datastore.prepare(q1);
        int count = 0;
        System.out.println("here i am");
        for(Entity item: pq1.asIterable()){
            System.out.println("yummy item" + item.getProperty("order"));
            primaryItems.add((String)item.getProperty("foodItem"));
            primaryMax.add((String)item.getProperty("priceMax"));
            primaryComments.add((String)item.getProperty("comments"));
            try{
                Entity altItem=datastore.get(item.getKey());
                altItems.add((String)altItem.getProperty("foodItem"));
                altMax.add((String)altItem.getProperty("priceMax"));
                altComments.add((String)altItem.getProperty("comments"));
            }catch (Exception e){
                altItems.add(null);
                altMax.add(null);
                altComments.add(null);
                e.printStackTrace();
            }
            count++;
            System.out.println("ordering"+item.getProperty("foodItem"));
        }
        this.name = name;
        this.number = number;

    }
    */

}
