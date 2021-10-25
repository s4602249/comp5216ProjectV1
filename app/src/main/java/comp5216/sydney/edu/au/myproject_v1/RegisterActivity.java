package comp5216.sydney.edu.au.myproject_v1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import comp5216.sydney.edu.au.myproject_v1.map.Map;
import comp5216.sydney.edu.au.myproject_v1.model.User;

/**
 * user can register through this activity
 */
public class RegisterActivity extends AppCompatActivity {
    EditText etRegEmail;
    EditText etTePassword;
    Button btnRegister;
    FirebaseAuth mAuth;

    EditText nameInput;
    EditText emailInput;
    EditText passwordInput;
    EditText telInput;
    EditText addressInput;
    EditText CardInput;
    EditText expireInput;
    EditText cvvInput;
    DatabaseReference userRef;

    String Address;
    ImageButton curloc;
    Intent intent;
    double lat;
    double lng;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etRegEmail = findViewById(R.id.field2);
        etTePassword = findViewById(R.id.field3);
        btnRegister = findViewById(R.id.save);
        nameInput = findViewById(R.id.field1);
        emailInput = findViewById(R.id.field2);
        passwordInput = findViewById(R.id.field3);
        telInput = findViewById(R.id.field4);
        addressInput = findViewById(R.id.field5);
        CardInput = findViewById(R.id.field6);
        expireInput = findViewById(R.id.field7);
        cvvInput = findViewById(R.id.field8);
        curloc = findViewById(R.id.findLocationn);

        //receive the information from Map.class
        Intent intent1 = getIntent();
        Bundle bundle = intent1.getExtras();
        if (bundle != null) {
            String data = bundle.getString("Address");
            System.out.println("--------------------------------" + data);
            addressInput.setText(data);
            emailInput.setText(bundle.getString("emailIntent"));
            passwordInput.setText(bundle.getString("passwordIntent"));
            nameInput.setText(bundle.getString("NameIntent"));
            telInput.setText(bundle.getString("telnumIntent"));
            CardInput.setText(bundle.getString("CardIntent"));
            expireInput.setText(bundle.getString("expIntent"));
            cvvInput.setText(bundle.getString("CvvIntent"));
            lat=bundle.getDouble("lat");
            lng=bundle.getDouble("lng");
        }


        mAuth = FirebaseAuth.getInstance();

        curloc.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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
                                    //send information to Map.class
                                    intent = new Intent(RegisterActivity.this, Map.class);
                                    intent.putExtra("emailIntent", etRegEmail.getText().toString());
                                    intent.putExtra("passwordIntent",etTePassword.getText().toString());
                                    intent.putExtra("NameIntent",nameInput.getText().toString());
                                    intent.putExtra("telnumIntent",telInput.getText().toString());
                                    intent.putExtra("AddressIntent",addressInput.getText().toString());
                                    intent.putExtra("CardIntent",CardInput.getText().toString());
                                    intent.putExtra("expIntent",expireInput.getText().toString());
                                    intent.putExtra("CvvIntent",cvvInput.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            });
            builder.create().show();
        });
        btnRegister.setOnClickListener(view -> {
            createUser();
        });
    }

    //create new user in database
    private void createUser() {
        mAuth = FirebaseAuth.getInstance();
        String email = etRegEmail.getText().toString();
        String password = etTePassword.getText().toString();
        String Name = nameInput.getText().toString();
        String telnum = telInput.getText().toString();

        Address = addressInput.getText().toString();

        String Card = CardInput.getText().toString();
        String exp = expireInput.getText().toString();
        String Cvv = cvvInput.getText().toString();
        //field validation
        if (TextUtils.isEmpty(Name)) {
            nameInput.setError("user name cannot be null");
            nameInput.requestFocus();
        } else if (TextUtils.isEmpty(email)) {
            etRegEmail.setError("Email cannot be null");
            etRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etTePassword.setError("password cannot be null");
            etTePassword.requestFocus();
        } else if (TextUtils.isEmpty(telnum)) {
            telInput.setError("tel number cannot be null");
            telInput.requestFocus();
        } else if (TextUtils.isEmpty(Address)) {
            addressInput.setError("Address cannot be null");
            addressInput.requestFocus();
        } else if (TextUtils.isEmpty(Card)) {
            CardInput.setError("Card number cannot be null");
            CardInput.requestFocus();
        } else if (TextUtils.isEmpty(exp)) {
            expireInput.setError("expire date number cannot be null");
            CardInput.requestFocus();
        } else if (TextUtils.isEmpty(Cvv)) {
            cvvInput.setError("cvv number cannot be null");
            CardInput.requestFocus();
        } else {

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(nameInput.getText().toString());
                        String name1 = nameInput.getText().toString();
                        String email1 = emailInput.getText().toString();
                        String password1 = passwordInput.getText().toString();
                        String tel1 = telInput.getText().toString();
                        String address1 = addressInput.getText().toString();
                        String card1 = CardInput.getText().toString();
                        String expire1 = expireInput.getText().toString();
                        String cvv1 = cvvInput.getText().toString();

                        User user = new User( name1,  email1,  password1,  tel1,  address1,  card1,
                                expire1,  cvv1, lat, lng) ;
                        userRef.setValue(user);
                       // DatabaseReference databaseReference = userRef.child("users");
                       // HashMap<String, user> users = new HashMap<>();
                        //users.put("alanisawesome", new user("abc", "abc@qq.com","111111","123","123",
                         //       "123","12","122"));
                        //databaseReference.setValue(users);
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
