package com.oraby.egyptiantourguide.ui.main.detailsView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.RequestManager;
import com.oraby.egyptiantourguide.AuthUser;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.ui.MainActivity;
import com.oraby.egyptiantourguide.ui.auth.login.LogIn;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class DetailsView extends DaggerAppCompatActivity {

    TextView name;
    TextView likes;
    TextView city;
    TextView description;
    TextView url;
    ImageView imageView;
    ImageView like_color;
    LinearLayout linearLayout;
    LinearLayout likes_icon;

    @Inject
    RequestManager requestManager;

    @Inject
    PrefManager prefManager;

    @Inject
    DetailsViewViewModel detailsViewViewModel;

    private static final String TAG = "DetailsView";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        name = findViewById(R.id.name_id);
        likes = findViewById(R.id.likes_id);
        city = findViewById(R.id.city_id);
        description = findViewById(R.id.description_id);
        imageView = findViewById(R.id.image_id);
        url = findViewById(R.id.url_id);
        linearLayout = findViewById(R.id.url_view_id);
        likes_icon = findViewById(R.id.like_photo_id);
        like_color = findViewById(R.id.like_id);

        final String type = getIntent().getStringExtra("type");
        final String name_ = getIntent().getStringExtra("name");
        final String city_ = getIntent().getStringExtra("city");
        final String likes_ = getIntent().getStringExtra("likes");
        final String description_ = getIntent().getStringExtra("description");
        final String id = getIntent().getStringExtra("id");
        final String photo = getIntent().getStringExtra("photo");
        final String url_ = getIntent().getStringExtra("url");



        if(type.equals("artifact")) {
            List<Artifact> artifacts = AuthUser.getUser().getLikesarts();

            if (artifacts != null && artifacts.size() > 0) {
                for (Artifact artifact : artifacts
                ) {
                    if (artifact.getName().equals(name_)) {
                        like_color.setImageResource(R.drawable.like3);
                    }
                }

            }
        }else if(type.equals("hotel"))
        {
            List<Hotel> hotels = AuthUser.getUser().getLikeshotels();

            if(hotels != null && hotels.size() > 0)
            {
                for (Hotel hotel:hotels
                ) {
                    if(hotel.getName().equals(name_)){
                        like_color.setImageResource(R.drawable.like3);
                    }
                }

            }

        }else if(type.equals("resturant"))
        {

            List<Resturant> resturants = AuthUser.getUser().getLikesresturants();

            if(resturants != null && resturants.size() > 0)
            {
                for (Resturant resturant:resturants
                ) {
                    if(resturant.getName().equals(name_)){
                        like_color.setImageResource(R.drawable.like3);
                    }
                }

            }

        }


        if(url_ != null)
        {
            linearLayout.setVisibility(View.VISIBLE);
        }

        requestManager.load(photo).into(imageView);
        name.setText(name_);
        likes.setText(likes_);
        city.setText(city_);
        description.setText(description_);
        url.setText(url_);

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url_));
                startActivity(i);
            }
        });

        likes_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(type.equals("artifact"))
                {
                    List<Artifact> artifacts = AuthUser.getUser().getLikesarts();

                    if(artifacts != null && artifacts.size() > 0)
                    {
                        for (Artifact artifact:artifacts
                             ) {
                            if(artifact.getName().equals(name_)){return;}
                        }
                    }

                    likes.setText(Long.parseLong(likes_) + 1 + "");
                    like_color.setImageResource(R.drawable.like3);
                    Artifact artifact = new Artifact(Long.parseLong(id+""),name_ ,city_ ,photo ,description_ ,Long.parseLong(likes_));
                    detailsViewViewModel.likeAritfact(artifact , AuthUser.getUser());
                    likes.setText(Long.parseLong(likes_) + 1 + "");
                    like_color.setImageResource(R.drawable.like3);

                }else if(type.equals("hotel"))
                {
                    List<Hotel> hotels = AuthUser.getUser().getLikeshotels();

                    if(hotels != null && hotels.size() > 0)
                    {
                        for (Hotel hotel:hotels
                        ) {
                            if(hotel.getName().equals(name_)){return;}
                        }
                    }

                    likes.setText(Long.parseLong(likes_) + 1 + "");
                    like_color.setImageResource(R.drawable.like3);
                    Hotel hotel = new Hotel(Long.parseLong(id+""),name_ ,city_ ,photo ,url_,description_ ,Long.parseLong(likes_));
                    detailsViewViewModel.likehotel(hotel , AuthUser.getUser());
                    likes.setText(Long.parseLong(likes_) + 1 + "");
                    like_color.setImageResource(R.drawable.like3);

                }else if(type.equals("resturant"))
                {
                    List<Resturant> resturants = AuthUser.getUser().getLikesresturants();

                    if(resturants != null && resturants.size() > 0)
                    {
                        for (Resturant resturant:resturants
                        ) {
                            if(resturant.getName().equals(name_)){return;}
                        }
                    }

                    likes.setText(Long.parseLong(likes_) + 1 + "");
                    like_color.setImageResource(R.drawable.like3);
                    Resturant resturant = new Resturant(Long.parseLong(id+""),name_ ,city_ ,photo ,url_,description_ ,Long.parseLong(likes_));
                    detailsViewViewModel.likeresturant(resturant , AuthUser.getUser());
                    likes.setText(Long.parseLong(likes_) + 1 + "");
                    like_color.setImageResource(R.drawable.like3);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String reload = getIntent().getStringExtra("reload");

        if(reload != null)
        {
            if(reload.equals("home"))
            {
                startActivity(new Intent(DetailsView.this, MainActivity.class));
                finish();
            }
        }


    }
}
