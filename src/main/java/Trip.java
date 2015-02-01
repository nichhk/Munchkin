import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

import java.util.Date;

// IF ERRORS WITH TIME CHECK OUT THE CHEAP SOLUTION AT LINE 38



public class Trip {
    String user;
    String eta;
    String lastOrder;
    String restaurant;
    double flat;
    double percentage;
    int maxOrder;
    String timeLeft;
    String phoneNumber;
    String dropOffLocation;
    long time;
    String customer;

    String rating;
    public Trip(Entity trip){
        System.out.println("His name is"+trip.getKey().getName());
        this.time = Long.parseLong((String) trip.getKey().getName());
        System.out.println("the id is " + time);
        this.dropOffLocation = (String)trip.getProperty("dropOffLocation");
        this.eta = Long.toString((long)trip.getProperty("eta"));
        this.lastOrder = Long.toString((long)trip.getProperty("lastOrder"));
        this.restaurant = (String)trip.getProperty("restaurant");
        try {
            this.flat = Double.parseDouble((String) trip.getProperty("flatFee"));
        }catch (Exception e){this.flat = 0;}
        try {
            this.percentage = Double.parseDouble((String) trip.getProperty("percentFee"));
        } catch (Exception e){this.percentage = 0;}
        this.maxOrder = Integer.parseInt((String)trip.getProperty("maxOrder"));
        this.phoneNumber = new User((String)trip.getProperty("user")).number;
        DateToMilliseconds dateSetter = new DateToMilliseconds();
        long[] timeLeftArray = dateSetter.milliToTimeString(Long.parseLong(lastOrder) - new Date().getTime()+21600000);
        this.timeLeft = formatTimeLeft(timeLeftArray);
        this.eta = dateSetter.timeToDate(Long.parseLong(eta));
        this.lastOrder = dateSetter.timeToDate(Long.parseLong(lastOrder));
        this.customer = "";
        this.rating = (String)trip.getProperty("rating") + " " + (String)trip.getProperty("numReviews");
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try{
            Entity user = datastore.get(KeyFactory.createKey("profile", (String)trip.getProperty("user")));
            this.user = user.getProperty("firstName") + " " + ((String)user.getProperty("lastName")).charAt(0) + ".";
        } catch (Exception e) {e.printStackTrace();}
    }

    private String formatTimeLeft(long[] timeLeftArray ){
        if(timeLeftArray[0]==0){
            return timeLeftArray[1]+" minutes";
        }
        else{
            return timeLeftArray[0] +" hours, "+ timeLeftArray[1]+" minutes";
        }
    }
    public void addCustomer(String toAdd){
        customer += toAdd;
    }
    public void addRating(int rating){
        this.rating = Integer.toString(rating);
    }


}
