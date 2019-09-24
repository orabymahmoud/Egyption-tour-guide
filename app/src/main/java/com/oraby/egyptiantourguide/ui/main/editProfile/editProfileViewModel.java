package com.oraby.egyptiantourguide.ui.main.editProfile;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oraby.egyptiantourguide.AuthUser;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.ui.Resource;

import javax.inject.Inject;

public class editProfileViewModel extends ViewModel {

    private DatabaseReference mDatabase;
    private Context context;
    private DatabaseReference databaseReference ;

    private MutableLiveData<Resource<Boolean>> resourceMutableLiveData = new MutableLiveData<>();
    @Inject
    public editProfileViewModel(){}

    public void setContext(Context context) {
        this.context = context;
    }

    public void updateuser(String name , final String password)
    {
        resourceMutableLiveData.setValue(Resource.loading((Boolean) null));
        mDatabase = FirebaseDatabase.getInstance().getReference();

        databaseReference = mDatabase.child("users").child(AuthUser.getUser().getMobile()).child("Name");

        databaseReference.setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                databaseReference = mDatabase.child("users").child(AuthUser.getUser().getMobile()).child("Password");
                databaseReference.setValue(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                     resourceMutableLiveData.setValue(Resource.success(true));
                    }})
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                resourceMutableLiveData.setValue(Resource.error(context.getString(R.string.err_pass)+"" , (Boolean) null));
                            }
                        });
            }})
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    resourceMutableLiveData.setValue(Resource.error(context.getString(R.string.err_user)+"" , (Boolean) null));
                }
            });

    }

    public MutableLiveData<Resource<Boolean>> getResourceMutableLiveData() {
        return resourceMutableLiveData;
    }
}
