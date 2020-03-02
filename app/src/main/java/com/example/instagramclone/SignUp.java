package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button saveBtn;
    private TextView txtName, punchSpeedTxt, punchPowerTxt, kickSpeedTxt, kickPowerTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        saveBtn = findViewById(R.id.saveObjectBtn);
        txtName = findViewById(R.id.nameText);
        punchSpeedTxt = findViewById(R.id.punchSpeedText);
        punchPowerTxt = findViewById(R.id.punchPowertext);
        kickSpeedTxt = findViewById(R.id.kickSpeedText);
        kickPowerTxt = findViewById(R.id.kickPowerText);

        saveBtn.setOnClickListener(SignUp.this);
//    public void helloWorldIsTapped(View view){
//
////        ParseObject boxer = new ParseObject("Boxer");
////        boxer.put("punch_speed", 200);
////        boxer.saveInBackground(new SaveCallback() {
////            @Override
////            public void done(ParseException e) {
////
////                if(e == null){
////                    Toast.makeText(SignUp.this, "boxer object is svaed successfully", Toast.LENGTH_LONG).show();
////                }
////
////            }
////        });
//    }

    }

    @Override
    public void onClick(View v) {

        try {

            final ParseObject kickBoxer = new ParseObject("KickBoxer");
            kickBoxer.put("name", txtName.getText().toString());
            kickBoxer.put("punchSpeed", punchSpeedTxt.getText().toString());
            kickBoxer.put("punchPower",punchPowerTxt.getText().toString());
            kickBoxer.put("kickSpeed", kickSpeedTxt.getText().toString());
            kickBoxer.put("kickPower", kickPowerTxt.getText().toString());
            kickBoxer.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toasty.success(SignUp.this, "Object "+ kickBoxer.get("name") + " is saved to the server.", Toast.LENGTH_SHORT, true).show();
                    } else {
                        Toasty.error(SignUp.this, "Error saving the OBJECT: " + kickBoxer.get("name"), Toast.LENGTH_SHORT, true).show();
                    }

                }
            });
        } catch (Exception e){
            Toasty.error(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
        }
    }

}
