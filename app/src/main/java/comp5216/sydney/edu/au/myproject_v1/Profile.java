package comp5216.sydney.edu.au.myproject_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comp5216.sydney.edu.au.myproject_v1.profile.EditProfile;
import comp5216.sydney.edu.au.myproject_v1.profile.Motivation;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;

public class Profile extends AppCompatActivity {
    Button buttonLogout;
    Button buttonEdit;
    SessionManager sessionManager;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    ImageButton imageButton;

    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    TextView textView5;
    TextView textView6;
    TextView textView7;

    String address;
    String cardnum;
    String cvv;
    String email;
    String expdata;
    String password;
    int point;
    String tel;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textView1 = findViewById(R.id.username);
        textView2 = findViewById(R.id.level);
        textView7=findViewById(R.id.points);

        textView3 = findViewById(R.id.email1);
        textView4=findViewById(R.id.address1);
        textView5=findViewById(R.id.contactnum1);
        textView6=findViewById(R.id.cardnum1);
        imageButton=findViewById(R.id.question);
        imageButton.setOnClickListener(view -> {
            startActivity(new Intent(Profile.this, Motivation.class));
            finish();
        });


        mAuth = FirebaseAuth.getInstance();
        //Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        //Get username from session
        String sEmail = sessionManager.getUsername();


        buttonEdit = findViewById(R.id.edit_profile);
        buttonEdit.setOnClickListener(view -> {
            Toast.makeText(Profile.this, "You can edit your profile now!" , Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Profile.this, EditProfile.class));
            finish();
        });



        buttonLogout = findViewById(R.id.cancel);
        buttonLogout.setOnClickListener(view -> {
            mAuth.signOut();
            sessionManager.setLogin(false);
            sessionManager.setUsername("");

            startActivity(new Intent(Profile.this, Wrapper.class));
            Toast.makeText(Profile.this, "You have logged out already!" , Toast.LENGTH_SHORT).show();
            finish();
        });

        Read(sEmail);

        BottomNavigationView bottomNavigationView = findViewById(R.id.button_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.profile);
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
                        startActivity(new Intent((getApplicationContext()), History.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.profile:

                        return true;
                }
                return false;
            }
        });
    }

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
                        System.out.println("------------tel" + tel);
                        System.out.println("------------username" + username);
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
                        String StrPoint = point + "";
                        textView7.setText("Points: "+StrPoint);

                        textView3.setText(email);
                        textView4.setText(address);
                        textView5.setText(tel);
                        textView6.setText(cardnum);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}