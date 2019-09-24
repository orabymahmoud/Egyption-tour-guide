package com.oraby.egyptiantourguide.ui.auth.vervication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.auth.login.LogIn;
import com.oraby.egyptiantourguide.ui.auth.signup.SignUp;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class VervicationActivity extends DaggerAppCompatActivity {

    private static final String TAG = "VervicationActivity";

    private EditText editText;
    private Button submit;
    private ProgressBar progressBar;
    private String verificationid;
    private FirebaseAuth mAuth;
    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vervication_mobile);

        editText = findViewById(R.id.vervication_code);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.Progress_par);

        setImage();
        String phonenumber = getIntent().getStringExtra("phonenumber");

        sendVerificationCode(phonenumber);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String code = editText.getText().toString().trim();

                if ((code.isEmpty() || code.length() < 6)){

                    editText.setError("Enter code...");
                    editText.requestFocus();
                    return;
                }

                try {
                    progressBar.setVisibility(View.VISIBLE);
                    verifyCode(code);
                }catch (Exception e){
                    Toast.makeText(VervicationActivity.this,getString(R.string.catch_vervication_error)+"" ,Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "onClick: " + e );
                }
            }
        });
    }

    private void verifyCode(String code){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationid, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            creatAcount();
                            progressBar.setVisibility(View.GONE);
                            String s = getIntent().getStringExtra("status");
                            if(s != null)
                            {
                                if(s.equals("forgetpassword"))
                                {
                                    Toast.makeText(VervicationActivity.this,getString(R.string.updated_password),Toast.LENGTH_SHORT).show();
                                }
                            }else {Toast.makeText(VervicationActivity.this,getString(R.string.Acount_Created),Toast.LENGTH_SHORT).show();}

                            Intent intent = new Intent(VervicationActivity.this , LogIn.class);
                            String mobile = getIntent().getStringExtra("mobile_");
                            String password = getIntent().getStringExtra("password");
                            intent.putExtra("phone" , mobile);
                            intent.putExtra("password" , password);
                            startActivity(intent);
                            finish();
                        } else {

                            progressBar.setVisibility(View.GONE);
                            Log.e(TAG, "onComplete: vervication error " );
                            Toast.makeText(VervicationActivity.this,getString(R.string.vervicatin_error),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
    }

    private void creatAcount() {

        String s = getIntent().getStringExtra("status");

        if(s != null)
        {
            if(s.equals("forgetpassword"))
            {
                String mobile = getIntent().getStringExtra("mobile_");
                String password = getIntent().getStringExtra("password");

                if(mobile != null && password != null)
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(mobile).child("Password");
                    myRef.setValue(password);
                }
            }
        }else{

            String mobile = getIntent().getStringExtra("mobile_");
            String name = getIntent().getStringExtra("name");
            String password = getIntent().getStringExtra("password");

            User user = new User(name ,mobile  ,password ,null , null , null);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("users");

            Map<String, Object>  objectMap = user.toMap();


            myRef.child(mobile).updateChildren(objectMap);
        }

    }

    private void sendVerificationCode(String number){
        Toast.makeText(VervicationActivity.this, getString(R.string.sending_code)+"",Toast.LENGTH_SHORT).show();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationid = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null){
                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VervicationActivity.this, e.getMessage(),Toast.LENGTH_LONG).show();
        }
    };

    public void setImage()
    {

        requestManager.load("https://firebasestorage.googleapis.com/v0/b/egyptian-tour-guide-a001b.appspot.com/o/background.jpg?alt=media&token=3111cfc9-d97e-4a1e-898a-075eb5969861") .into((ImageView)findViewById(R.id.backphoto));
    }
}
