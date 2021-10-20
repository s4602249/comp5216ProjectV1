package comp5216.sydney.edu.au.myproject_v1.shoppingRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import comp5216.sydney.edu.au.myproject_v1.History;
import comp5216.sydney.edu.au.myproject_v1.MainActivity;
import comp5216.sydney.edu.au.myproject_v1.Profile;
import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.ShoppingDelivery;
import comp5216.sydney.edu.au.myproject_v1.model.Request;
import comp5216.sydney.edu.au.myproject_v1.model.User;
import comp5216.sydney.edu.au.myproject_v1.notificationPackage.NotificationSender;

public class ConfirmRequestActivity extends AppCompatActivity {
    TextView etTitle;
    TextView etItem1;
    TextView etPrice1;
    TextView etItem2;
    TextView etPrice2;
    TextView etItem3;
    TextView etPrice3;
    TextView etTotalPrice;
    TextView etCreateTime;
    TextView etDueTime;
    TextView etStatus;
    TextView etAddress;
    TextView etCreatorPhoneNumber;
    TextView etAcceptorName;
    TextView etAcceptorPhoneNumber;
    ImageButton ibBack;
    Button btnConfirm;

    User user;
    FirebaseAuth mAuth;
    Request request;
    DateFormat df;
    DatabaseReference db;

    String username;
    String TitlePassHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_request);

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

        mAuth = FirebaseAuth.getInstance();
        //Initial database
        db = FirebaseDatabase.getInstance().getReference().child("RequestItem");

        //Initial date format
        df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        //Initialize view
        ibBack = findViewById(R.id.back);
        etTitle = findViewById(R.id.requestTitle);
        etItem1 = findViewById(R.id.itemDescription1);
        etPrice1 = findViewById(R.id.price1);
        etItem2 = findViewById(R.id.itemDescription2);
        etPrice2 = findViewById(R.id.price2);
        etItem3 = findViewById(R.id.itemDescription3);
        etPrice3 = findViewById(R.id.price3);
        etTotalPrice = findViewById(R.id.textViewTotalPrice);
        etCreateTime = findViewById(R.id.textViewCreateTime);
        etDueTime = findViewById(R.id.textViewDueTime);
        etStatus = findViewById(R.id.textViewStatus);
        etAddress = findViewById(R.id.textViewAddress);
        etCreatorPhoneNumber = findViewById(R.id.textViewCreatorPhoneNumber);
        etAcceptorName = findViewById(R.id.textViewAcceptorName);
        etAcceptorPhoneNumber = findViewById(R.id.textViewAcceptorPhoneNumber);
        btnConfirm = findViewById(R.id.btnAccept);
        //set data
        Intent intent = this.getIntent();

        //get pass data of username and title
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            username=bundle.getString("username");
            TitlePassHere=bundle.getString("title");
        }

        ReadRequest();

        //return function
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(ConfirmRequestActivity.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

        //accept request function
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleFullId = username+TitlePassHere;
                DatabaseReference requestInfo = FirebaseDatabase.getInstance().getReference("RequestItem").child(titleFullId);
                requestInfo.child("status").setValue("Completed");
                requestInfo.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //String creator = snapshot.child("creatorName").getValue().toString();
                        String acceptor = snapshot.child("acceptorName").getValue().toString();

                        DatabaseReference tokenInfo = FirebaseDatabase.getInstance().getReference("TokenDevice").child(acceptor);
                        tokenInfo.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String token = snapshot.child("token").getValue().toString();
                                System.out.println("hello");
                                NotificationSender notificationsSender = new NotificationSender(
                                        token,
                                        "You have completed an order!","Congradulations! You have finished "+username+"'s order! You have earned 2 points!",
                                        getApplicationContext(), ConfirmRequestActivity.this);
                                System.out.println("hehe");
                                notificationsSender.sendNotification();
                                System.out.println("haha");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        DatabaseReference creatorName = FirebaseDatabase.getInstance().getReference("Users").child(acceptor);
                        creatorName.child("point").setValue(ServerValue.increment(2));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent backIntent = new Intent(ConfirmRequestActivity.this, MainActivity.class);
                startActivity(backIntent);
                finish();
            }
        });

    }

    private void ReadRequest(){
        DatabaseReference reference;

        reference= FirebaseDatabase.getInstance().getReference("RequestItem");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datas:snapshot.getChildren()) {
                    //datas.child("email")是一个键值对
                    if (datas.child("title").getValue().toString().equals(TitlePassHere)){
                        etTitle.setText(datas.child("title").getValue().toString());
                        etItem1.setText(datas.child("itemDescription1").getValue().toString());
                        etItem2.setText(datas.child("itemDescription2").getValue().toString());
                        etItem3.setText(datas.child("itemDescription3").getValue().toString());
                        etPrice1.setText(datas.child("price1").getValue().toString());
                        etPrice2.setText(datas.child("price2").getValue().toString());
                        etPrice3.setText(datas.child("price3").getValue().toString());
                        etTotalPrice.setText(datas.child("totalPrice").getValue().toString());

                        long time = Long.parseLong(datas.child("dueTime").getValue().toString());

                        Date date = new Date(time);
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                        etDueTime.setText(formatter.format(date).toString());
                        etAddress.setText(datas.child("address").getValue().toString());
                        etCreatorPhoneNumber.setText(datas.child("creatorPhoneNumber").getValue().toString());
                        etStatus.setText(datas.child("status").getValue().toString());
                        etAcceptorName.setText(datas.child("acceptorName").getValue().toString());
                        etAcceptorPhoneNumber.setText(datas.child("acceptorPhoneNumber").getValue().toString());
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
