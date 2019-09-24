package com.oraby.egyptiantourguide.di.main.profile;

import com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterArtifacts;
import com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterHotels;
import com.oraby.egyptiantourguide.ui.main.home.RecyclerViewHorizontalListAdapterresturants;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {

    @ProfileScope
    @Provides
    static RecyclerViewHorizontalListAdapterArtifacts providesRecyclerViewHorizontalListAdapterArtifacts()
    {return new RecyclerViewHorizontalListAdapterArtifacts();}

    @ProfileScope
    @Provides
    static RecyclerViewHorizontalListAdapterHotels providesRecyclerViewHorizontalListAdapterHotels()
    {return new RecyclerViewHorizontalListAdapterHotels();}

    @ProfileScope
    @Provides
    static RecyclerViewHorizontalListAdapterresturants providesRecyclerViewHorizontalListAdapterresturants()
    {return new RecyclerViewHorizontalListAdapterresturants();}
}
