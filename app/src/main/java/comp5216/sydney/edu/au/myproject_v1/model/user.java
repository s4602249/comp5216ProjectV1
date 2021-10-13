package comp5216.sydney.edu.au.myproject_v1.model;

public class user {

    public String username;
    public String email;
    public String password;
    public String tel;
    public String address;
    public String cardnumber;
    public String expiredate;
    public String cvv;
    public int point;


    public user(String username, String email, String password, String tel, String address, String cardnumber,
                String expiredate, String cvv) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.tel = tel;
        this.address = address;
        this.cardnumber = cardnumber;
        this.expiredate = expiredate;
        this.cvv = cvv;
        this.point = 0;
    }

    public String getUsername() { return username; }

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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public int getPoint() { return point; }

    public void setPoint(int point) { this.point = point; }

}
