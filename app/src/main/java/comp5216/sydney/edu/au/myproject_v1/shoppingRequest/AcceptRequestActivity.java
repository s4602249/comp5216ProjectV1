package comp5216.sydney.edu.au.myproject_v1.shoppingRequest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.ShoppingDelivery;
import comp5216.sydney.edu.au.myproject_v1.model.Request;
import comp5216.sydney.edu.au.myproject_v1.model.User;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;

public class AcceptRequestActivity extends AppCompatActivity {

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
    Button btnAccept;

    User user;
    FirebaseAuth mAuth;
    Request request;
    DateFormat df;
    DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_request);
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
        btnAccept = findViewById(R.id.btnAccept);

        //set data
        Intent intent = this.getIntent();
        user = null;
        request = null;
        if (intent != null) {
            //get user information
            /*initializeUser();*/
            /*user = (User) intent.getSerializableExtra("user");*/
            user = (User) intent. getSerializableExtra("user");
            request = (Request) intent.getSerializableExtra("request");
        }
        if (request != null) {
            etTitle.setText(request.getTitle());
            etItem1.setText(request.getItemDescription1() == null ? "None" : request.getItemDescription1());
            etPrice1.setText(String.valueOf(request.getPrice1()));
            etItem2.setText(request.getItemDescription2() == null ? "None" : request.getItemDescription2());
            etPrice2.setText(String.valueOf(request.getPrice2()));
            etItem3.setText(request.getItemDescription3() == null ? "None" : request.getItemDescription3());
            etPrice3.setText(String.valueOf(request.getPrice3()));
            etTotalPrice.setText(String.valueOf(request.getTotalPrice()));
            etCreateTime.setText(df.format(new Date(request.getCreateTime())));
            etDueTime.setText(df.format(new Date(request.getDueTime())));
            etStatus.setText(request.getStatus());
            etAddress.setText(request.getAddress());
            etCreatorPhoneNumber.setText(request.getCreatorPhoneNumber());
            etAcceptorName.setText(request.getAcceptorName() == null ? "None" : request.getAcceptorName());
            etAcceptorPhoneNumber.setText(request.getAcceptorPhoneNumber() == null ? "None" : request.getAcceptorPhoneNumber());
        }

        //return function
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(AcceptRequestActivity.this, ShoppingDelivery.class);
                startActivity(backIntent);
                finish();
            }
        });

        //accept request function
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request.setStatus("Accepted");
                request.setAcceptorName(user.getUsername());
                request.setAcceptorPhoneNumber(user.getPhoneNumber());
                Map<String, Object> updates = new HashMap<>();
                String key = request.getCreatorName() + request.getTitle();
                updates.put(key, request);
                db.updateChildren(updates);

                Intent intent = new Intent(AcceptRequestActivity.this, ShoppingDelivery.class);
                startActivity(intent);
            }
        });
    }

    public void initializeUser() {
        user = new User();
        user.setUsername("Bob");
        user.setEmail("Bob2021@gmail.com");
        user.setPassword("123456");
        user.setAddress("#1A, 2345 Avenue, Sydney");
        user.setLat(-33.8692);
        user.setLng(151.2092);
        user.setPhoneNumber("13000000000");
    }
}