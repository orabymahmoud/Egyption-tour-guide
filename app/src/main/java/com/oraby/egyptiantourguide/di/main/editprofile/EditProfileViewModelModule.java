package com.oraby.egyptiantourguide.di.main.editprofile;

import androidx.lifecycle.ViewModel;

import com.oraby.egyptiantourguide.di.ViewModelKey;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsViewViewModel;
import com.oraby.egyptiantourguide.ui.main.editProfile.editProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class EditProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(editProfileViewModel.class)
    public abstract ViewModel bindEditProfileViewModel(editProfileViewModel viewModel);
}
