package comp5216.sydney.edu.au.myproject_v1.notificationPackage;

import android.app.Activity;
import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class NotificationSender {

    /***************************************************************************************
     *    Title: Send Notification From One Device to Other (open source code)
     *    Author: Abhishek G
     *    Date: 25/02/2021
     *    Code version: n.d
     *    Availability: https://drive.google.com/drive/folders/1CGLLNnmYQYoMWSCBsvKXOmUyJPsAOdvt?usp=sharing
     *
     ***************************************************************************************/

    String userToken;
    String title;
    String message;
    Context context;
    Activity activity;
    private RequestQueue requestQ;
    private final String url = "https://fcm.googleapis.com/fcm/send";
    private final String serverKey ="AAAAW8WBQxQ:APA91bG5CObi_43E_sjUzQFs1DrfYzHUshDXLkmQYwDXbgoerhxrq6WntdOPxM7ky5onnS_-vblWFBQ7bVbWYMdDUUxDbmzb2rT_a5ref1g1GWwa9f2S5ddhC_rIXMExMMXp3tlb9fNS";

    public NotificationSender(String userToken, String title, String message, Context context, Activity activity) {
        this.userToken = userToken;
        this.title = title;
        this.message = message;
        this.context = context;
        this.activity = activity;
    }

    public void sendNotification() {
        System.out.println("aaaaaa");
        requestQ = Volley.newRequestQueue(activity);
        JSONObject mainObj = new JSONObject();
        try {
            mainObj.put("to", userToken);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title", title);
            notificationObj.put("body", message);
            notificationObj.put("icon", "icon"); // enter icon that exists in drawable only
            mainObj.put("notification", notificationObj);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type", "application/json");
                    header.put("authorization", "key=" + serverKey);
                    return header;
                }
            };
            requestQ.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
