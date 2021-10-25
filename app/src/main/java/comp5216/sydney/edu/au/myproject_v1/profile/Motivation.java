package comp5216.sydney.edu.au.myproject_v1.profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import comp5216.sydney.edu.au.myproject_v1.History;
import comp5216.sydney.edu.au.myproject_v1.MainActivity;
import comp5216.sydney.edu.au.myproject_v1.Profile;
import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.ShoppingDelivery;

/**
 * This is just a class to show the motivation page
 */
public class Motivation extends AppCompatActivity {
    ImageButton imageButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_motivation);
        imageButton=findViewById(R.id.Back);
        imageButton.setOnClickListener(view -> {
            startActivity(new Intent(Motivation.this, Profile.class));
            finish();
        });

        // set bottom navigation bar
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
                        //check wifiConnection
                        if(checkWifiConnection()==false){
                            AlertDialog.Builder builder = new AlertDialog.Builder(Motivation.this);
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
                                            Intent intent = new Intent(Motivation.this, Motivation.class);
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

                        return true;
                }
                return false;
            }
        });
    }

    private boolean checkWifiConnection(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return wifi.isConnected();
    }
}
