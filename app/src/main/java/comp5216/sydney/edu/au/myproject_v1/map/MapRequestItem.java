package comp5216.sydney.edu.au.myproject_v1.map;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Button;

import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentContainerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.RegisterActivity;
import comp5216.sydney.edu.au.myproject_v1.shoppingRequest.RequestYourItem;

public class MapRequestItem extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap map;
    SupportMapFragment mapFragment;
    SearchView searchView;
    Button button;
    FragmentContainerView gMap;
    String location;
    double lat;
    double lng;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        searchView = findViewById(R.id.sv_location);
        button = findViewById(R.id.back);
        gMap=findViewById(R.id.google_map);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String textTitleIntent = bundle.getString("textTitleIntent");
        String itemName1Intent = bundle.getString("itemName1Intent");
        String itemName2Intent = bundle.getString("itemName2Intent");
        String itemName3Intent = bundle.getString("itemName3Intent");

        String price1Intent = bundle.getString("price1Intent");
        String price2Intent = bundle.getString("price2Intent");
        String price3Intent = bundle.getString("price3Intent");
        String addressIntent = bundle.getString("addressIntent");
        String contactNumIntent = bundle.getString("contactNumIntent");
        String timeIntent = bundle.getString("timeIntent");
        String username=bundle.getString("usernameIntent");


        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(MapRequestItem.this, RequestYourItem.class);
            try {
                intent1.putExtra("Address", location);
                intent1.putExtra("textTitleIntent", textTitleIntent);
                intent1.putExtra("itemName1Intent", itemName1Intent);
                intent1.putExtra("itemName2Intent", itemName2Intent);
                intent1.putExtra("itemName3Intent", itemName3Intent);
                intent1.putExtra("price1Intent", price1Intent);
                intent1.putExtra("price2Intent", price2Intent);
                intent1.putExtra("price3Intent", price3Intent);
                intent1.putExtra("addressIntent", addressIntent);
                intent1.putExtra("contactNumIntent", contactNumIntent);
                intent1.putExtra("timeIntent", timeIntent);
                intent1.putExtra("lat",lat);
                intent1.putExtra("lng",lng);
                intent1.putExtra("username",username);
                startActivity(intent1);
                finish();

            } catch (NullPointerException e) {

            }


        });

        mapFragment =(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        doStuff();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

    }

    private void doStuff(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                //do map thing
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                location = searchView.getQuery().toString();
                                List<Address> addressList = null;
                                if (location != null || !location.equals("")) {
                                    Geocoder geocoder = new Geocoder(MapRequestItem.this);
                                    try {
                                        addressList = geocoder.getFromLocationName(location, 1);
                                        Address address = addressList.get(0);
                                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                                        map.addMarker(new MarkerOptions().position(latLng).title(location));
                                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                                        lat=address.getLatitude();
                                        lng=address.getLongitude();
                                    } catch (IndexOutOfBoundsException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String s) {
                                return false;
                            }
                        });
                    }
                });


            }
        }).start();



    }


}
