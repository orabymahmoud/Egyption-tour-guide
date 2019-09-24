package com.oraby.egyptiantourguide.ui.main.home;

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
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsView;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerViewHorizontalListAdapterresturants extends RecyclerView.Adapter<RecyclerViewHorizontalListAdapterresturants.Resturantsholder>{
    private List<Resturant> resturants;
    private  List<Resturant> itemsCopy = new ArrayList<>();
    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setResturants(List<Resturant> resturants) {
        this.resturants = resturants;
        for(Resturant item: resturants){
            itemsCopy.add(item);
        }
    }

    @Override
    public Resturantsholder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_horizental, parent, false);
        Resturantsholder gvh = new Resturantsholder(view);
        return gvh;
    }

    @Override
    public void onBindViewHolder(Resturantsholder holder, final int position) {
        holder.txtview.setText(resturants.get(position).getName());
        Log.e(TAG, "onBindViewHolder: " + resturants.get(position).getPhoto() );


        Glide.with(context).load(resturants.get(position).getPhoto()).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = resturants.get(position).getName().toString();

                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context , DetailsView.class);

                intent.putExtra("reload" , "profile");
                intent.putExtra("type","resturant");
                intent.putExtra("name",resturants.get(position).getName());
                intent.putExtra("likes",resturants.get(position).getLikes() + "");
                intent.putExtra("photo",resturants.get(position).getPhoto());
                intent.putExtra("city",resturants.get(position).getAdress());
                intent.putExtra("description",resturants.get(position).getDescription());
                intent.putExtra("id",resturants.get(position).getId()+"");
                intent.putExtra("url",resturants.get(position).getUrl());

                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return resturants.size();
    }

    public class Resturantsholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        public Resturantsholder(View view) {
            super(view);
            imageView=view.findViewById(R.id.image_art);
            txtview=view.findViewById(R.id.name_art);
        }
    }

    public void filter(String text) {

        resturants.clear();
        if(text.isEmpty()){
            resturants.addAll(itemsCopy);
        } else{
            text = text.toLowerCase();
            for(Resturant item: itemsCopy){
                if(item.getName().toLowerCase().contains(text) || item.getAdress().toLowerCase().contains(text)){
                    resturants.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
