import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by compsci on 2/5/15.
 */
public class DeleteServlet  extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        String deleteType = req.getParameter("type");
        Key key = KeyFactory.stringToKey(req.getParameter("key"));
        SafeDelete safeDelete = new SafeDelete();
        if(deleteType.equals("order")){
            safeDelete.deleteOrder(key);
        }
        if(deleteType.equals("trip")){
            safeDelete.deleteTrip(key);
        }
        if(deleteType.equals("profile")){
            safeDelete.deleteProfile(key);
        }

    }
}
