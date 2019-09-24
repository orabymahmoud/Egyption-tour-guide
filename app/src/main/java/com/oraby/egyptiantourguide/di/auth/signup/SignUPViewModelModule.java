package com.oraby.egyptiantourguide.di.auth.signup;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.auth.login.LogInViewModel;
import com.oraby.egyptiantourguide.ui.auth.signup.SignUpViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class SignUPViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    public abstract ViewModel bindSignUpViewModel(SignUpViewModel viewModel);
}
