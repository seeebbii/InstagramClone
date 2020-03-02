package com.example.instagramclone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class SignUpLoginActivity extends AppCompatActivity {

    private EditText edtUsernameSignUp, edtPasswordSignUp, edtUsernameLogin, edtPasswordLogin;
    private Button btnSignUp, btnLogin;

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setContentView(R.layout.signup_login_activity);

        // INITIALIZING ALL THE UI COMPONENTS
        edtUsernameSignUp = findViewById(R.id.edtUsernameSignup);
        edtPasswordSignUp = findViewById(R.id.edtPasswordSignup);
        btnSignUp = findViewById(R.id.btnSignUp);

        edtUsernameLogin = findViewById(R.id.edtUsernameLogin);
        edtPasswordLogin = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLogin);

        // ONCLICK LISTENERS
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseUser appUser = new ParseUser();
                appUser.setUsername(edtUsernameSignUp.getText().toString());
                appUser.setPassword(edtPasswordSignUp.getText().toString());

                appUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toasty.success(SignUpLoginActivity.this, appUser.get("username") +" is Signed Up successfully!", Toast.LENGTH_SHORT, true).show();
                        } else{
                            Toasty.error(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logInInBackground(edtUsernameLogin.getText().toString(), edtPasswordLogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null && e == null){
                            Toasty.success(SignUpLoginActivity.this, user.get("username") +" is Logged in successfully!", Toast.LENGTH_SHORT, true).show();
                        }else{
                            Toasty.error(SignUpLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                    }
                });
            }
        });



    }
}
