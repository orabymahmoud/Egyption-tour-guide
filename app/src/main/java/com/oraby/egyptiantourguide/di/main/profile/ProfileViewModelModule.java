package com.oraby.egyptiantourguide.di.main.profile;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.hotels.HotelsFragmentViewModel;
import com.oraby.egyptiantourguide.ui.main.profile.ProfileFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ProfileViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileFragmentViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileFragmentViewModel viewModel);
}
