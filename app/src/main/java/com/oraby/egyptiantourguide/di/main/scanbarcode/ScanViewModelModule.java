package com.oraby.egyptiantourguide.di.main.scanbarcode;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.scanbarcode.ScanViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ScanViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ScanViewModel.class)
    public abstract ViewModel bindsacnViewModel(ScanViewModel viewModel);
}
