package com.oraby.egyptiantourguide.ui.main.profile;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHorizontalListAdapterHotels extends RecyclerView.Adapter<RecyclerViewHorizontalListAdapterHotels.Hotelsholder>{
    private List<Hotel> hotels;
    private List<Hotel> itemsCopy = new ArrayList<>();
    Context context;
    private static final String TAG = "RecyclerViewHorizontalL";

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
        for(Hotel item: hotels){
            itemsCopy.add(item);
        }
    }

    @Override
    public Hotelsholder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_horizental, parent, false);
        Hotelsholder gvh = new Hotelsholder(view);
        return gvh;
    }

    @Override
    public void onBindViewHolder(Hotelsholder holder, final int position) {
        holder.txtview.setText(hotels.get(position).getName());
        Log.e(TAG, "onBindViewHolder: " + hotels.get(position).getPhoto() );


        Glide.with(context).load(hotels.get(position).getPhoto()).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = hotels.get(position).getName().toString();

                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context , DetailsView.class);

                intent.putExtra("type","hotel");
                intent.putExtra("name",hotels.get(position).getName());
                intent.putExtra("likes",hotels.get(position).getLikes() + "");
                intent.putExtra("photo",hotels.get(position).getPhoto());
                intent.putExtra("city",hotels.get(position).getAdress());
                intent.putExtra("description",hotels.get(position).getDescription());
                intent.putExtra("id",hotels.get(position).getId()+"");
                intent.putExtra("url",hotels.get(position).getUrl());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class Hotelsholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        public Hotelsholder(View view) {
            super(view);
            imageView=view.findViewById(R.id.image_art);
            txtview=view.findViewById(R.id.name_art);
        }
    }

    public void filter(String text) {

        hotels.clear();
        if(text.isEmpty()){
            hotels.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(Hotel item: itemsCopy){
                if(item.getName().toLowerCase().contains(text) || item.getAdress().toLowerCase().contains(text)){
                    hotels.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
