import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by compsci on 1/31/15.
 */
public class RatingServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setAttribute("page", "null");
        req.setAttribute("isApproved", "1");
        req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
        req.setAttribute("userId", req.getAttribute("userId"));
        try {
            req.getRequestDispatcher("Rating.jsp").forward(req, resp);
        } catch (Exception e) { e.printStackTrace();}
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        int rating = Integer.parseInt(req.getParameter("rating"));
        String comment = req.getParameter("comment");
        String userId = req.getParameter("userId");
        Entity user;
        try {
            user = datastore.get(KeyFactory.createKey("user", userId));
            int numReviews = Integer.parseInt((String) user.getProperty("numReviews"));
            user.setProperty("rating", (rating + numReviews*Double.parseDouble((String)user.getProperty("rating")))/(numReviews + 1));
            user.setProperty("numReviews", 1 + numReviews);
        } catch (Exception e) {e.printStackTrace();}
    }
}