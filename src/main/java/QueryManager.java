import com.google.appengine.api.datastore.*;

import java.util.ArrayList;
import java.util.List;

import static com.google.appengine.api.datastore.FetchOptions.Builder.*;

public class QueryManager {

    private static DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();

    public List<Entity> query(String type, String property, String value, int num, Query.FilterOperator op){
        Query q = new Query(type);
        Query.FilterPredicate filter = new Query.FilterPredicate(property,
                op, value);
        PreparedQuery pq = dataStore.prepare(q.setFilter(filter));
        try {
            return pq.asList(withLimit(num));
        }catch (Exception e){
            try{
                return query(type,property,Long.parseLong(value), num, op);
            }
            catch (Exception e1){
                e1.printStackTrace();
            }
        }
        return new ArrayList<Entity>();
    }
    public List<Entity> query(String type, String property, Long value, int num, Query.FilterOperator op){
        Query q = new Query(type);
        Query.FilterPredicate filter = new Query.FilterPredicate(property,
                op, value);
        PreparedQuery pq = dataStore.prepare(q.setFilter(filter));
        return pq.asList(withLimit(num));

    }
    public List<Entity> queryByKey(String type, Key value, int limit){

        Query q = new Query(type);
        Query.Filter filter = new Query.FilterPredicate(Entity.KEY_RESERVED_PROPERTY,Query.FilterOperator.EQUAL,value);
        PreparedQuery pq = dataStore.prepare(q.setFilter(filter));
        return pq.asList(withLimit(limit));



    }
}
