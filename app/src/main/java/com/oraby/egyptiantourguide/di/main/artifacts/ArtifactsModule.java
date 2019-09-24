package com.oraby.egyptiantourguide.di.main.artifacts;


import com.oraby.egyptiantourguide.ui.main.artifacts.RecyclerViewVerticalListAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class ArtifactsModule {

    @ArtifactsScope
    @Provides
    static RecyclerViewVerticalListAdapter providesArtifactsRecycleView(){return new RecyclerViewVerticalListAdapter();}

}
