package com.oraby.egyptiantourguide.di.main.home;

import android.content.Context;

import com.oraby.egyptiantourguide.di.main.hotels.HotelsScope;
import com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterArtifacts;
import com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterHotels;
import com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterresturants;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @HomeScope
    @Provides
    static RecyclerViewHorizontalListAdapterArtifacts  providesRecyclerViewHorizontalListAdapterArtifacts()
    {return new RecyclerViewHorizontalListAdapterArtifacts();}

    @HomeScope
    @Provides
    static RecyclerViewHorizontalListAdapterHotels providesRecyclerViewHorizontalListAdapterHotels()
    {return new RecyclerViewHorizontalListAdapterHotels();}

    @HomeScope
    @Provides
    static RecyclerViewHorizontalListAdapterresturants providesRecyclerViewHorizontalListAdapterresturants()
    {return new RecyclerViewHorizontalListAdapterresturants();}


}
