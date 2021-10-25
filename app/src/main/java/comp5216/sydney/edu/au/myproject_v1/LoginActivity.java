package comp5216.sydney.edu.au.myproject_v1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import comp5216.sydney.edu.au.myproject_v1.session.SessionManager;

/**
 * user can log in via this activity
 */
public class LoginActivity extends AppCompatActivity {
    EditText etLoginEmail;
    EditText etLoginPassword;
    TextView tvRegisterHere;
    Button btnLogin;
    FirebaseAuth mAuth;

    SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etLoginEmail = findViewById(R.id.editTextTextEmailAddress);
        etLoginPassword = findViewById(R.id.editTextTextPassword);
        btnLogin = findViewById(R.id.button);
        mAuth = FirebaseAuth.getInstance();

        // create sessionManager
        sessionManager=new SessionManager(getApplicationContext());

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

    }

    public void loginUser() {
        String email = etLoginEmail.getText().toString();
        String password = etLoginPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            etLoginEmail.setError("Email cannot be null");
            etLoginEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etLoginPassword.setError("password cannot be null");
            etLoginPassword.requestFocus();
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "User logged in successfully", Toast.LENGTH_SHORT).show();

                        //session starts here
                        String sEmail=etLoginEmail.getText().toString();
                        //String sPassword=etLoginPassword.getText().toString();
                        //store login in session
                        sessionManager.setLogin(true);
                        //store username in session
                        sessionManager.setUsername(sEmail);


                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        //intent.putExtra("emailIntent", etLoginEmail.getText().toString()); not need anymore we got session
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
