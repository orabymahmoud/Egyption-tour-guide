package com.oraby.egyptiantourguide.ui.main.resturants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.Hotel;
import com.oraby.egyptiantourguide.models.Resturant;
import com.oraby.egyptiantourguide.ui.Resource;
import com.oraby.egyptiantourguide.ui.main.scanbarcode.ScannedBarcodeActivity;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class resturants_fragments extends DaggerFragment {

    private static final String TAG = "resturants_fragments";
    @Inject
    public ResturantsFragmentsViewModel mViewModel;
    private ProgressBar progressBar;
    private com.oraby.egyptiantourguide.ui.main.resturants.RecyclerViewVerticalListAdapter recyclerViewVerticalListAdapter;
    private RecyclerView recyclerView;
    @Inject
    PrefManager prefManager;


    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;

    public static resturants_fragments newInstance() {
        return new resturants_fragments();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.resturants_fragments_fragment, container, false);
        mViewModel.setContext(((AppCompatActivity)getActivity()));
        prefManager.setContext(((AppCompatActivity)getActivity()));

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
                    mToolbar.setTitle(getString(R.string.resturants));
                    mToolbar.setSubtitle(null);
                    mToolbar.setTitleTextColor(Color.WHITE);
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });

        recyclerView = view.findViewById(R.id.resturants);
        progressBar = view.findViewById(R.id.Progress_home);

        mViewModel.getresturants();

        mViewModel.observepopularressturants().observe(this, new Observer<Resource<List<Resturant>>>() {
            @Override
            public void onChanged(Resource<List<Resturant>> listResource) {
                if(listResource != null)
                {
                    switch (listResource.status)
                    {
                        case SUCCESS:{
                            progressBar.setVisibility(View.GONE);

                            recyclerViewVerticalListAdapter = new RecyclerViewVerticalListAdapter();
                            recyclerViewVerticalListAdapter.setContext(((AppCompatActivity)getActivity()));
                            recyclerViewVerticalListAdapter.setResturants(listResource.data);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(((AppCompatActivity)getActivity()), LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(recyclerViewVerticalListAdapter);
                            break;
                        }

                        case ERROR:{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(((AppCompatActivity) getActivity()) , listResource.message , Toast.LENGTH_SHORT).show();
                            break;
                        }

                        case LOADING:{
                            progressBar.setVisibility(View.VISIBLE);
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
        mViewModel = ViewModelProviders.of(this).get(ResturantsFragmentsViewModel.class);
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
                    recyclerViewVerticalListAdapter.filter(newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.e("onQueryTextSubmit", query);
                    recyclerViewVerticalListAdapter.filter(query);
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

    @Override
    public void onResume() {
        super.onResume();

        if(mViewModel != null && recyclerViewVerticalListAdapter != null)
        {
            mViewModel.getresturants();

            mViewModel.observepopularressturants().observe(this, new Observer<Resource<List<Resturant>>>() {
                @Override
                public void onChanged(Resource<List<Resturant>> listResource) {
                    if(listResource.data!= null)
                    {
                        recyclerViewVerticalListAdapter.setResturants(listResource.data);
                    }
                }
            });

        }
    }

}
