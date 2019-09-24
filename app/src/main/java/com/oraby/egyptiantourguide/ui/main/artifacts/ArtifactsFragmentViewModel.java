package com.oraby.egyptiantourguide.ui.main.artifacts;

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
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.ui.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ArtifactsFragmentViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private static final String TAG = "ArtifactsFragmentViewMo";
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;
    private MutableLiveData<Resource<List<Artifact>>> popularartifacts = new MutableLiveData<>();
    private Context context;

    @Inject
    public ArtifactsFragmentViewModel(){}

    public void setContext(Context context) {
        this.context = context;
    }

    public void getartifacts()
    {
        final List<Artifact> artifacts = new ArrayList<>();

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
                popularartifacts.setValue(Resource.error(context.getString(R.string.error_art)+"", (List<Artifact> ) null));
            }
        });

    }

    public MutableLiveData<Resource<List<Artifact>>> observepopularartifacts (){
        return popularartifacts;
    }
}
