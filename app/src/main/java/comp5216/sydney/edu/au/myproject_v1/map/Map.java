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
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import comp5216.sydney.edu.au.myproject_v1.R;
import comp5216.sydney.edu.au.myproject_v1.RegisterActivity;

/**
 * This class is used for RegisterActivity class, and save the information that passed from RegisterActivity
 */
public class Map extends FragmentActivity implements OnMapReadyCallback {
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

        //receive the information from RegisterActivity
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String emailIntent = bundle.getString("emailIntent");
        String passwordIntent = bundle.getString("passwordIntent");
        String NameIntent = bundle.getString("NameIntent");
        String telnumIntent = bundle.getString("telnumIntent");
        String CardIntent = bundle.getString("CardIntent");
        String expIntent = bundle.getString("expIntent");
        String CvvIntent = bundle.getString("CvvIntent");
        System.out.println("--------------------------------" + CvvIntent);
        System.out.println("--------------------------------" + emailIntent);

        //when click the button, send back the information stored in intent
        button.setOnClickListener(view -> {
            Intent intent1 = new Intent(Map.this, RegisterActivity.class);
            try {
                intent1.putExtra("Address", location);
                intent1.putExtra("emailIntent", emailIntent);
                intent1.putExtra("passwordIntent", passwordIntent);
                intent1.putExtra("NameIntent", NameIntent);
                intent1.putExtra("telnumIntent", telnumIntent);
                intent1.putExtra("CardIntent", CardIntent);
                intent1.putExtra("expIntent", expIntent);
                intent1.putExtra("CvvIntent", CvvIntent);
                intent1.putExtra("lat",lat);
                intent1.putExtra("lng",lng);
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

    /**
     * create a new thread for searching the location
     */
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
                                    Geocoder geocoder = new Geocoder(Map.this);
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
