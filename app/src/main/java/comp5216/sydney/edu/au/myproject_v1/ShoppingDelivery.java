package comp5216.sydney.edu.au.myproject_v1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import comp5216.sydney.edu.au.myproject_v1.model.Request;
import comp5216.sydney.edu.au.myproject_v1.model.User;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.AcceptRequestActivity;
import comp5216.sydney.edu.au.myproject_v1.utils.DistanceCalculator;

public class ShoppingDelivery extends AppCompatActivity implements GoogleMap.OnMarkerClickListener, OnMapReadyCallback {

    private static final String TAG = "ShoppingDelivery";

    private static final DistanceCalculator mDisCalculator = new DistanceCalculator();

    FirebaseAuth mAuth;
    DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    // current location
    private LatLng currLocation;

    GoogleMap mMap;
    FragmentContainerView gMap;
    SupportMapFragment mapFragment;

    SessionManager sessionManager;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_delivery);
        gMap = findViewById(R.id.map);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mAuth = FirebaseAuth.getInstance();

        //get user information
        sessionManager = new SessionManager(getApplicationContext());
        String email = sessionManager.getUsername();
        db.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().toString().equals(email)) {
                        user = ds.getValue(User.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("firebase", error.toException().toString());
            }
        });


        BottomNavigationView bottomNavigationView=findViewById(R.id.button_navigation);
        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.shopping);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent((getApplicationContext()),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.shopping:

                        return true;
                    case R.id.history:
                        startActivity(new Intent((getApplicationContext()),History.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent((getApplicationContext()),Profile.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return  false;
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (mMap != null) {
            /** Get current location */
            double currLat = user.getLat();
            double currLng = user.getLng();
            currLocation = new LatLng(currLat, currLng);

            List<Request> requestList = new ArrayList<>();
            db.child("RequestItem").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {
                        List<Request> allRequests = new ArrayList<>();
                        if (task.getResult() != null) {
                            Map map = (Map) task.getResult().getValue();
                            if (map != null) {
                                for (Object object: map.values()) {
                                    Request request = new Request();
                                    Map m = (Map) object;
                                    request.setTitle(String.valueOf(m.get("title")));
                                    request.setCreatorName(String.valueOf(m.get("creatorName")));
                                    request.setAcceptorName(m.get("acceptorName") == null ? null : String.valueOf(m.get("acceptorName")));
                                    request.setAddress(String.valueOf(m.get("address")));
                                    request.setLat(new Double((m.get("lat")).toString()));
                                    request.setLng(new Double((m.get("lng")).toString()));
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
                                    allRequests.add(request);
                                }
                            }
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                        }
                        // Select all request which are in the distance within 1km
                        for (Object object : allRequests) {
                            Request request = (Request) object;
                            if (request.getStatus().equals("Posted")) {
                                LatLng destination = new LatLng(request.getLat(), request.getLng());
                                if (mDisCalculator.GetDistance(destination, currLocation) < 1000) {
                                    requestList.add(request);
                                }
                            }
                        }
                        // Add markers within 1km to the map
                        for (Request request : requestList) {
                            LatLng destination = new LatLng(request.getLat(), request.getLng());
                            Marker marker = mMap.addMarker(new MarkerOptions()
                                    .position(destination)
                                    .icon(BitmapDescriptorFactory.defaultMarker(240)));
                            marker.setTag(request);
                        }
                        // Add current location marker to the map and move the camera
                        Marker currLocMarker =  mMap.addMarker(new MarkerOptions().position(currLocation));
                        currLocMarker.setTag(null);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLocation));
                        // Enable zoom controls
                        mMap.getUiSettings().setZoomControlsEnabled(true);
                        // Animate the camera to zoom into the map
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currLocation, 18));
                    } else {
                        Log.d("onMapReady", "Error getting documents: ", task.getException());
                    }
                }
            });
        } else {
            Toast.makeText(this, "Error - Map was null!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.getTag() != null) {
            Request request = (Request) marker.getTag();
            Intent intent = new Intent(ShoppingDelivery.this, AcceptRequestActivity.class);
            if (request != null) {
                intent.putExtra("user", user);
                intent.putExtra("request", request);
            }
            this.startActivity(intent);
        }
        return false;
    }

}