package comp5216.sydney.edu.au.myproject_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.myproject_v1.historyPage.showRequestActivity;
import comp5216.sydney.edu.au.myproject_v1.model.DeviceToken;
import comp5216.sydney.edu.au.myproject_v1.model.Request;
import comp5216.sydney.edu.au.myproject_v1.profile.Motivation;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.CancelYourItem;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.ConfirmRequestActivity;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.RequestYourItem;
import comp5216.sydney.edu.au.myproject_v1.historyPage.showDeliverActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    TextView textView1;
    TextView textView2;
    ImageButton request;
    TextView reqNameView;
    TextView delNameView;
    TextView reqTitleView;

    String address;
    String cardnum;
    String cvv;
    String email;
    String expdata;
    String password;
    int point;
    String tel;
    String username;

    SessionManager sessionManager;

    ArrayList<Request> requests;

    ListView listView;
    ListView dellstView;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ArrayAdapter<Request> adapter;
    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);//activity_main not used anymore
        mAuth = FirebaseAuth.getInstance();
        textView1 = findViewById(R.id.username);
        textView2 = findViewById(R.id.level);
        request = findViewById(R.id.request1);
        reqNameView = findViewById(R.id.reqName);
        delNameView= findViewById(R.id.delName);
        reqTitleView = findViewById(R.id.reqTitle);
        listView = (ListView) findViewById(R.id.lstView);
        dellstView = (ListView) findViewById(R.id.dellstView);


        requests = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, requests);
        dellstView.setAdapter(adapter);


        //Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        //Get username from session
        String sEmail = sessionManager.getUsername();
        System.out.println("sessiontesting-----------------" + sEmail);

        Read(sEmail);

        /* we have session now no need anymore!
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            String data = bundle.getString("emailIntent");
            Read(data);
            System.out.println("------------"+data);
            //textView1.setText(username);
        }*/
        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        //set bottom navigation bar
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.shopping:
                        //check wifiConnection
                        if(checkWifiConnection()==false){
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Wifi warning")
                                    .setMessage("We found that you are not connecting to Wifi. Browsing the map will consume more data usage, are you sure you want to continue?")
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            startActivity(new Intent((getApplicationContext()), ShoppingDelivery.class));
                                            overridePendingTransition(0, 0);
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            builder.create().show();
                        }
                        return true;
                    case R.id.history:
                        startActivity(new Intent((getApplicationContext()), History.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent((getApplicationContext()), Profile.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        //get battery level
        BatteryManager bm = (BatteryManager) MainActivity.this.getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);


        request.setOnClickListener(view -> {
            // if battery lower than 5%, then user cannot post new request
            if(batLevel<5){
                Toast.makeText(MainActivity.this, "Cannot post request since battery is lower than 5% ",
                        Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else{
            reference = FirebaseDatabase.getInstance().getReference("RequestItem");

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //count the number of request, request must <=3
                    int check = 0;
                    int count = 0;
                    if (point <= 10) {
                        count = 1;
                    }
                    if (point > 10 && point <= 20) {
                        count = 2;
                    }
                    if (point > 20 && point <= 30) {
                        count = 3;
                    }
                    if (point > 30) {
                        count = 4;
                    }
                    for (DataSnapshot datas : snapshot.getChildren()) {
                        //datas.child("email")是一个键值对
                        if (datas.getKey().startsWith(username)) {
                            check++;
                            if (check >= count) {
                                Toast.makeText(MainActivity.this, "Cannot request again, since you already reach the maximum", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            Intent intent = new Intent(MainActivity.this, RequestYourItem.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
            }
        });

        //call Listview
        setupListViewListener();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            startActivity(new Intent(MainActivity.this, Wrapper.class));
            finish();
        }
    }

    // read user's data and display in the screen
    public void Read(String data) {
        reference = FirebaseDatabase.getInstance().getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datas : snapshot.getChildren()) {
                    //datas.child("email")是一个键值对
                    if (datas.child("email").getValue().toString().equals(data)) {
                        username = datas.child("username").getValue().toString();
                        address = datas.child("address").getValue().toString();
                        cardnum = datas.child("cardnumber").getValue().toString();
                        cvv = datas.child("cvv").getValue().toString();
                        email = datas.child("email").getValue().toString();
                        expdata = datas.child("expiredate").getValue().toString();
                        password = datas.child("password").getValue().toString();
                        point = Integer.parseInt(datas.child("point").getValue().toString());
                        tel = datas.child("phoneNumber").getValue().toString();
                        textView1.setText(username);

                        //save user token
                        saveToken();

                        if (point <= 10) {
                            textView2.setText("Level 1");
                        } else if (point < 21) {
                            textView2.setText("Level 2");
                        } else if (point < 31) {
                            textView2.setText("Level 3");
                        } else {
                            textView2.setText("Level 4");
                        }
                        ReadReq();
                        getDelivers();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    // read request information from database
    public void ReadReq() {

        // Create an ArrayList of String
        items = new ArrayList<String>();

        reference = FirebaseDatabase.getInstance().getReference("RequestItem");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datas : snapshot.getChildren()) {
                    //datas.child("email")是一个键值对

                    if (datas.getKey().startsWith(username)) { //changed to startswith
                        Log.i("req", username);
                        Log.i("req", datas.child("creatorName").getValue().toString());
                        if (datas.child("creatorName").getValue().toString().equals(username)
                                &&(datas.child("status").getValue().toString().equals("Posted")
                                || datas.child("status").getValue().toString().equals("Accepted"))) {

                            items.add(datas.child("title").getValue().toString());
                            reqNameView.setText("Request Title");

                            itemsAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, items);

                            // Connect the listView and the adapter
                            listView.setAdapter(itemsAdapter);
                        } else {
                            reqNameView.setText("None");
                        }
                    }

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //read deliverys from database
    public void getDelivers() {
        reference = FirebaseDatabase.getInstance().getReference("RequestItem");
        reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                requests.clear();

                List<Request> allRequests = new ArrayList<>();
                if (task.getResult() != null) {
                    java.util.Map map = (java.util.Map) task.getResult().getValue();
                    if (map != null) {
                        for (Object object : map.values()) {
                            Request request = new Request();
                            java.util.Map m = (Map) object;
                            Log.i("acceptorName",String.valueOf(m.get("acceptorName")));
                            if(String.valueOf(m.get("acceptorName")).equals(username)
                                    && (String.valueOf(m.get("status")).equals("Posted")
                                    || String.valueOf(m.get("status")).equals("Accepted"))) {
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

    //list view setup
    private void setupListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long rowId) {
                Log.i("MainActivity", "Long Clicked item " + position);
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long
                    id) {
                String titleName = username+items.get(position);
                System.out.println(titleName);
                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("RequestItem").child(titleName).child("status");
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String statusCheck = snapshot.getValue(String.class);
                        if(statusCheck.equals("Posted")){
                            Intent intent = new Intent(MainActivity.this, CancelYourItem.class);
                            intent.putExtra("username", username);
                            intent.putExtra("title", items.get(position));
                            startActivity(intent);
                            finish();
                        } else if (statusCheck.equals("Accepted")){
                            System.out.println("hello");
                            Intent intent = new Intent(MainActivity.this, ConfirmRequestActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("title", items.get(position));
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        dellstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Request request = (Request) dellstView.getItemAtPosition(position);
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


                Intent intent = new Intent(MainActivity.this, showDeliverActivity.class);
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
    }

    //save user token
    private void saveToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        DatabaseReference reqRef = FirebaseDatabase.getInstance().getReference().child("TokenDevice").child(username);
                        DeviceToken userDevice = new DeviceToken(token, username);
                        reqRef.setValue(userDevice);

                        // Log and toast

                    }
                });
    }

    private boolean checkWifiConnection(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi.isConnected();
    }
}