package comp5216.sydney.edu.au.myproject_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import comp5216.sydney.edu.au.myproject_v1.map.Map;
import comp5216.sydney.edu.au.myproject_v1.profile.Motivation;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.CancelYourItem;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.RequestYourItem;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference reference;
    TextView textView1;
    TextView textView2;
    ImageButton request;
    TextView reqNameView;
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

    ListView listView;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);//activity_main not used anymore
        mAuth = FirebaseAuth.getInstance();
        textView1 = findViewById(R.id.username);
        textView2 = findViewById(R.id.level);
        request = findViewById(R.id.request1);
        reqNameView = findViewById(R.id.reqName);
        reqTitleView = findViewById(R.id.reqTitle);
        listView = (ListView) findViewById(R.id.lstView);

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
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        return true;
                    case R.id.shopping:
                        startActivity(new Intent((getApplicationContext()), ShoppingDelivery.class));
                        overridePendingTransition(0, 0);
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

        request.setOnClickListener(view -> {
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

    // read user's data and display in scrren
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
                        tel = datas.child("tel").getValue().toString();
                        textView1.setText(username);
                        if (point <= 10) {
                            textView2.setText("Level 1");
                        } else if (point < 21) {
                            textView2.setText("Level 2");
                        } else if (point < 31) {
                            textView2.setText("Level 3");
                        } else {
                            textView2.setText("Level 4");
                        }

                    }
                }
                ReadReq();

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
                reqNameView.setText("None");

                for (DataSnapshot datas : snapshot.getChildren()) {
                    //datas.child("email")是一个键值对
                    if (datas.getKey().startsWith(username)) { //changed to startswith
                        items.add(datas.child("title").getValue().toString());
                        reqNameView.setText("Request Title");

                        itemsAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, items);

                        // Connect the listView and the adapter
                        listView.setAdapter(itemsAdapter);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                //Go to cancel request page

                Intent intent = new Intent(MainActivity.this, CancelYourItem.class);
                intent.putExtra("username", username);
                intent.putExtra("title", items.get(position));
                startActivity(intent);
                finish();

            }
        });
    }
}