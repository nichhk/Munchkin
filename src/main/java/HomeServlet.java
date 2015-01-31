
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by compsci on 1/31/15.
 */
public class HomeServlet extends HttpServlet {


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp){
        req.setAttribute("page", "null");
        if (!LoginStatus.getStatus()){
            req.setAttribute("isApproved", "0");
            req.setAttribute("log", LoginStatus.getLogInUrl("/create_profile"));
            try {
                System.out.println("going to home2.jsp");
                req.setAttribute("logIn", LoginStatus.getLogInUrl("/"));
                req.setAttribute("createProf", LoginStatus.getLogInUrl("/create_profile"));
                req.getRequestDispatcher("Home2.jsp").forward(req, resp);
            } catch(Exception e) {e.printStackTrace();}
        }
        else{
            req.setAttribute("isApproved", "1");
            req.setAttribute("log", LoginStatus.getLogOutUrl("/"));
            try{
                req.getRequestDispatcher("home1.jsp").forward(req, resp);
            } catch (Exception e) {e.printStackTrace();}
        }
    }

}
