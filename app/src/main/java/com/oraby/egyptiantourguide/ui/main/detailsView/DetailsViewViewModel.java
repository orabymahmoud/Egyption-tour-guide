package com.oraby.egyptiantourguide.ui.main.detailsView;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class DetailsViewViewModel extends ViewModel {

    @Inject
    PrefManager prefManager;
    private DatabaseReference mDatabase;
    private DatabaseReference databaseReference ;


    @Inject
    public DetailsViewViewModel() {

    }

    public void likeAritfact(Artifact artifact, User user)
    {
        if(user.getMobile() != null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();

            databaseReference = mDatabase.child("artifacts");

            artifact.setLikes(artifact.getLikes() + 1 );

            Map<String, Object> artifactMap = artifact.toMap();

            databaseReference.child(artifact.getId()+"").updateChildren(artifactMap);

            if(user.getLikesarts() != null)
            {
                List<Artifact> artifacts = user.getLikesarts();
                artifacts.add(artifact);
                user.setLikesarts(artifacts);
            }else{
                List<Artifact> artifacts = new ArrayList<>();
                artifacts.add(artifact);
                user.setLikesarts(artifacts);
            }

            Map<String, Object> userMap = user.toMap();

            databaseReference = mDatabase.child("users");

            databaseReference.child(user.getMobile()).updateChildren(userMap);
        }
    }

    public void likehotel(Hotel hotel , User user)
    {
        if(user.getMobile() != null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();

            databaseReference = mDatabase.child("hotels");

            hotel.setLikes(hotel.getLikes() + 1 );

            Map<String, Object> hotelMap = hotel.toMap();

            databaseReference.child(hotel.getId()+"").updateChildren(hotelMap);

            if(user.getLikeshotels() != null)
            {
                List<Hotel> hotels = user.getLikeshotels();
                hotels.add(hotel);
                user.setLikeshotels(hotels);
            }else{
                List<Hotel> hotels = new ArrayList<>();
                hotels.add(hotel);
                user.setLikeshotels(hotels);
            }

            Map<String, Object> userMap = user.toMap();

            databaseReference = mDatabase.child("users");

            databaseReference.child(user.getMobile()).updateChildren(userMap);
        }

    }

    public void likeresturant(Resturant resturant , User user)
    {
        if(user.getMobile() != null)
        {
            mDatabase = FirebaseDatabase.getInstance().getReference();

            databaseReference = mDatabase.child("resturants");

            resturant.setLikes(resturant.getLikes() + 1 );

            Map<String, Object> resturantMap = resturant.toMap();

            databaseReference.child(resturant.getId()+"").updateChildren(resturantMap);

            if(user.getLikeshotels() != null)
            {
                List<Resturant> resturants = user.getLikesresturants();
                resturants.add(resturant);
                user.setLikesresturants(resturants);
            }else{
                List<Resturant> resturants = new ArrayList<>();
                resturants.add(resturant);
                user.setLikesresturants(resturants);
            }

            Map<String, Object> userMap = user.toMap();

            databaseReference = mDatabase.child("users");

            databaseReference.child(user.getMobile()).updateChildren(userMap);
        }

    }


}
