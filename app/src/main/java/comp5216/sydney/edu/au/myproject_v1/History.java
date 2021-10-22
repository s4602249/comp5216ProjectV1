package comp5216.sydney.edu.au.myproject_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.myproject_v1.model.Request;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;
import comp5216.sydney.edu.au.myproject_v1.historyPage.showRequestActivity;

public class History extends AppCompatActivity {

    ListView listView;
    ArrayAdapter<Request> adapter;
    ArrayList<Request> requests;
    SessionManager sessionManager;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    String username;
    Button reqBtn, delBtn;
    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.history);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent((getApplicationContext()), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.shopping:
                        startActivity(new Intent((getApplicationContext()), ShoppingDelivery.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.history:

                        return true;
                    case R.id.profile:
                        startActivity(new Intent((getApplicationContext()), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
        class MyClickListener implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                switch(v.getId()) {
                    case R.id.requestButton:
                        delBtn.setBackgroundColor(Color.parseColor("#9e9e9e"));
                        reqBtn.setBackgroundColor(Color.parseColor("#6200EE"));
                        getRequests();
                        break;
                    case R.id.deliverButton:
                        reqBtn.setBackgroundColor(Color.parseColor("#9e9e9e"));
                        delBtn.setBackgroundColor(Color.parseColor("#6200EE"));
                        getDelivers();
                        break;
                }
            }
        }

        listView = findViewById(R.id.requestHistory);
        reqBtn = findViewById(R.id.requestButton);
        reqBtn.setBackgroundColor(Color.parseColor("#6200EE"));
        delBtn = findViewById(R.id.deliverButton);
        delBtn.setBackgroundColor(Color.parseColor("#9e9e9e"));
        MyClickListener myClickListener = new MyClickListener();
        reqBtn.setOnClickListener(myClickListener);
        delBtn.setOnClickListener(myClickListener);
        requests = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requests);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Request request = (Request) listView.getItemAtPosition(position);
                String title = request.getTitle();
                String itemDescription1 = request.getItemDescription1();
                String itemDescription2 = request.getItemDescription2();
                String itemDescription3 = request.getItemDescription3();
                String price1 = request.getPrice1();
                String price2 = request.getPrice2();
                String price3 = request.getPrice3();
                String totalPrice = request.getTotalPrice();
                String createTime = format.format(request.getCreateTime());
                String dueTime = format.format(request.getDueTime());
                String status = request.getStatus();
                String acceptorName = request.getAcceptorName();
                String acceptorPhoneNumber = request.getAcceptorPhoneNumber();
                String creatorName = request.getCreatorName();
                String creatorPhoneNumber = request.getCreatorPhoneNumber();


                Intent intent = new Intent(History.this, showRequestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", title);
                bundle.putString("itemDescription1", itemDescription1);
                bundle.putString("itemDescription2", itemDescription2);
                bundle.putString("itemDescription3", itemDescription3);
                bundle.putString("price1", price1);
                bundle.putString("price2", price2);
                bundle.putString("price3", price3);
                bundle.putString("totalPrice", totalPrice);
                bundle.putString("createTime", createTime);
                bundle.putString("dueTime", dueTime);
                bundle.putString("status", status);
                bundle.putString("acceptorName", acceptorName);
                bundle.putString("acceptorPhoneNumber", acceptorPhoneNumber);
                bundle.putString("creatorName", creatorName);
                bundle.putString("creatorPhoneNumber", creatorPhoneNumber);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        //Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        //Get username from session
        String sEmail = sessionManager.getUsername();
        getTheUsername(sEmail);
    }


    public void getRequests() {
        reference = FirebaseDatabase.getInstance().getReference("RequestItem");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                requests.clear();

                if (task.getResult() != null) {
                    Map map = (Map) task.getResult().getValue();
                    if (map != null) {
                        for (Object object : map.values()) {
                            Request request = new Request();
                            Map m = (Map) object;
//                            Log.i("creatorName",String.valueOf(m.get("creatorName")));
//                            Log.i("userame",username);
                            if(String.valueOf(m.get("creatorName")).equals(username)
                                    && (String.valueOf(m.get("status")).equals("Completed")
                            || String.valueOf(m.get("status")).equals("Overdue"))) {
//                                Log.i("mylog","load?");
//                                Log.i("nbame",String.valueOf(m.get("acceptorName")));
                                request.setTitle(String.valueOf(m.get("title")));
                                request.setCreatorName(String.valueOf(m.get("creatorName")));
                                request.setAcceptorName(m.get("acceptorName") == null ? null : String.valueOf(m.get("acceptorName")));
                                request.setAddress(String.valueOf(m.get("address")));
                                request.setLat((double) m.get("lat"));
                                request.setLng((double) m.get("lng"));
                                request.setCreateTime((long) m.get("createTime"));
                                request.setDueTime((long) m.get("dueTime"));
                                request.setCreatorPhoneNumber(String.valueOf(m.get("creatorPhoneNumber")));
                                request.setAcceptorPhoneNumber(m.get("acceptorPhoneNumber") == null ? null : String.valueOf(m.get("acceptorPhoneNumber")));
                                request.setItemDescription1(String.valueOf(m.get("itemDescription1")));
                                request.setPrice1(String.valueOf(m.get("price1")));
                                request.setItemDescription2(String.valueOf(m.get("itemDescription2")));
                                request.setPrice2(String.valueOf(m.get("price2")));
                                request.setItemDescription3(String.valueOf(m.get("itemDescription3")));
                                request.setPrice3(String.valueOf(m.get("price3")));
                                request.setTotalPrice(String.valueOf(m.get("totalPrice")));
                                request.setStatus(String.valueOf(m.get("status")));
                                requests.add(request);
                            }

                        }
                    }

                }

                adapter.notifyDataSetChanged();
            }

        });


    }

    public void getDelivers() {
        reference = FirebaseDatabase.getInstance().getReference("RequestItem");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                requests.clear();

                List<Request> allRequests = new ArrayList<>();
                if (task.getResult() != null) {
                    Map map = (Map) task.getResult().getValue();
                    if (map != null) {
                        for (Object object : map.values()) {
                            Request request = new Request();
                            Map m = (Map) object;
                            // Log.i("acceptorName",String.valueOf(m.get("acceptorName")));
                            if(String.valueOf(m.get("acceptorName")).equals(username)
                                    && (String.valueOf(m.get("status")).equals("Completed")
                                    || String.valueOf(m.get("status")).equals("Overdue"))) {
                                request.setTitle(String.valueOf(m.get("title")));
                                request.setCreatorName(String.valueOf(m.get("creatorName")));
                                request.setAcceptorName(m.get("acceptorName") == null ? null : String.valueOf(m.get("acceptorName")));
                                request.setAddress(String.valueOf(m.get("address")));
                                request.setLat((double) m.get("lat"));
                                request.setLng((double) m.get("lng"));
                                request.setCreateTime((long) m.get("createTime"));
                                request.setDueTime((long) m.get("dueTime"));
                                request.setCreatorPhoneNumber(String.valueOf(m.get("creatorPhoneNumber")));
                                request.setAcceptorPhoneNumber(m.get("acceptorPhoneNumber") == null ? null : String.valueOf(m.get("acceptorPhoneNumber")));
                                request.setItemDescription1(String.valueOf(m.get("itemDescription1")));
                                request.setPrice1(String.valueOf(m.get("price1")));
                                request.setItemDescription2(String.valueOf(m.get("itemDescription2")));
                                request.setPrice2(String.valueOf(m.get("price2")));
                                request.setItemDescription3(String.valueOf(m.get("itemDescription3")));
                                request.setPrice3(String.valueOf(m.get("price3")));
                                request.setTotalPrice(String.valueOf(m.get("totalPrice")));
                                request.setStatus(String.valueOf(m.get("status")));
                                requests.add(request);
                            }

                        }
                    }

                }

                adapter.notifyDataSetChanged();


            }

        });


    }

    public void getTheUsername(String email) {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.getResult() != null) {
                    Map map = (Map) task.getResult().getValue();
                    if (map != null) {
                        for (Object object : map.values()) {
                            Map m = (Map) object;
//                            Log.i("email",String.valueOf(m.get("email")));
                            if(String.valueOf(m.get("email")).equals(email)) {
                                username = String.valueOf(m.get("username"));
                                // username = "Bob";
//                                Log.i("username",username);
                                getRequests();
                                break;
                            }
                        }
                    }

                }
            }

        });
    }







}


