package comp5216.sydney.edu.au.myproject_v1.model;

import java.io.Serializable;

public class User implements Serializable {

    private String username;//user name
    private String email;//user email
    private String password;//user password
    private String phoneNumber;//user phone number
    private String address;//user address
    private String cardnumber;//credit card number
    private String expiredate;//credit card expire date
    private String cvv;//credit card cvv code
    private int point;//user point to determine user level
    private double lat;//user location latitude
    private double lng;//user location longitude

    /**
     Non-parameter constructor of user
     @author Mingle Ao
     @version 1.0
     */
    public User() {
    }

    /**
     constructor with parameter of user
     @author Mingle Ao
     @version 1.0
     @param username a string variable of username
     @param email a string variable of email
     @param password a string variable of password
     @param phoneNumber a string variable of phone number
     @param address a string variable of address
     @param cardnumber a string variable of card number
     @param expiredate a string variable of expire date
     @param cvv a string variable of cvv
     @param lat a double variable of lat
     @param lng a double variable of lng
     */
    public User(String username, String email, String password, String phoneNumber, String address, String cardnumber,
                String expiredate, String cvv,double lat,double lng) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.cardnumber = cardnumber;
        this.expiredate = expiredate;
        this.cvv = cvv;
        this.point = 0;
        this.lat=lat;
        this.lng=lng;
    }

    /**
     Setter and getter of user
     @author Mingle Ao
     @version 1.0
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

}
