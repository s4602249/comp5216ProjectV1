package comp5216.sydney.edu.au.myproject_v1.shoppingRequest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import comp5216.sydney.edu.au.myproject_v1.History;

import comp5216.sydney.edu.au.myproject_v1.MainActivity;
import comp5216.sydney.edu.au.myproject_v1.Profile;
import comp5216.sydney.edu.au.myproject_v1.R;

import comp5216.sydney.edu.au.myproject_v1.ShoppingDelivery;

import comp5216.sydney.edu.au.myproject_v1.map.MapRequestItem;
import comp5216.sydney.edu.au.myproject_v1.model.Request;


public class RequestYourItem extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference reqRef;
    //DatabaseReference reference;
    EditText textTitle;
    EditText itemName1;
    EditText itemName2;
    EditText itemName3;
    EditText price1;
    EditText price2;
    EditText price3;
    EditText address;
    EditText contactNum;
    EditText time;
    Intent intent;
    ImageButton curloc;
    ImageButton back;
    String username;
    Button confirm;
    TextView TotalPrice;
    TextView Depsit;

    double lat;
    double lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_your_item);

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
        textTitle = findViewById(R.id.Text1);
        itemName1 = findViewById(R.id.Text2);
        itemName2 = findViewById(R.id.Text4);
        itemName3 = findViewById(R.id.Text6);
        price1 = findViewById(R.id.Text3);
        price2 = findViewById(R.id.Text5);
        price3 = findViewById(R.id.Text7);
        TotalPrice = findViewById(R.id.totalPriceCan);
        Depsit = findViewById(R.id.timeCan);
        //add textChangedListener
        back = findViewById(R.id.back1);

        // go back to main page
        back.setOnClickListener(view -> {
            Intent intent = new Intent(RequestYourItem.this, MainActivity.class);
            startActivity(intent);
            finish();
        });


        price1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (price1.getText().toString().equals("")) {
                    price1.setText("0");
                }
                if (price2.getText().toString().equals("")) {
                    price2.setText("0");
                }
                if (price3.getText().toString().equals("")) {
                    price3.setText("0");
                }

                if (!price1.getText().toString().equals("") && !price2.getText().toString().equals("") &&
                        !price3.getText().toString().equals("") && isNumeric(price1.getText().toString()) && isNumeric(price2.getText().toString())
                        && isNumeric(price3.getText().toString())) {

                    TotalPrice.setText(String.valueOf(Double.valueOf(price1.getText().toString()) + Double.valueOf(price2.getText().toString())
                            + Double.valueOf(price3.getText().toString())));


                }
            }
        });
        price2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (price1.getText().toString().equals("")) {
                    price1.setText("0");
                }
                if (price2.getText().toString().equals("")) {
                    price2.setText("0");
                }
                if (price3.getText().toString().equals("")) {
                    price3.setText("0");
                }

                if (!price1.getText().toString().equals("") && !price2.getText().toString().equals("") &&
                        !price3.getText().toString().equals("") && isNumeric(price1.getText().toString()) && isNumeric(price2.getText().toString())
                        && isNumeric(price3.getText().toString())) {

                    TotalPrice.setText(String.valueOf(Double.valueOf(price1.getText().toString()) + Double.valueOf(price2.getText().toString())
                            + Double.valueOf(price3.getText().toString())));

                }


            }
        });
        price3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (price1.getText().toString().equals("")) {
                    price1.setText("0");
                }
                if (price2.getText().toString().equals("")) {
                    price2.setText("0");
                }
                if (price3.getText().toString().equals("")) {
                    price3.setText("0");
                }


                if (!price1.getText().toString().equals("") && !price2.getText().toString().equals("") &&
                        !price3.getText().toString().equals("") && isNumeric(price1.getText().toString()) && isNumeric(price2.getText().toString())
                        && isNumeric(price3.getText().toString())) {

                    TotalPrice.setText(String.valueOf(Double.valueOf(price1.getText().toString()) + Double.valueOf(price2.getText().toString())
                            + Double.valueOf(price3.getText().toString())));

                }


            }
        });

        address = findViewById(R.id.Text8);
        contactNum = findViewById(R.id.Text9);

        time = findViewById(R.id.Text10);
        time.setInputType(InputType.TYPE_NULL);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimeDialog(time);
            }
        });

        curloc = findViewById(R.id.findLocationn3);
        confirm = findViewById(R.id.cancelReq);

        mAuth = FirebaseAuth.getInstance();
        curloc.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(RequestYourItem.this);
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

                                    intent = new Intent(RequestYourItem.this, MapRequestItem.class);
                                    intent.putExtra("usernameIntent", username);
                                    intent.putExtra("textTitleIntent", textTitle.getText().toString());
                                    intent.putExtra("itemName1Intent", itemName1.getText().toString());
                                    intent.putExtra("itemName2Intent", itemName2.getText().toString());
                                    intent.putExtra("itemName3Intent", itemName3.getText().toString());
                                    intent.putExtra("price1Intent", price1.getText().toString());
                                    intent.putExtra("price2Intent", price2.getText().toString());
                                    intent.putExtra("price3Intent", price3.getText().toString());
                                    intent.putExtra("addressIntent", address.getText().toString());
                                    intent.putExtra("contactNumIntent", contactNum.getText().toString());
                                    intent.putExtra("timeIntent", time.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            });
            builder.create().show();
        });
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        if (bundle != null) {
            String data = bundle.getString("Address");
            System.out.println("--------------------------------" + data);
            address.setText(data);
            textTitle.setText(bundle.getString("textTitleIntent"));
            itemName1.setText(bundle.getString("itemName1Intent"));
            itemName2.setText(bundle.getString("itemName2Intent"));
            itemName3.setText(bundle.getString("itemName3Intent"));
            price1.setText(bundle.getString("price1Intent"));
            price2.setText(bundle.getString("price2Intent"));
            price3.setText(bundle.getString("price3Intent"));
            contactNum.setText(bundle.getString("contactNumIntent"));
            time.setText(bundle.getString("timeIntent"));
            lat = bundle.getDouble("lat");
            lng = bundle.getDouble("lng");
            username = bundle.getString("username");
            System.out.println("this is user name from map " + username);
        }

        // = new SessionManager(getApplicationContext());
        //Get username from session
        //String sEmail = sessionManager.getUsername();
        Intent intentFromMainActivity = getIntent();
        Bundle bundleMainActivity = intentFromMainActivity.getExtras();
        if (bundle != null) {
            String data = bundleMainActivity.getString("username");
            username = data;
            System.out.println("this is username---------" + username);
            confirm.setOnClickListener(view -> {

                System.out.println("this is username inside---------" + username);
                createReq();
            });
        }

        setInfo();

    }

    //Create request under user name
    private void createReq() {

        mAuth = FirebaseAuth.getInstance();
        String TitleSet = textTitle.getText().toString();
        String itemName1Set = itemName1.getText().toString();
        //String itemName2Set = itemName2.getText().toString();
        //String itemName3Set = itemName3.getText().toString();

        String price1Set = price1.getText().toString();
        String price2Set = price2.getText().toString();
        String price3Set = price3.getText().toString();
        String telnumSet = contactNum.getText().toString();
        String addressSet = address.getText().toString();
        String timeSet = time.getText().toString();

        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference("RequestItem");

       //check if title is in database, title cannot be the same
         reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot datas : snapshot.getChildren()) {
                    //datas.child("email")是一个键值对

                    if (datas.getKey().equals(username+TitleSet)) {

                        startActivity(new Intent(RequestYourItem.this, MainActivity.class));
                        //Toast.makeText(RequestYourItem.this, "You just update your request, since title is the same", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        //input validation check
        if (TextUtils.isEmpty(TitleSet)) {
            textTitle.setError("Title cannot be null");
            textTitle.requestFocus();
        } else if (TextUtils.isEmpty(itemName1Set)) {
            itemName1.setError("At least one item name cannot be null");
            itemName1.requestFocus();

        } else if (TextUtils.isEmpty(price1Set)) {
            price1.setError("At least one item price cannot be null");
            price1.requestFocus();

        } else if (TextUtils.isEmpty(timeSet)) {
            time.setError("Arrival time cannot be null");
            time.requestFocus();
        } else if (TextUtils.isEmpty(addressSet)) {
            address.setError("address cannot be null");
            address.requestFocus();
        } else if (TextUtils.isEmpty(telnumSet)) {
            contactNum.setError("phone number cannot be null");
            contactNum.requestFocus();
        } else if (!isNumeric(price1Set) || Double.parseDouble(price1Set) < 0) {
            price1.setError("price must be positive number");
            price1.requestFocus();
        } else if (!isNumeric(price2Set) || Double.parseDouble(price2Set) < 0) {
            price2.setError("price must be positive number");
            price2.requestFocus();
        } else if (!isNumeric(price3Set) || Double.parseDouble(price3Set) < 0) {
            price3.setError("price must be positive number");
            price3.requestFocus();
        } else {
            String textTitleUpload = textTitle.getText().toString();
            String itemName1Upload = itemName1.getText().toString();
            String itemName2Upload = itemName2.getText().toString();
            String itemName3Upload = itemName3.getText().toString();
            String price1Upload = price1.getText().toString();
            String price2Upload = price2.getText().toString();
            String price3Upload = price3.getText().toString();
            String addressUpload = address.getText().toString();
            String contactNumUpload = contactNum.getText().toString();
            String timeUpload = time.getText().toString();
            // String dtStart = "2010-10-15T09:27:37Z";

            SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");

//            if(!isValidDate(timeUpload)){
//                Toast.makeText(RequestYourItem.this,
//                        "Must follow the date format", Toast.LENGTH_SHORT).show();
//            }

            try {
                System.out.println(timeUpload);
                System.out.println("hehe");
                Date dueTime = format.parse(timeUpload);
                System.out.println(dueTime);

                Date createTime = new Date();
                Date nowTime = new Date();
                //System.out.println(dateFormat.format(date));
                /*if(!timeUpload.equals("yyyy-MM-dd HH:mm")){
                    Toast.makeText(RequestYourItem.this,
                            "Must follow the date format", Toast.LENGTH_SHORT).show();
                }*/



                reqRef = FirebaseDatabase.getInstance().getReference().child("RequestItem").child(username + textTitleUpload);
                String status="";
                if(dueTime.getTime()<nowTime.getTime()){ //less than now
                    status="Overdue";
                }
                else{
                    status="Posted";
                }
                Request request = new Request(textTitleUpload, username, "", addressUpload,
                        lat, lng, createTime.getTime(), dueTime.getTime(), contactNumUpload,
                        "", itemName1Upload, itemName2Upload,
                        itemName3Upload, price1Upload, price2Upload, price3Upload,
                        status, (Double.parseDouble(price1Upload) + Double.parseDouble(price2Upload) + Double.parseDouble(price3Upload) + "")
                );

           /* Request request = new Request(username, textTitleUpload, itemName1Upload, itemName2Upload, itemName3Upload,
                    price1Upload, price2Upload, price3Upload, timeUpload, addressUpload, contactNumUpload, "Wating");*/
                reqRef.setValue(request);

                startActivity(new Intent(RequestYourItem.this, MainActivity.class));
                Toast.makeText(RequestYourItem.this, "Data changed", Toast.LENGTH_SHORT).show();
                finish();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    //check the num if it is integer
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;

    }

//    public static boolean isValidDate(String inDate) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        dateFormat.setLenient(false);
//        try {
//            dateFormat.parse(inDate.trim());
//        } catch (ParseException pe) {
//            return false;
//        }
//        return true;
//    }

    private void showDateTimeDialog(final EditText date_time_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);

                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd HH:mm");
                        //save date string in to @deadline, which used for sending back to the main activity
                        String deadline = simpleDateFormat.format(calendar.getTime());
                        time.setText(deadline);

                        System.out.println(deadline);
                        date_time_in.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                };

                new TimePickerDialog(RequestYourItem.this,timeSetListener,calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
            }
        };

        new DatePickerDialog(RequestYourItem.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();

    }

    private void setInfo(){
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datas : snapshot.getChildren()) {
                    System.out.println(datas.child("username").getValue().toString());
                    if(datas.child("username").getValue().toString().equals(username)){
                        //datas.child("email")是一个键值对
                        System.out.println("????");
                        System.out.println(datas.child("phoneNumber").getValue().toString());
                        contactNum.setText(datas.child("phoneNumber").getValue().toString());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}