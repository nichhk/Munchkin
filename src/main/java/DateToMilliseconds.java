/**
 * Created by lukesamora on 1/31/15.
 */
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateToMilliseconds{

    public long dateToTime(int[] exact_time){
        GregorianCalendar cal = new GregorianCalendar(exact_time[0], exact_time[1]-1, exact_time[2], exact_time[3], exact_time[4]);
        cal.setTimeZone(TimeZone.getTimeZone("America/Houston"));
        return cal.getTime().getTime();
    }
    public String timeToDate(long time){
        Calendar calendar=GregorianCalendar.getInstance();

        calendar.setTimeInMillis(time);
        calendar.setTimeZone(TimeZone.getTimeZone("America/Houston"));
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        int mHour = calendar.get(Calendar.HOUR);
        int amOrPm = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get((Calendar.MINUTE));
        return (mHour==0?12:mHour)+":"+mMinute + (amOrPm<12?"am":"pm")+ " "+ (mMonth+1)+"/"+mDay;
    }
    public long[] milliToTimeString(long mill){ // Formats a millisecond time to a String
        long sec = (long)(mill/1000.0);
        long[] returnVal = new long[3];
        returnVal[0] =  sec/3600;
        returnVal[1] = (long)(sec%3600)/60;
        returnVal[2] = (long)(sec%3600)%60;
        return returnVal;
    }
}