package comp5216.sydney.edu.au.myproject_v1.utils;

import com.google.android.gms.maps.model.LatLng;

/**
 * a class to calculate the distance of two location
 * @author Mingle Ao
 * @version 1.0
 */
public class DistanceCalculator {

    private static double EARTH_RADIUS = 6378137.0;

    /**
     * convert degrees to radians
     * @author Mingle Ao
     * @version 1.0
     * @param d a double variable of degrees
     * @return a double variable of radians
     */
    private double rad(double d){
        return d * Math.PI / 180.0;
    }

    /**
     * calculate the distance of two location
     * @author Mingle Ao
     * @version 1.0
     * @param latLng1 a LatLng variable of location1
     * @param latLng2 a LatLng variable of location2
     * @return a double variable of the distance in meter
     */
    public double GetDistance(LatLng latLng1, LatLng latLng2){
        double radLat1 = rad(latLng1.latitude);
        double radLat2 = rad(latLng2.latitude);
        double a = radLat1 - radLat2;
        double b = rad(latLng1.longitude) - rad(latLng2.longitude);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
}
