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
    String id;
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
    }
    private String formatTimeLeft(long[] timeLeftArray ){
        if(timeLeftArray[0]==0){
            return timeLeftArray[1]+" minutes";
        }
        else{
            return timeLeftArray[0] +" hours, "+ timeLeftArray[1]+" minutes";
        }
    }


}
