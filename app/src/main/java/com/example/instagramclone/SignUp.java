package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

import android.os.Bundle;
import android.view.View;
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
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.List;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private Button saveBtn;
    private EditText txtName, punchSpeedTxt, punchPowerTxt, kickSpeedTxt, kickPowerTxt;
    private TextView txtGetData;
    private Button btnGetAllData;
    private String allKickBoxers;
    private TextView displayAllData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        saveBtn = findViewById(R.id.saveObjectBtn);
        saveBtn.setOnClickListener(SignUp.this);

        txtName = findViewById(R.id.nameText);
        punchSpeedTxt = findViewById(R.id.punchSpeedText);
        punchPowerTxt = findViewById(R.id.punchPowertext);
        kickSpeedTxt = findViewById(R.id.kickSpeedText);
        kickPowerTxt = findViewById(R.id.kickPowerText);
        txtGetData = findViewById(R.id.txtGetData);
        btnGetAllData = findViewById(R.id.btnGetAllData);
        displayAllData = findViewById(R.id.allDataTxt);

        txtGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> parseQuery = ParseQuery.getQuery("KickBoxer");
                parseQuery.getInBackground("nNu5o67C34", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if(object != null && e == null){
                            txtGetData.setText(object.get("name") + " - " + "Punch Power: " + object.get("punchPower"));
                        }

                    }
                });
            }
        });

        btnGetAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allKickBoxers = "";

                ParseQuery<ParseObject> queryAll = ParseQuery.getQuery("KickBoxer");
                queryAll.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if(e == null){
                            if(objects.size() > 0){
                                for(ParseObject kickBoxer : objects){
                                    allKickBoxers = allKickBoxers + kickBoxer.get("name") + " " + kickBoxer.get("punchSpeed")+ " "+ kickBoxer.get("punchPower")+ " "
                                            + kickBoxer.get("kickSpeed") + " " + kickBoxer.get("kickPower") + "\n";
                                    displayAllData.setText(allKickBoxers);
                                }
                                //Toasty.success(SignUp.this, allKickBoxers, Toast.LENGTH_SHORT, true).show();
                            } else{
                                Toasty.error(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT, true).show();
                            }
                        }
                    }
                });
            }
        });
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
