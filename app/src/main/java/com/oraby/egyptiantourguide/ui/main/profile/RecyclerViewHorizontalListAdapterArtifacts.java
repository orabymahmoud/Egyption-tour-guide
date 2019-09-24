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
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RecyclerViewHorizontalListAdapterArtifacts extends RecyclerView.Adapter<RecyclerViewHorizontalListAdapterArtifacts.Artifactsholder>{
    private List<Artifact> superArtifacts = new ArrayList<>();
    private List<Artifact> itemsCopy = new ArrayList<>();
    Context context;

    private static final String TAG = "RecyclerViewHorizontalL";


    @Inject
    public RecyclerViewHorizontalListAdapterArtifacts() {
    }


    public void setSuperArtifacts(List<Artifact> superArtifacts) {
        this.superArtifacts = superArtifacts;
        for(Artifact item: superArtifacts){
           itemsCopy.add(item);
        }
    }

    public void setContext(Context context) {
        this.context = context;

    }

    @Override
    public Artifactsholder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflate the layout file
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_of_horizental, parent, false);
        Artifactsholder gvh = new Artifactsholder(view);
        return gvh;
    }

    @Override
    public void onBindViewHolder(Artifactsholder holder, final int position) {

        holder.txtview.setText(superArtifacts.get(position).getName());
        Log.e(TAG, "onBindViewHolder: " + superArtifacts.get(position).getPhoto() );


            Glide.with(context).load(superArtifacts.get(position).getPhoto()).into(holder.imageView);


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = superArtifacts.get(position).getName().toString();
                Toast.makeText(context, productName + " is selected", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context , DetailsView.class);

                intent.putExtra("type","artifact");
                intent.putExtra("name",superArtifacts.get(position).getName());
                intent.putExtra("likes",superArtifacts.get(position).getLikes() + "");
                intent.putExtra("photo",superArtifacts.get(position).getPhoto());
                intent.putExtra("city",superArtifacts.get(position).getAdress());
                intent.putExtra("description",superArtifacts.get(position).getDescription());
                intent.putExtra("id",superArtifacts.get(position).getId()+"");


                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return superArtifacts.size();
    }

    public class Artifactsholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtview;
        public Artifactsholder(View view) {
            super(view);
            imageView=view.findViewById(R.id.image_art);
            txtview=view.findViewById(R.id.name_art);
        }
    }

    public void filter(String text) {

        Log.e(TAG, "onBindViewHolder: " + superArtifacts.size());
        superArtifacts.clear();
        Log.e(TAG, "onBindViewHolder:  ater clear" + superArtifacts.size());
        if(text.isEmpty()){
            superArtifacts.addAll(itemsCopy);
            Log.e(TAG, "onBindViewHolder: add all items copey" + superArtifacts.size());
        } else{
            text = text.toLowerCase();
            Log.e(TAG, "onBindViewHolder: before for " + superArtifacts.size());
            Log.e(TAG, "onBindViewHolder: itemscopy size  " + itemsCopy.size());
            for(Artifact item: itemsCopy){
                Log.e(TAG, "onBindViewHolder: " + item.getName().contains(text));
                if(item.getName().toLowerCase().contains(text) || item.getAdress().toLowerCase().contains(text)){
                    superArtifacts.add(item);
                }
            }
            Log.e(TAG, "onBindViewHolder: after for " + superArtifacts.size());
        }
        notifyDataSetChanged();
    }
}
