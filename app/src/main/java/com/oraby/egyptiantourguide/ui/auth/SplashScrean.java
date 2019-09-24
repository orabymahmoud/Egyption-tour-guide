package com.oraby.egyptiantourguide.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.RequestManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.MainActivity;
import com.oraby.egyptiantourguide.ui.auth.login.LogIn;
import com.oraby.egyptiantourguide.ui.auth.signup.SignUp;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

import static io.reactivex.internal.util.NotificationLite.getValue;

public class SplashScrean extends DaggerAppCompatActivity {

    Button login;
    TextView signup;
    ImageView imageView;

    @Inject
    RequestManager requestManager;

    @Inject
    PrefManager prefManager;

    private static final String TAG = "SplashScrean";

    @Override
    protected void onStart() {
        super.onStart();
        prefManager.setContext(this);
        if(!prefManager.isUserLogedOut())
        {
            startActivity(new Intent(SplashScrean.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        setImage();

        login = findViewById(R.id.log_in);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(login.getContext() , LogIn.class));
            }
        });

        signup = findViewById(R.id.create_new_account);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(signup.getContext() , SignUp.class));

            }
        });

    }

    public void setImage()
    {

        requestManager.load("https://firebasestorage.googleapis.com/v0/b/egyptian-tour-guide-a001b.appspot.com/o/artifacts%2Fpyramids.jpg?alt=media&token=a9a24af8-65ca-4175-ac64-65453b0243f6") .into((ImageView)findViewById(R.id.welcomephoto));
    }
}
