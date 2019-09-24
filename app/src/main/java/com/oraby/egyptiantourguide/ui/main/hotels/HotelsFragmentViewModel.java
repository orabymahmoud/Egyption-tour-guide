package com.oraby.egyptiantourguide.ui.main.hotels;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.Resource;
import com.oraby.egyptiantourguide.ui.auth.login.AuthResource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HotelsFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;
    private MutableLiveData<Resource<List<Hotel>>> hotelsdata = new MutableLiveData<>();
    private Context context;
    private static final String TAG = "HotelsFragmentViewModel";

    @Inject
    public HotelsFragmentViewModel(){
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void  gethotels() {

        final List<Hotel> hotels = new ArrayList<>();

        hotelsdata.setValue(Resource.loading((List<Hotel> ) null));

        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseReference = mDatabase.child("hotels");

        Query myMostViewedPostsQuery = databaseReference
                .orderByChild("likes");

        myMostViewedPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.e(TAG, "onDataChange: " + dataSnapshot.toString() );


                for (DataSnapshot snapshot:dataSnapshot.getChildren()
                ) {
                    Hotel hotel = snapshot.getValue(Hotel.class);
                    hotels.add(hotel);
                }

                hotelsdata.setValue(Resource.success(hotels));

                Log.e(TAG, "onDataChange: " + hotels.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                hotelsdata.setValue(Resource.error(context.getString(R.string.err_hotels)+"", (List<Hotel> ) null));
            }
        });

    }

    public MutableLiveData<Resource<List<Hotel>>> observepopularhotels() {
        return hotelsdata;
    }
}
