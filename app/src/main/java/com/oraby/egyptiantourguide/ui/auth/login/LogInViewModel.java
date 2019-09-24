package com.oraby.egyptiantourguide.ui.auth.login;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.auth.SplashScrean;

import javax.inject.Inject;

public class LogInViewModel extends ViewModel {


    private static final String TAG = "LogInViewModel";
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;
    private MutableLiveData<AuthResource<User>> userLiveData = new MutableLiveData<>();
    private Context context;

    @Inject
    public LogInViewModel(){}
    public void setContext(Context context) {
        this.context = context;
    }

    public LiveData<AuthResource<User>> Login(String mobile , final String password)
    {
        userLiveData.setValue(AuthResource.loading((User) (null)));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseReference = mDatabase.child("users");

        Query myMostViewedPostsQuery = databaseReference
                .orderByChild("Mobile").equalTo(mobile);

        myMostViewedPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user11 = dataSnapshot.getValue(User.class);;
                if(user11 == null)
                {
                    userLiveData.setValue(AuthResource.error(context.getString(R.string.mobile_is_not_found)+"" , (User) null));
                }
                Log.e(TAG, "onDataChange: " + dataSnapshot.toString());
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    user11 = datas.getValue(User.class);

                    if(user11!= null)
                    {
                        if(user11.getPassword().equals(password))
                        {
                            userLiveData.setValue(AuthResource.authenticated(user11));
                        }else{

                            userLiveData.setValue(AuthResource.error(context.getString(R.string.password_is_wrong)+"", (User) null));
                        }

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                userLiveData.setValue(AuthResource.error("DatabaseError :" + databaseError.getMessage() , (User) null));
            }
        });

        return userLiveData;
    }


}
