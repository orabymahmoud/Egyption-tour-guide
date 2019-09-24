package com.oraby.egyptiantourguide.ui.main.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oraby.egyptiantourguide.AuthUser;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Artifact;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.MainActivity;
import com.oraby.egyptiantourguide.ui.Resource;
import com.oraby.egyptiantourguide.ui.auth.login.AuthResource;
import com.oraby.egyptiantourguide.ui.auth.login.LogIn;
import com.oraby.egyptiantourguide.ui.main.editProfile.editProfile;
import com.oraby.egyptiantourguide.ui.main.scanbarcode.ScannedBarcodeActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static android.app.Activity.RESULT_CANCELED;
import static androidx.constraintlayout.widget.Constraints.TAG;

public class home_fragment extends DaggerFragment {

    @Inject
    public HomeFragmentViewModel mViewModel;
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;
    private ProgressBar progressBar;

    public com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterArtifacts recyclerViewHorizontalListAdapterArtifacts;
    public com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterHotels recyclerViewHorizontalListAdapterHotels;
    public com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterresturants recyclerViewHorizontalListAdapterresturants;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    @Inject
    PrefManager prefManager;


    public static home_fragment newInstance() {
        return new home_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.home_fragment_fragment, container, false);

        mViewModel.setContext(((AppCompatActivity)getActivity()));
        prefManager.setContext(((AppCompatActivity)getActivity()));

        String phone = prefManager.getLoginDetails().getMobile();
        String password = prefManager.getLoginDetails().getPassword();

        mViewModel.getauthdata(phone , password).observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if(userAuthResource != null)
                {
                    switch (userAuthResource.status)
                    {
                        case LOADING:{

                            break;
                        }

                        case AUTHENTICATED:{
                            AuthUser.setUser(userAuthResource.data);
                            break;
                        }

                        case ERROR:{
                            Toast.makeText(((AppCompatActivity)getActivity()) ,userAuthResource.message ,Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case NOT_AUTHENTICATED:{
                            break;
                        }
                    }
                }
            }
        });

        final Toolbar mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);

        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) view.findViewById(R.id.toolbar_layout);
        AppBarLayout appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    mToolbar.setTitle(getString(R.string.home));
                    mToolbar.setSubtitle(null);
                    mToolbar.setTitleTextColor(Color.WHITE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });


        recyclerView = view.findViewById(R.id.popularartifacts);
        recyclerView1 = view.findViewById(R.id.popularhotels);
        recyclerView2 = view.findViewById(R.id.popularresturants);
        progressBar = view.findViewById(R.id.Progress_home);


        mViewModel.getPopularartifacts();
        mViewModel.getPopularhotels();
        mViewModel.getPopularresturants();

        mViewModel.observepopularartifacts().observe(this, new Observer<Resource<List<Artifact>>>() {
            @Override
            public void onChanged(Resource<List<Artifact>> listResource) {
                if(listResource != null)
                {
                    switch (listResource.status)
                    {
                        case LOADING: {
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        }

                        case ERROR:{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(((AppCompatActivity)getActivity()) ,listResource.message , Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case SUCCESS:{
                            progressBar.setVisibility(View.GONE);
                            recyclerView.addItemDecoration(new DividerItemDecoration(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL) {
                                @Override
                                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) { }});

                            recyclerViewHorizontalListAdapterArtifacts = new RecyclerViewHorizontalListAdapterArtifacts();
                            recyclerViewHorizontalListAdapterArtifacts.setContext(((AppCompatActivity)getActivity()));
                            recyclerViewHorizontalListAdapterArtifacts.setSuperArtifacts(new ArrayList<Artifact>(99));

                            recyclerViewHorizontalListAdapterArtifacts.setSuperArtifacts(listResource.data);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView.setLayoutManager(horizontalLayoutManager);
                            recyclerView.setAdapter(recyclerViewHorizontalListAdapterArtifacts);

                            break;
                        }
                    }
                }
            }
        });

        mViewModel.observepopularhotels().observe(this, new Observer<Resource<List<Hotel>>>() {
            @Override
            public void onChanged(Resource<List<Hotel>> listResource) {
                if(listResource != null)
                {
                    switch (listResource.status)
                    {
                        case LOADING: {
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        }

                        case ERROR:{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(((AppCompatActivity)getActivity()) ,listResource.message , Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case SUCCESS:{
                            progressBar.setVisibility(View.GONE);
                            recyclerView1.addItemDecoration(new DividerItemDecoration(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL) {
                                @Override
                                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) { }});

                            recyclerViewHorizontalListAdapterHotels = new RecyclerViewHorizontalListAdapterHotels();
                            recyclerViewHorizontalListAdapterHotels.setContext(((AppCompatActivity)getActivity()));
                            recyclerViewHorizontalListAdapterHotels.setHotels(new ArrayList<Hotel>());
                            recyclerViewHorizontalListAdapterHotels.setHotels(listResource.data);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView1.setLayoutManager(horizontalLayoutManager);
                            recyclerView1.setAdapter(recyclerViewHorizontalListAdapterHotels);

                            break;
                        }
                    }
                }
            }
        });

        mViewModel.observepopularressturants().observe(this, new Observer<Resource<List<Resturant>>>() {
            @Override
            public void onChanged(Resource<List<Resturant>> listResource) {
                if(listResource != null)
                {
                    switch (listResource.status)
                    {
                        case LOADING: {
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        }

                        case ERROR:{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(((AppCompatActivity)getActivity()) ,listResource.message , Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case SUCCESS:{
                            progressBar.setVisibility(View.GONE);
                            recyclerView1.addItemDecoration(new DividerItemDecoration(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL) {
                                @Override
                                public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) { }});

                            recyclerViewHorizontalListAdapterresturants = new RecyclerViewHorizontalListAdapterresturants();
                            recyclerViewHorizontalListAdapterresturants.setContext(((AppCompatActivity)getActivity()));
                            recyclerViewHorizontalListAdapterresturants.setResturants(new ArrayList<Resturant>());
                            recyclerViewHorizontalListAdapterresturants.setResturants(listResource.data);
                            LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(((AppCompatActivity)getActivity()), LinearLayoutManager.HORIZONTAL, false);
                            recyclerView2.setLayoutManager(horizontalLayoutManager);
                            recyclerView2.setAdapter(recyclerViewHorizontalListAdapterresturants);

                            break;
                        }
                    }
                }
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(((AppCompatActivity)getActivity()) , ScannedBarcodeActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeFragmentViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.e("onQueryTextChange", newText);
                    recyclerViewHorizontalListAdapterHotels.filter(newText);
                    recyclerViewHorizontalListAdapterArtifacts.filter(newText);
                    recyclerViewHorizontalListAdapterresturants.filter(newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.e("onQueryTextSubmit", query);
                    recyclerViewHorizontalListAdapterHotels.filter(query);
                    recyclerViewHorizontalListAdapterArtifacts.filter(query);
                    recyclerViewHorizontalListAdapterresturants.filter(query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }


}
