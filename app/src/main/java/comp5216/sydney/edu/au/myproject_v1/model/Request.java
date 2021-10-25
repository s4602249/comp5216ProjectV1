package comp5216.sydney.edu.au.myproject_v1.model;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private String title;//request title
    private String creatorName;//request creator name
    private String acceptorName;//request acceptor name
    private String address;//request destination address
    private double lat;//latitude of request destination address
    private double lng;//longitude of request destination address
    private long createTime;//request create time
    private long dueTime;//request due time
    private String creatorPhoneNumber;//request creator phone number
    private String acceptorPhoneNumber;//request acceptor phone number
    private String itemDescription1;//item 1 name
    private String itemDescription2;//item 2 name
    private String itemDescription3;//item 3 name
    private String price1;//item 1 price
    private String price2;//item 2 price
    private String price3;//item 3 price
    private String status;//request status [posted, accepted, completed, overdue]
    private String totalPrice;//request items total price

    /**
     Non-parameter constructor of request
     @author Mingle Ao
     @version 1.0
     */
    public Request() {
    }

    /**
     constructor with parameter of user
     @author Mingle Ao
     @version 1.0
     @param title a string variable of request name
     @param creatorName a string variable of request creator name
     @param acceptorName a string variable of request acceptor name
     @param address a string variable of request destination address
     @param lat a double variable of latitude of request destination address
     @param lng a double variable of longitude of request destination address
     @param createTime a string variable of request create time
     @param dueTime a string variable of request due time
     @param creatorPhoneNumber a string variable of request creator phone number
     @param acceptorPhoneNumber a string variable of request acceptor phone number
     @param itemDescription1 a string variable of item 1 name
     @param price1 a string variable of item 1 price
     @param itemDescription2 a string variable of item 2 name
     @param price2 a string variable of item 2 price
     @param itemDescription3 a string variable of item 3 name
     @param price3 a string variable of item 3 price
     @param status a string variable of request status
     @param totalPrice a string variable of request items total price
     */
    public Request(String title, String creatorName, String acceptorName, String address, double lat,
                   double lng, long createTime, long dueTime, String creatorPhoneNumber, String acceptorPhoneNumber, String itemDescription1, String itemDescription2, String itemDescription3,
                   String price1, String price2, String price3, String status, String totalPrice) {
        this.title = title;
        this.creatorName = creatorName;
        this.acceptorName = acceptorName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.createTime = createTime;
        this.dueTime = dueTime;
        this.creatorPhoneNumber = creatorPhoneNumber;
        this.acceptorPhoneNumber = acceptorPhoneNumber;
        this.itemDescription1 = itemDescription1;
        this.itemDescription2 = itemDescription2;
        this.itemDescription3 = itemDescription3;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    /**
     Setter and getter of request
     @author Mingle Ao
     @version 1.0
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getDueTime() {
        return dueTime;
    }

    public void setDueTime(long dueTime) {
        this.dueTime = dueTime;
    }

    public String getCreatorPhoneNumber() {
        return creatorPhoneNumber;
    }

    public void setCreatorPhoneNumber(String creatorPhoneNumber) {
        this.creatorPhoneNumber = creatorPhoneNumber;
    }

    public String getAcceptorPhoneNumber() {
        return acceptorPhoneNumber;
    }

    public void setAcceptorPhoneNumber(String acceptorPhoneNumber) {
        this.acceptorPhoneNumber = acceptorPhoneNumber;
    }

    public String getItemDescription1() {
        return itemDescription1;
    }

    public void setItemDescription1(String itemDescription1) {
        this.itemDescription1 = itemDescription1;
    }

    public String getItemDescription2() {
        return itemDescription2;
    }

    public void setItemDescription2(String itemDescription2) {
        this.itemDescription2 = itemDescription2;
    }

    public String getItemDescription3() {
        return itemDescription3;
    }

    public void setItemDescription3(String itemDescription3) {
        this.itemDescription3 = itemDescription3;
    }

    public String getPrice1() {
        return price1;
    }

    public void setPrice1(String price1) {
        this.price1 = price1;
    }

    public String getPrice2() {
        return price2;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public String getPrice3() {
        return price3;
    }

    public void setPrice3(String price3) {
        this.price3 = price3;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

//    @Override
//    public String toString() {
//        return "Request{" +
//                "title='" + title + '\'' +
//                ", creatorName='" + creatorName + '\'' +
//                ", acceptorName='" + acceptorName + '\'' +
//                ", address='" + address + '\'' +
//                ", lat=" + lat +
//                ", lng=" + lng +
//                ", createTime=" + createTime +
//                ", dueTime=" + dueTime +
//                ", creatorPhoneNumber='" + creatorPhoneNumber + '\'' +
//                ", acceptorPhoneNumber='" + acceptorPhoneNumber + '\'' +
//                ", itemDescription1='" + itemDescription1 + '\'' +
//                ", itemDescription2='" + itemDescription2 + '\'' +
//                ", itemDescription3='" + itemDescription3 + '\'' +
//                ", price1='" + price1 + '\'' +
//                ", price2='" + price2 + '\'' +
//                ", price3='" + price3 + '\'' +
//                ", status='" + status + '\'' +
//                ", totalPrice='" + totalPrice + '\'' +
//                '}';
//    }
    @Override
    public String toString() {
        return title;
    }
}