package com.oraby.egyptiantourguide.di.main.hotels;

import com.oraby.egyptiantourguide.ui.main.hotels.RecyclerViewVerticalListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class HotelsModule {
    @HotelsScope
    @Provides
    static RecyclerViewVerticalListAdapter providesRecyclerViewVerticalListAdapter(){return new RecyclerViewVerticalListAdapter();}
}
