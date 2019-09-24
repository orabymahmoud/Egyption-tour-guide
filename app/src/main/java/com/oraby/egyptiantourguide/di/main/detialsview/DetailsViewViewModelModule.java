package com.oraby.egyptiantourguide.di.main.detialsview;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.artifacts.ArtifactsFragmentViewModel;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsViewViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class DetailsViewViewModelModule {


    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewViewModel.class)
    public abstract ViewModel bindDetailsViewViewModel(DetailsViewViewModel viewModel);
}
