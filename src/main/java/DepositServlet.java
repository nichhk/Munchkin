import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by compsci on 1/31/15.
 */
public class DepositServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        double estimatedPrice = Double.parseDouble(req.getParameter("depositAmt"));
        System.out.println("estimated price is "+ estimatedPrice);
        //String rounded =
        //resp.sendRedirect("https://venmo.com/Joseph-Hwang-2?txn=pay&amount="+rounded);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse resp){
    }
}
