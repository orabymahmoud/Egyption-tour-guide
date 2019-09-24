package com.oraby.egyptiantourguide.ui.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.MainActivity;
import com.oraby.egyptiantourguide.ui.auth.login.LogIn;
import com.oraby.egyptiantourguide.ui.auth.vervication.VervicationActivity;
import com.oraby.egyptiantourguide.util.CountryData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class SignUp extends DaggerAppCompatActivity {

    private static final String TAG = "SignUp";
    private EditText name;
    private EditText mobile;
    private EditText password;
    private Button signup;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;

    @Inject
    SignUpViewModel signUpViewModel;

    private Spinner spinner;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        setImage();
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        name = findViewById(R.id.name_sign_up);
        mobile = findViewById(R.id.mobile_sign_up);
        password = findViewById(R.id.password_sign_up);
        progressBar = findViewById(R.id.Progress_sign_up);

        linearLayout = findViewById(R.id.name_sign_up_layout);

        signup = findViewById(R.id.button_sign_up);

        final String s = getIntent().getStringExtra("status");
        if(s != null){

            if(s.equals("forgetpassword")) {

                linearLayout.setVisibility(View.GONE);
                signup.setText(getString(R.string.remember_me));
            }
        }

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              if(s != null){

                  if(s.equals("forgetpassword"))
                  {

                      String mobile_ = mobile.getText().toString().trim();
                      String password_ = password.getText().toString().trim();

                      Intent intent = new Intent(SignUp.this, VervicationActivity.class);
                      String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                      String phonenumber = "+" + code + mobile_;
                      intent.putExtra("mobile_", mobile_);
                      intent.putExtra("password", password_);
                      intent.putExtra("phonenumber", phonenumber);
                      intent.putExtra("status","forgetpassword");

                      startActivity(intent);
                  }


              }else{
                  String name_ = name.getText().toString().trim();
                  final String mobile_ = mobile.getText().toString().trim();
                  String password_ = password.getText().toString().trim();


                  if (name_.isEmpty() && name_.length() <= 7) {
                      name.setError(getString(R.string.invalid_name)+"");
                      name.setFocusable(true);
                      return;

                  }

                  if (mobile_.isEmpty()) {
                      mobile.setError(getString(R.string.invalid_mobile)+"");
                      mobile.setFocusable(true);
                      return;
                  }

                  if (password_.length() <= 7) {
                      password.setError(getString(R.string.inavlid_password)+"");
                      password.setFocusable(true);
                      return;
                  }


                  progressBar.setVisibility(View.GONE);
                  Intent intent = new Intent(SignUp.this, VervicationActivity.class);
                  String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];
                  String phonenumber = "+" + code + mobile_;
                  intent.putExtra("name", name_);
                  intent.putExtra("mobile_", mobile_);
                  intent.putExtra("password", password_);
                  intent.putExtra("phonenumber", phonenumber);

                  startActivity(intent);
              }

            }
            });

    }



    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void setImage()
    {

        requestManager.load("https://firebasestorage.googleapis.com/v0/b/egyptian-tour-guide-a001b.appspot.com/o/background.jpg?alt=media&token=3111cfc9-d97e-4a1e-898a-075eb5969861") .into((ImageView)findViewById(R.id.backphoto));
    }
}
