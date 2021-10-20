package comp5216.sydney.edu.au.myproject_v1.model;

import java.io.Serializable;

public class DeviceToken implements Serializable {
    private String token;

    private String username;

    public DeviceToken(){
    }

    public DeviceToken(String token, String username){
        this.token=token;
        this.username=username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
