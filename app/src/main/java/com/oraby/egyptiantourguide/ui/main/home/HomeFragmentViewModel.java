package com.oraby.egyptiantourguide.ui.main.home;

import android.content.Context;
import android.util.Log;

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
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.Resource;
import com.oraby.egyptiantourguide.ui.auth.login.AuthResource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;
    private MutableLiveData<AuthResource<User>> userLiveData = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Artifact>>> popularartifacts = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Hotel>>> popularhotels = new MutableLiveData<>();
    private MutableLiveData<Resource<List<Resturant>>> popularresturants = new MutableLiveData<>();
    private Context context;
    private static final String TAG = "HomeFragmentViewModel";

    @Inject
    public HomeFragmentViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void getPopularartifacts()
    {
        final List<Artifact> artifacts = new ArrayList<>();
        popularartifacts = new MutableLiveData<>();

        popularartifacts.setValue(Resource.loading((List<Artifact> ) null));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseReference = mDatabase.child("artifacts");

        Query myMostViewedPostsQuery = databaseReference
                .orderByChild("likes").limitToFirst(10);

        myMostViewedPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: " + dataSnapshot.toString() );


                for (DataSnapshot snapshot:dataSnapshot.getChildren()
                ) {
                    Artifact artifact = snapshot.getValue(Artifact.class);
                    artifacts.add(artifact);
                }

                    popularartifacts.setValue(Resource.success(artifacts));

                Log.e(TAG, "onDataChange: " + artifacts.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularartifacts.setValue(Resource.error(context.getString(R.string.err_arts)+"", (List<Artifact> ) null));
            }
        });

    }

    public MutableLiveData<Resource<List<Artifact>>>  observepopularartifacts (){
        return popularartifacts;
    }

    public void  getPopularhotels() {

        final List<Hotel> hotels = new ArrayList<>();
        popularhotels = new MutableLiveData<>();

        popularhotels.setValue(Resource.loading((List<Hotel> ) null));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseReference = mDatabase.child("hotels");

        Query myMostViewedPostsQuery = databaseReference
                .orderByChild("likes").limitToFirst(10);

        myMostViewedPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: " + dataSnapshot.toString() );


                for (DataSnapshot snapshot:dataSnapshot.getChildren()
                ) {
                    Hotel hotel = snapshot.getValue(Hotel.class);
                    hotels.add(hotel);
                }

                popularhotels.setValue(Resource.success(hotels));

                Log.e(TAG, "onDataChange: " + hotels.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularhotels.setValue(Resource.error(context.getString(R.string.err_hotels)+"", (List<Hotel> ) null));
            }
        });

    }

    public MutableLiveData<Resource<List<Hotel>>> observepopularhotels() {
        return popularhotels;
    }

    public void  getPopularresturants() {
        final List<Resturant> resturants = new ArrayList<>();
        popularresturants = new MutableLiveData<>();

        popularresturants.setValue(Resource.loading((List<Resturant> ) null));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseReference = mDatabase.child("resturants");

        Query myMostViewedPostsQuery = databaseReference
                .orderByChild("likes").limitToFirst(10);

        myMostViewedPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: " + dataSnapshot.toString() );


                for (DataSnapshot snapshot:dataSnapshot.getChildren()
                ) {
                    Resturant resturant = snapshot.getValue(Resturant.class);
                    resturants.add(resturant);
                }

                popularresturants.setValue(Resource.success(resturants));

                Log.e(TAG, "onDataChange: " + resturants.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                popularresturants.setValue(Resource.error(context.getString(R.string.err_rest)+"", (List<Resturant> ) null));
            }
        });

    }

    public MutableLiveData<Resource<List<Resturant>>> observepopularressturants() {
        return popularresturants;
    }

    public LiveData<AuthResource<User>> getauthdata(String mobile , final String password)
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

                            userLiveData.setValue(AuthResource.error(context.getString(R.string.password_is_wrong)+"" , (User) null));
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
