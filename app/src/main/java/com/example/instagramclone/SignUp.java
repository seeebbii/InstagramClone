package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import org.w3c.dom.Text;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignUp, btnLogin;
    private EditText edtEnterEmail, edtUsername, edtEnterPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up");
        // INITIALIZING UI COMPONENTS
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogIn);

        edtEnterEmail = findViewById(R.id.edtEnterEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtEnterPassword = findViewById(R.id.edtEnterPassword);

        edtEnterPassword.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });


        if(ParseUser.getCurrentUser() != null){
            //ParseUser.logOut();
            transitionToSocialMediaActivity();
        }

        // ONCLICK LISTENERS
        btnSignUp.setOnClickListener(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    // SIGN UP CLICK LISTENER, FOR PROGRESS DIALOG
    @Override
    public void onClick(View v) {

        if(edtEnterEmail.getText().toString().equals("") ||
                edtUsername.getText().toString().equals("") ||
                edtEnterPassword.getText().toString().equals("")){
            Toasty.info(SignUp.this, "Email, Username, Password is required!", Toast.LENGTH_SHORT, true).show();
        }else{
            final ParseUser appUser = new ParseUser();
            appUser.setEmail(edtEnterEmail.getText().toString());
            appUser.setUsername(edtUsername.getText().toString());
            appUser.setPassword(edtEnterPassword.getText().toString());

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Signing Up " + edtUsername.getText().toString());
            progressDialog.show();

            appUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        Toasty.success(SignUp.this, appUser.get("username") +" is Signed Up successfully!", Toast.LENGTH_SHORT, true).show();
                        transitionToSocialMediaActivity();
                    } else{
                        Toasty.error(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                    }
                    progressDialog.dismiss();
                }
            });
        }

    }

    public void rootLayoutTapped(View view){
        try{
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    private void transitionToSocialMediaActivity(){
        Intent intent = new Intent(SignUp.this, SocialMediaActivity.class);
        startActivity(intent);
    }

}
