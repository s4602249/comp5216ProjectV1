package comp5216.sydney.edu.au.myproject_v1.model;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
    private String title;
    private String creatorName;
    private String acceptorName;
    private String address;
    private double lat;
    private double lng;
    private long createTime;
    private long dueTime;
    private String creatorPhoneNumber;
    private String acceptorPhoneNUmber;
    private String item1Description1;
    private String item1Description2;
    private String item1Description3;
    private String price1;
    private String price2;
    private String price3;
    private String status;
    private String totalPrice;

    public Request() {
    }

    public Request(String title, String creatorName, String acceptorName, String address,
                   double lat, double lng, long createTime, long dueTime, String creatorPhoneNumber,
                   String acceptorPhoneNUmber, String item1Description1, String item1Description2,
                   String item1Description3, String price1, String price2, String price3,
                   String status, String totalPrice) {
        this.title = title;
        this.creatorName = creatorName;
        this.acceptorName = acceptorName;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.createTime = createTime;
        this.dueTime = dueTime;
        this.creatorPhoneNumber = creatorPhoneNumber;
        this.acceptorPhoneNUmber = acceptorPhoneNUmber;
        this.item1Description1 = item1Description1;
        this.item1Description2 = item1Description2;
        this.item1Description3 = item1Description3;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
        this.status = status;
        this.totalPrice = totalPrice;
    }

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

    public String getAcceptorPhoneNUmber() {
        return acceptorPhoneNUmber;
    }

    public void setAcceptorPhoneNUmber(String acceptorPhoneNUmber) {
        this.acceptorPhoneNUmber = acceptorPhoneNUmber;
    }

    public String getItem1Description1() {
        return item1Description1;
    }

    public void setItem1Description1(String item1Description1) {
        this.item1Description1 = item1Description1;
    }

    public String getItem1Description2() {
        return item1Description2;
    }

    public void setItem1Description2(String item1Description2) {
        this.item1Description2 = item1Description2;
    }

    public String getItem1Description3() {
        return item1Description3;
    }

    public void setItem1Description3(String item1Description3) {
        this.item1Description3 = item1Description3;
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

    @Override
    public String toString() {
        return "Request{" +
                "title='" + title + '\'' +
                ", creatorName='" + creatorName + '\'' +
                ", acceptorName='" + acceptorName + '\'' +
                ", address='" + address + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", createTime=" + createTime +
                ", dueTime=" + dueTime +
                ", creatorPhoneNumber='" + creatorPhoneNumber + '\'' +
                ", acceptorPhoneNUmber='" + acceptorPhoneNUmber + '\'' +
                ", item1Description1='" + item1Description1 + '\'' +
                ", item1Description2='" + item1Description2 + '\'' +
                ", item1Description3='" + item1Description3 + '\'' +
                ", price1='" + price1 + '\'' +
                ", price2='" + price2 + '\'' +
                ", price3='" + price3 + '\'' +
                ", status='" + status + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
}
