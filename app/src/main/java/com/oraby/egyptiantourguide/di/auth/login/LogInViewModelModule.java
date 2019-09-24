package com.oraby.egyptiantourguide.di.auth.login;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.auth.login.LogInViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LogInViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel.class)
    public abstract ViewModel bindLogInViewModel(LogInViewModel viewModel);
}
