package comp5216.sydney.edu.au.myproject_v1.shoppingRequest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import comp5216.sydney.edu.au.myproject_v1.History;
import comp5216.sydney.edu.au.myproject_v1.MainActivity;
import comp5216.sydney.edu.au.myproject_v1.Profile;
import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.ShoppingDelivery;


/**
 * This class is used to cancel the request item
 */
public class CancelYourItem extends AppCompatActivity {
    String username;
    String TitlePassHere;
    TextView title;

    //Users can request three items at most one time
    TextView itemName1;
    TextView itemName2;
    TextView itemName3;
    TextView price1;
    TextView price2;
    TextView price3;
    TextView TotalPrice;
    TextView ArrTime;
    TextView Address;
    TextView ConNum;
    TextView Status;
    Button cancel;
    ImageButton back;

    DatabaseReference reference;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_item_request);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(CancelYourItem.this);
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
                                            Intent intent = new Intent(CancelYourItem.this, CancelYourItem.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                            builder.create().show();
                        } else{
                            startActivity(new Intent((getApplicationContext()), ShoppingDelivery.class));
                            overridePendingTransition(0, 0);
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

        //back to MainActivity class
        back=findViewById(R.id.back1);
        back.setOnClickListener(view -> {
            Intent intent = new Intent(CancelYourItem.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
        //get information from MainActivity
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        if (bundle != null) {
            username=bundle.getString("username");
            TitlePassHere=bundle.getString("title");
        }
        title=findViewById(R.id.titleCan);
        itemName1=findViewById(R.id.name1Can);
        itemName2=findViewById(R.id.name2Can);
        itemName3=findViewById(R.id.name3Can);
        price1=findViewById(R.id.price1Can);
        price2=findViewById(R.id.price2Can);
        price3=findViewById(R.id.price3Can);
        TotalPrice=findViewById(R.id.totalPriceCan);
        ArrTime=findViewById(R.id.timeCan);
        Address=findViewById(R.id.addressCan);
        ConNum=findViewById(R.id.numCan);
        Status=findViewById(R.id.statusCan);
        cancel=findViewById(R.id.cancelReq);
        //read data from realtime database
        ReadReq(username);
        cancel.setOnClickListener(view -> {
            DeleteReq(username);
            startActivity(new Intent(CancelYourItem.this, MainActivity.class));
            Toast.makeText(CancelYourItem.this, "You have delte your request!", Toast.LENGTH_SHORT).show();
            finish();
        });


    }

    /**
     * delete request from database
     * @param name
     */
    public void DeleteReq(String name){
        reference= FirebaseDatabase.getInstance().getReference("RequestItem");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot datas: dataSnapshot.getChildren()) {
                    if (datas.child("title").getValue().toString().equals(TitlePassHere)){
                        datas.getRef().removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * read data from database
     * @param name
     */
    public void ReadReq(String name){
        reference= FirebaseDatabase.getInstance().getReference("RequestItem");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datas:snapshot.getChildren()) {
                    //datas.child("email")是一个键值对
                    if (datas.child("title").getValue().toString().equals(TitlePassHere)){
                        title.setText(datas.child("title").getValue().toString());
                        itemName1.setText(datas.child("itemDescription1").getValue().toString());
                        itemName2.setText(datas.child("itemDescription2").getValue().toString());
                        itemName3.setText(datas.child("itemDescription3").getValue().toString());
                        price1.setText(datas.child("price1").getValue().toString());
                        price2.setText(datas.child("price2").getValue().toString());
                        price3.setText(datas.child("price3").getValue().toString());
                        TotalPrice.setText(datas.child("totalPrice").getValue().toString());

                       /* SimpleDateFormat format = new SimpleDateFormat();
                        Date dueTime = null;
                        try {
                            dueTime = format.parse(datas.child("dueTime").getValue().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }*/



                        long time = Long.parseLong(datas.child("dueTime").getValue().toString());


                        Date date = new Date(time);
                        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                        ArrTime.setText(formatter.format(date).toString());
                        Address.setText(datas.child("address").getValue().toString());
                        ConNum.setText(datas.child("creatorPhoneNumber").getValue().toString());
                        Status.setText(datas.child("status").getValue().toString());
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean checkWifiConnection(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi.isConnected();
    }
}
