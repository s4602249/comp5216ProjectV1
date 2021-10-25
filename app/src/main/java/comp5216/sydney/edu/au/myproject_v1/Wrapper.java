package comp5216.sydney.edu.au.myproject_v1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Wrapper class, user can choose log in and sign up
 */
public class Wrapper extends AppCompatActivity {
    Button login;
    Button signup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        login.setOnClickListener(view -> {
            startActivity(new Intent(Wrapper.this,LoginActivity.class));
            finish();
        });
        signup.setOnClickListener(view-> {
            startActivity(new Intent(Wrapper.this,RegisterActivity.class));
            finish();
        });
    }
}

