import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

public class QueryManager {

    private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

    /* Used to query the datastore for entities by their property values
     */

    public List<Entity> query(String type, String property, String value, int num, Query.FilterOperator op){
        Query q = new Query(type);
        Query.FilterPredicate filter = new Query.FilterPredicate(property,op, value);
        PreparedQuery pq = dataStore.prepare(q.setFilter(filter));
        try {
            return pq.asList(withLimit(num)); // Returns at max "num" results from datastore
        }catch (Exception e){
            try{ // If the original query returned no results, attempt to query for a Long version of the value
                return query(type,property,Long.parseLong(value), num, op);
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return new ArrayList<Entity>(); // Returns an empty list if all else fails
    }

    /* Attempts to query the datastore for entities by properties using a long input value
     */

    public List<Entity> query(String type, String property, Long value, int num, Query.FilterOperator op){

        Query q = new Query(type);
        Query.FilterPredicate filter = new Query.FilterPredicate(property,
                op, value);
        PreparedQuery pq = dataStore.prepare(q.setFilter(filter));
        return pq.asList(withLimit(num));
    }
    public Entity getParent(Key key){
        try{
            return dataStore.get(key);
        }catch (Exception e){
            return null;
        }
    }
    public List<Entity> getChildren(Key myKey, String childType){
        try {
            Entity me = dataStore.get(myKey);
            Query q = new Query(childType).setAncestor(myKey);
            PreparedQuery pq = dataStore.prepare(q);
            long maxOrder = (long)me.getProperty("maxOrder");
            return pq.asList(withLimit((int)maxOrder));
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public Entity queryByKey(Key key){
        try{
            return dataStore.get(key);
        }catch (Exception e){
            return null;
        }
    }
}
