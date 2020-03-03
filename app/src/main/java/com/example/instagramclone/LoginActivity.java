package com.example.instagramclone;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignUp, btnLogin;
    private EditText  edtLoginEmail, edtLoginPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");
        // INITIALIZING UI COMPONENTS
        edtLoginEmail = findViewById(R.id.edtLoginEmail);
        edtLoginPassword = findViewById(R.id.edtLoginPassword);
        btnSignUp = findViewById(R.id.btnSignUpLoginActivity);
        btnLogin = findViewById(R.id.btnLoginActivity);
        btnLogin.setOnClickListener(this);

        edtLoginPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnLogin);
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser() != null){
            ParseUser.logOut();
        }


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(edtLoginEmail.getText().toString().equals("") || edtLoginPassword.getText().toString().equals("")){
            Toasty.info(LoginActivity.this, "Email and Password is required!", Toast.LENGTH_SHORT, true).show();
        }
        else{
            ParseUser.logInInBackground(edtLoginEmail.getText().toString(), edtLoginPassword.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if(user != null && e == null){
                        Toasty.success(LoginActivity.this, user.get("username") +" is Logged in successfully!", Toast.LENGTH_SHORT, true).show();
                        transitionToSocialMediaActivity();
                    } else{
                        Toasty.error(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                }
            });
        }
    }

    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}
