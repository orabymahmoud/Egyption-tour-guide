package com.oraby.egyptiantourguide.di.main.hotels;

import androidx.lifecycle.ViewModel;
import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.home.HomeFragmentViewModel;
import com.oraby.egyptiantourguide.ui.main.hotels.HotelsFragmentViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class HotelsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HotelsFragmentViewModel.class)
    public abstract ViewModel bindHotelsViewModel(HotelsFragmentViewModel viewModel);
}
