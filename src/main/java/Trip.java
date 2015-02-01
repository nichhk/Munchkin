import com.google.appengine.api.datastore.Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    String time;
    List<String> customer = new ArrayList<String>();
    List<String> cusNumbers = new ArrayList<String>();
    String rating;
    public Trip(Entity trip){
        this.time = trip.getKey().getName();
        System.out.println("the id is " + time);
        this.dropOffLocation = (String)trip.getProperty("dropOffLocation");
        this.eta = (String)trip.getProperty("eta");
        this.lastOrder = (String)trip.getProperty("lastOrder");
        this.restaurant = (String)trip.getProperty("restaurant");
        this.flat = Double.parseDouble((String)trip.getProperty("flatFee"));
        this.percentage = Double.parseDouble((String)trip.getProperty("percentFee"));
        this.maxOrder = Integer.parseInt((String)trip.getProperty("maxOrder"));
        this.phoneNumber = new User((String)trip.getProperty("user")).number;
        DateToMilliseconds dateSetter = new DateToMilliseconds();
        long[] timeLeftArray = dateSetter.milliToTimeString(Long.parseLong(lastOrder) - new Date().getTime()+21600000);
        this.timeLeft = formatTimeLeft(timeLeftArray);
        this.eta = dateSetter.timeToDate(Long.parseLong(eta));
        this.lastOrder = dateSetter.timeToDate(Long.parseLong(lastOrder));
    }


/*


    public Trip(String userName, String eta, String lastOrder, String restaurant,
                String flat, String percentage, String maxOrder, String phoneNumber, String dropOffLocation, String id){
        this.id = id;
        this.dropOffLocation  = dropOffLocation;
        this.eta = eta;
        this.lastOrder = lastOrder;

            this.flat = Double.parseDouble(flat);


            this.percentage = Double.parseDouble(percentage);

        this.maxOrder = Integer.parseInt(maxOrder);
        this.user = userName;
        this.phoneNumber = phoneNumber;
        //this.eta = new DateToMilliseconds().timeToDate(eta);
        //this.lastOrder = new DateToMilliseconds().timeToDate(lastOrder);
        this.restaurant = restaurant;
        DateToMilliseconds dateSetter = new DateToMilliseconds();
        long[] timeLeftArray = dateSetter.milliToTimeString(Long.parseLong(lastOrder) - new Date().getTime()+21600000);
        this.timeLeft = formatTimeLeft(timeLeftArray);
        System.out.println("timeleft is"+timeLeft);
        this.eta = dateSetter.timeToDate(Long.parseLong(eta));
        this.lastOrder = dateSetter.timeToDate(Long.parseLong(lastOrder));
        this.dropOffLocation = dropOffLocation;
    }*/
    private String formatTimeLeft(long[] timeLeftArray ){
        if(timeLeftArray[0]==0){
            return timeLeftArray[1]+" minutes";
        }
        else{
            return timeLeftArray[0] +" hours, "+ timeLeftArray[1]+" minutes";
        }
    }
    public void addCustomer(String cus, String num){
        customer.add(cus);
        cusNumbers.add(num);
    }
    public void addRating(int rating){
        this.rating = Integer.toString(rating);
    }


}
