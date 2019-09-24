package com.oraby.egyptiantourguide.ui.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.bumptech.glide.RequestManager;
import com.oraby.egyptiantourguide.AuthUser;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.MainActivity;
import com.oraby.egyptiantourguide.ui.auth.signup.SignUp;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class LogIn extends DaggerAppCompatActivity {

    @Inject
    LogInViewModel logInViewModel;
    ProgressBar progressBar;
    TextView username;
    TextView password;
    TextView forgetpassword;

    Button login;
    private static final String TAG = "LogIn";

    @Inject
    PrefManager prefManager;

    @Inject
    RequestManager requestManager;
    @Inject
    User user;

    @Override
    protected void onStart() {
        super.onStart();
        prefManager.setContext(this);
        if(!prefManager.isUserLogedOut())
        {
            startActivity(new Intent(LogIn.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logInViewModel.setContext(this);
        setContentView(R.layout.log_in);

        username = findViewById(R.id.username_log_in);
        password = findViewById(R.id.password_log_in);
        progressBar = findViewById(R.id.Progress_log_in);
        login = findViewById(R.id.button_log_in);


        forgetpassword = findViewById(R.id.forget_password_log_in);
        String mobile = getIntent().getStringExtra("mobile_");
        String password_ = getIntent().getStringExtra("password");
        setImage();
        if(mobile != null)
        {
            username.setText(mobile);
        }

        if(password_ != null)
        {
            password.setText(password_);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String username_ = username.getText().toString();
                String password_ = password.getText().toString();

                if(username_.isEmpty())
                {
                    username.setError(getString(R.string.invalid_mobile)+"");
                    username.setFocusable(true);
                    return;
                }

                if(password_.length() <=7)
                {
                    password.setError(getString(R.string.inavlid_password)+"");
                    password.setFocusable(true);
                    return;
                }

                logInViewModel.Login(username_ ,password_).observe(LogIn.this, new Observer<AuthResource<User>>() {
                    @Override
                    public void onChanged(AuthResource<User> userAuthResource) {
                        if(userAuthResource != null)
                        {
                            switch (userAuthResource.status)
                            {
                                case LOADING:{
                                    progressBar.setVisibility(View.VISIBLE);
                                    break;
                                }

                                case AUTHENTICATED:{
                                    prefManager.setContext(LogIn.this);
                                    prefManager.saveLoginDetails(userAuthResource.data);
                                    Log.e(TAG, "onChanged: " + userAuthResource.data.getMobile());
                                    AuthUser.setUser(userAuthResource.data);
                                    startActivity(new Intent(LogIn.this, MainActivity.class));
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                    break;
                                }

                                case ERROR:{
                                    Toast.makeText(getApplicationContext() ,userAuthResource.message ,Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    break;
                                }

                                case NOT_AUTHENTICATED:{
                                    progressBar.setVisibility(View.GONE);
                                    break;
                                }
                            }
                        }
                    }
                });
            }
        });


        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogIn.this , SignUp.class);
                intent.putExtra("status","forgetpassword");
                startActivity(intent);
            }
        });


    }

    public void setImage()
    {

        requestManager.load("https://firebasestorage.googleapis.com/v0/b/egyptian-tour-guide-a001b.appspot.com/o/background.jpg?alt=media&token=3111cfc9-d97e-4a1e-898a-075eb5969861") .into((ImageView)findViewById(R.id.backphoto));
    }

}
