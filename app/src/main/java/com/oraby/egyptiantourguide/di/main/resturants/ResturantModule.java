package com.oraby.egyptiantourguide.di.main.resturants;

import com.oraby.egyptiantourguide.ui.main.resturants.RecyclerViewVerticalListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ResturantModule {

    @ResturantScope
    @Provides
    static RecyclerViewVerticalListAdapter providesRecyclerViewVerticalListAdapter(){return new RecyclerViewVerticalListAdapter();}
}
