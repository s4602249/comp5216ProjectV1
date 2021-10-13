package comp5216.sydney.edu.au.myproject_v1.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    // initialize variable
    /*
    *android.content.SharedPreferences.Editor. Interface used for modifying values in a SharedPreferences object.
    * All changes you make in an editor are batched,
    * and not copied back to the original SharedPreferences until you call commit() or apply()*/
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    //Create constructor
    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("Appkey", 0);
        editor = sharedPreferences.edit();
        editor.apply();
    }

    //Create set login method
    public void setLogin(boolean login){
        editor.putBoolean("KEY_LOGIN",login);
        editor.commit();
    }

    //Create get login method
    public boolean getLogin(){
        return sharedPreferences.getBoolean("KEY_LOGIN",false);
    }

    //Create set username method
    public void setUsername(String username){
        editor.putString("KEY_USERNAME",username);
        editor.commit();
    }

    //Create get username method
    public String getUsername(){
        return sharedPreferences.getString("KEY_USERNAME","");
    }
}
