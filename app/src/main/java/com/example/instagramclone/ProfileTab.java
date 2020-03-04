package com.example.instagramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import androidx.fragment.app.Fragment;
import es.dmoral.toasty.Toasty;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private EditText edtProfileName, edtBio, edtProfession, edtHobbies, edtFavSport;
    private Button btnUpdateInfo;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile_tab, container, false);
        edtProfileName = view.findViewById(R.id.edtProfileName);
        edtBio = view.findViewById(R.id.edtBio);
        edtProfession = view.findViewById(R.id.edtProfession);
        edtHobbies = view.findViewById(R.id.edtHobbies);
        edtFavSport = view.findViewById(R.id.edtFavSport);
        btnUpdateInfo = view.findViewById(R.id.btnUpdateInfo);

        final ParseUser parseUser = ParseUser.getCurrentUser();

        // IF ELSE FOR CHECKING THE VALUES ON THE SERVER ARE NULL OR NOT
        if (parseUser.get("profileName") == null) {
            edtProfileName.setText("");

        } else {
            edtProfileName.setText(parseUser.get("profileName").toString());

        }
        if(parseUser.get("profileBio") == null){
            edtBio.setText("");
        }else{
            edtBio.setText(parseUser.get("profileBio") + "");
        }
        if(parseUser.get("profileProfession") == null){
            edtProfession.setText("");
        }else{
            edtProfession.setText(parseUser.get("profileProfession") + "");
        }
        if(parseUser.get("profileHobbies") == null){
            edtHobbies.setText("");
        }else{
            edtHobbies.setText(parseUser.get("profileHobbies") + "");
        }
        if(parseUser.get("profileFavoriteSport") == null){
            edtFavSport.setText("");
        }else{
            edtFavSport.setText(parseUser.get("profileFavoriteSport") + "");
        }

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseUser.put("profileName", edtProfileName.getText().toString());
                parseUser.put("profileBio", edtBio.getText().toString());
                parseUser.put("profileProfession", edtProfession.getText().toString());
                parseUser.put("profileHobbies", edtHobbies.getText().toString());
                parseUser.put("profileFavoriteSport", edtFavSport.getText().toString());

                final ProgressDialog progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Updating Info, Please be patient...");
                progressDialog.show();

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toasty.success(getContext(), "Info updated!", Toast.LENGTH_SHORT, true).show();
                        }else{
                            Toasty.error(getContext(), e.getMessage(), Toast.LENGTH_SHORT, true).show();
                        }
                        progressDialog.dismiss();
                    }
                });
            }
        });

        return view;
    }
}
