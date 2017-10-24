package com.sadhika.bmwproject.utils;

import android.location.Location;

import com.sadhika.bmwproject.model.pojos.LocationInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sadhika on 7/6/17.
 */


public class Utils {


    /**
     *  Returns long time stamp for given date string.
     * @param arrivalTime
     * @return
     */
    public static long getArrivalTimeStamp(String arrivalTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(arrivalTime);
        } catch (ParseException e) {
        }
        return  null == date ? -1 : date.getTime();
    }

    /**\
     * Just a converter method that gives Location out of LocationInfo.
     * @param locInfo
     * @return
     */
    public static Location LocationInfoToLocation(LocationInfo locInfo) {
        Location loc = new Location("");
        if ( locInfo == null ) {
            return loc;
        }
        loc.setLatitude(locInfo.getLatitude());
        loc.setLongitude(locInfo.getLongitude());
        return loc;
    }

    /**
     * Returns time difference from current time in readable format.
     * @param time
     * @return
     */
    public static String getRemainingTimeToArrive(String time) {

        long timeToReach = getArrivalTimeStamp(time) - new Date().getTime();
        return timeToReach < 0 ? "Already Reached" : timeStampToReadableFormat(timeToReach);
    }

    private static String timeStampToReadableFormat(long timeStamp) {
        StringBuilder sb = new StringBuilder();
        long days = TimeUnit.MILLISECONDS.toDays(timeStamp);
        if (days > 0) {
            sb.append(days + " days ");
        }
        long hours = TimeUnit.MILLISECONDS.toHours(timeStamp) -
                TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toHours(timeStamp));

        if (hours > 0) {
            sb.append(hours + " hours ");
        }

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeStamp) -
                TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeStamp));
        if (minutes > 0) {
            sb.append(minutes + " minutes ");
        }

        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeStamp) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeStamp));

        if (seconds > 0 ) {
            sb.append(seconds + " seconds");
        }

        return sb.toString();
    }

}
