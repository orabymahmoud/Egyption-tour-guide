package com.oraby.egyptiantourguide.ui.main.resturants;

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
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.ui.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ResturantsFragmentsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;
    private MutableLiveData<Resource<List<Resturant>>> resourceMutableLiveData = new MutableLiveData<>();
    private static final String TAG = "ResturantsFragmentsView";
    private Context context;

    @Inject
    public ResturantsFragmentsViewModel(){}

    public void setContext(Context context) {
        this.context = context;
    }

    public void  getresturants() {
        final List<Resturant> resturants = new ArrayList<>();

        resourceMutableLiveData.setValue(Resource.loading((List<Resturant> ) null));

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

                resourceMutableLiveData.setValue(Resource.success(resturants));

                Log.e(TAG, "onDataChange: " + resturants.size() );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                resourceMutableLiveData.setValue(Resource.error(context.getString(R.string.err_rest)+"", (List<Resturant> ) null));
            }
        });

    }

    public MutableLiveData<Resource<List<Resturant>>> observepopularressturants() {
        return resourceMutableLiveData;
    }

}
