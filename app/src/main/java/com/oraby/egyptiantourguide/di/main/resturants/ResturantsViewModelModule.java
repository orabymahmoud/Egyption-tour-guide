package com.oraby.egyptiantourguide.di.main.resturants;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.profile.ProfileFragmentViewModel;
import com.oraby.egyptiantourguide.ui.main.resturants.ResturantsFragmentsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ResturantsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ResturantsFragmentsViewModel.class)
    public abstract ViewModel bindResturanntViewModel(ResturantsFragmentsViewModel viewModel);

}
