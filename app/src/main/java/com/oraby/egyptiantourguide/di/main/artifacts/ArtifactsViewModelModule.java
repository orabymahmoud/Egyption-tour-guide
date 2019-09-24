package com.oraby.egyptiantourguide.di.main.artifacts;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.auth.login.LogInViewModel;
import com.oraby.egyptiantourguide.ui.main.artifacts.ArtifactsFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ArtifactsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ArtifactsFragmentViewModel.class)
    public abstract ViewModel bindArtifactsViewModel(ArtifactsFragmentViewModel viewModel);
}
