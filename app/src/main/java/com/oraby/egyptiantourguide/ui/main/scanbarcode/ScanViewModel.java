package com.oraby.egyptiantourguide.ui.main.scanbarcode;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.ui.Resource;

import java.util.List;

import javax.inject.Inject;

public class ScanViewModel  extends ViewModel {

    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;
    private MutableLiveData<Resource<Artifact>> artifact_ = new MutableLiveData<>();
    private Context context;
    private static final String TAG = "ScanViewModel";

    @Inject
    public ScanViewModel(){}

    public void setContext(Context context) {
        this.context = context;
    }

    public void getArtifact(String i){

        if(i != null)
        {
            artifact_.setValue(Resource.loading((Artifact) null));
            mDatabase = FirebaseDatabase.getInstance().getReference();

            databaseReference = mDatabase.child("artifacts").child(i);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e(TAG, "onDataChange: " + dataSnapshot.toString() );
                    if(dataSnapshot != null)
                    {
                        Artifact artifact = dataSnapshot.getValue(Artifact.class);
                        artifact_.setValue(Resource.success(artifact));
                    }else{
                        artifact_.setValue(Resource.error( context.getString(R.string.barcode_error_message), (Artifact) null));
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    artifact_.setValue(Resource.error(databaseError + context.getString(R.string.barcode_error_message), (Artifact) null));
                }
            });


        }else{
            artifact_.setValue(Resource.error(  context.getString(R.string.barcode_error_message) , (Artifact) null));
        }

    }

    public MutableLiveData<Resource<Artifact>> getArtifact_() {
        return artifact_;
    }
}
