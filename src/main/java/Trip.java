/**
 * Created by nich on 1/31/15.
 */
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
    public Trip(String userName, String eta, String lastOrder, String restaurant,
                String flat, String percentage, String maxOrder, String phoneNumber){
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
        long[] timeLeftArray = dateSetter.milliToTimeString(Long.parseLong(lastOrder) - System.currentTimeMillis());
        this.timeLeft = formatTimeLeft(timeLeftArray);
        this.eta = dateSetter.timeToDate(Long.parseLong(eta));
        this.lastOrder = dateSetter.timeToDate(Long.parseLong(lastOrder));
    }
    private String formatTimeLeft(long[] timeLeftArray ){
        String returnVal = timeLeftArray[0] +" hours, "+ timeLeftArray[1]+" minutes";
        return returnVal;
    }


}
