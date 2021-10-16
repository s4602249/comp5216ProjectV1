package comp5216.sydney.edu.au.myproject_v1.profile;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import comp5216.sydney.edu.au.myproject_v1.History;
import comp5216.sydney.edu.au.myproject_v1.MainActivity;
import comp5216.sydney.edu.au.myproject_v1.Profile;
import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.ShoppingDelivery;
import comp5216.sydney.edu.au.myproject_v1.map.MapEdit;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;

public class EditProfile extends AppCompatActivity {
    DatabaseReference reference;
    SessionManager sessionManager;
    FirebaseAuth mAuth;


    EditText text4;
    EditText text5;
    EditText text6;
    EditText text7;
    EditText text8;

    Button btnCancel, btnConfirm;
    String address;
    String cardnum;
    String cvv;
    String email;
    String expdata;
    String password;
    int point;
    String tel;
    String username;

    ImageButton curloc;
    Intent intent;
    double lat;
    double lng;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        text4 = findViewById(R.id.field4);
        text5 = findViewById(R.id.field5);
        text6 = findViewById(R.id.field6);
        text7 = findViewById(R.id.field7);
        text8 = findViewById(R.id.field8);
        btnConfirm = findViewById(R.id.edit_profile);
        btnCancel = findViewById(R.id.cancel);
        curloc = findViewById(R.id.findLocationn);
        reference = FirebaseDatabase.getInstance().getReference("Users");
        mAuth = FirebaseAuth.getInstance();
        //Initialize SessionManager
        sessionManager = new SessionManager(getApplicationContext());

        //Get email from session
        String sEmail = sessionManager.getUsername();
        //read data

        Read(sEmail);

        // receive intent
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        if (bundle != null) {
            String data = bundle.getString("Address");
            System.out.println("--------------------------------" + data);
            text4.setText(bundle.getString("telnumIntent"));
            text5.setText(data);
            text6.setText(bundle.getString("CardIntent"));
            text7.setText(bundle.getString("expIntent"));
            text8.setText(bundle.getString("CvvIntent"));
            lat=bundle.getDouble("lat");
            lng=bundle.getDouble("lng");
        }

        btnConfirm.setOnClickListener(view -> {

            if (ifTelChanged() != false && ifAddressChanged() != false && ifCardNumChanged()!=false && ifExpireChanged() != false
                    &&ifCvvChanged() != false)  {
                reference.child(username).child("address").setValue(text5.getText().toString());
                reference.child(username).child("tel").setValue(text4.getText().toString());
                reference.child(username).child("cardnumber").setValue(text6.getText().toString());
                reference.child(username).child("cvv").setValue(text8.getText().toString());
                reference.child(username).child("expiredate").setValue(text7.getText().toString());
                reference.child(username).child("lat").setValue(lat);
                reference.child(username).child("lng").setValue(lng);
                startActivity(new Intent(EditProfile.this, Profile.class));
                Toast.makeText(EditProfile.this, "You have successfully edited your profile!", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EditProfile.this, "Edit failed, try again!", Toast.LENGTH_SHORT).show();
            }


        });

        curloc.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
            builder.setTitle(R.string.dialog_delete_title)
                    .setMessage(R.string.dialog_delete_msg)
                    .setPositiveButton(R.string.cancel, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User cancelled the dialog
                                    // Nothing happens
                                }
                            })
                    .setNegativeButton(R.string.Yes, new
                            DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface, int i) {


                                    intent = new Intent(EditProfile.this, MapEdit.class);
                                    intent.putExtra("telnumIntent",text4.getText().toString());
                                    intent.putExtra("AddressIntent",text5.getText().toString());
                                    intent.putExtra("CardIntent",text6.getText().toString());
                                    intent.putExtra("expIntent",text7.getText().toString());
                                    intent.putExtra("CvvIntent",text8.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            });
            builder.create().show();
        });




        btnCancel.setOnClickListener(view -> {
            startActivity(new Intent(EditProfile.this, Profile.class));
            finish();
        });
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


    public boolean ifAddressChanged() {
        if (TextUtils.isEmpty(text5.getText().toString())) {
            text5.setError("Address cannot be null");
            text5.requestFocus();
            return false;
        } else if (!address.equals(text5.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }


    public boolean ifTelChanged() {
        if (TextUtils.isEmpty(text4.getText().toString())) {
            text4.setError("Tel number cannot be null");
            text4.requestFocus();
            return false;
        }
        else if (!tel.equals(text4.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifCardNumChanged() {
        if (TextUtils.isEmpty(text6.getText().toString())) {
            text6.setError("Card number cannot be null");
            text6.requestFocus();
            return false;
        }
        else if (!cardnum.equals(text6.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifCvvChanged() {
        if (TextUtils.isEmpty(text8.getText().toString())) {
            text8.setError("Cvv cannot be null");
            text8.requestFocus();
            return false;
        }
        else if (!expdata.equals(text8.getText().toString())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean ifExpireChanged() {
        if (TextUtils.isEmpty(text7.getText().toString())) {
            text7.setError("Expire date cannot be null");
            text7.requestFocus();
            return false;
        }
       else if (!expdata.equals(text7.getText().toString())) {
            return true;
        } else {
            return false;
        }
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
