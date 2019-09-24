package com.oraby.egyptiantourguide.di.main.home;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.editProfile.editProfileViewModel;
import com.oraby.egyptiantourguide.ui.main.home.HomeFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HomeViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeFragmentViewModel.class)
    public abstract ViewModel bindHomeViewModel(HomeFragmentViewModel viewModel);
}
