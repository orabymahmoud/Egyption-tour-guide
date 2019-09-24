package com.oraby.egyptiantourguide.di;

import com.oraby.egyptiantourguide.di.auth.login.LogInModule;
import com.oraby.egyptiantourguide.di.auth.login.LogInScope;
import com.oraby.egyptiantourguide.di.auth.login.LogInViewModelModule;
import com.oraby.egyptiantourguide.di.auth.signup.SignUPViewModelModule;
import com.oraby.egyptiantourguide.di.auth.signup.SignUpModule;
import com.oraby.egyptiantourguide.di.auth.signup.SignUpScope;
import com.oraby.egyptiantourguide.di.auth.vervication.VervicationModule;
import com.oraby.egyptiantourguide.di.auth.vervication.VervicationScope;
import com.oraby.egyptiantourguide.di.main.artifacts.ArtifactsModule;
import com.oraby.egyptiantourguide.di.main.artifacts.ArtifactsScope;
import com.oraby.egyptiantourguide.di.main.artifacts.ArtifactsViewModelModule;
import com.oraby.egyptiantourguide.di.main.detialsview.DetailsViewModule;
import com.oraby.egyptiantourguide.di.main.detialsview.DetailsViewScope;
import com.oraby.egyptiantourguide.di.main.detialsview.DetailsViewViewModelModule;
import com.oraby.egyptiantourguide.di.main.editprofile.EditProfileModule;
import com.oraby.egyptiantourguide.di.main.editprofile.EditProfileScope;
import com.oraby.egyptiantourguide.di.main.editprofile.EditProfileViewModelModule;
import com.oraby.egyptiantourguide.di.main.home.HomeModule;
import com.oraby.egyptiantourguide.di.main.home.HomeScope;
import com.oraby.egyptiantourguide.di.main.home.HomeViewModelModule;
import com.oraby.egyptiantourguide.di.main.hotels.HotelsModule;
import com.oraby.egyptiantourguide.di.main.hotels.HotelsScope;
import com.oraby.egyptiantourguide.di.main.hotels.HotelsViewModelModule;
import com.oraby.egyptiantourguide.di.main.profile.ProfileModule;
import com.oraby.egyptiantourguide.di.main.profile.ProfileScope;
import com.oraby.egyptiantourguide.di.main.profile.ProfileViewModelModule;
import com.oraby.egyptiantourguide.di.main.resturants.ResturantModule;
import com.oraby.egyptiantourguide.di.main.resturants.ResturantScope;
import com.oraby.egyptiantourguide.di.main.resturants.ResturantsViewModelModule;
import com.oraby.egyptiantourguide.di.main.scanbarcode.ScanModule;
import com.oraby.egyptiantourguide.di.main.scanbarcode.ScanScope;
import com.oraby.egyptiantourguide.di.main.scanbarcode.ScanViewModelModule;
import com.oraby.egyptiantourguide.ui.MainActivity;
import com.oraby.egyptiantourguide.ui.auth.login.LogIn;
import com.oraby.egyptiantourguide.ui.auth.login.LogInViewModel;
import com.oraby.egyptiantourguide.ui.auth.signup.SignUp;
import com.oraby.egyptiantourguide.ui.auth.SplashScrean;
import com.oraby.egyptiantourguide.ui.auth.vervication.VervicationActivity;
import com.oraby.egyptiantourguide.ui.main.artifacts.artifacts_fragment;
import com.oraby.egyptiantourguide.ui.main.detailsView.DetailsView;
import com.oraby.egyptiantourguide.ui.main.editProfile.editProfile;
import com.oraby.egyptiantourguide.ui.main.home.home_fragment;
import com.oraby.egyptiantourguide.ui.main.hotels.hotels_fragment;
import com.oraby.egyptiantourguide.ui.main.profile.profile_fragment;
import com.oraby.egyptiantourguide.ui.main.resturants.resturants_fragments;
import com.oraby.egyptiantourguide.ui.main.scanbarcode.ScannedBarcodeActivity;

import javax.inject.Singleton;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {

    @ContributesAndroidInjector
    abstract SplashScrean contributesplashsactivity();

    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivity();

    @HomeScope
    @ContributesAndroidInjector(modules = {HomeViewModelModule.class , HomeModule.class})
    abstract home_fragment contributeHomeFragment();

    @HotelsScope
    @ContributesAndroidInjector(modules = {HotelsModule.class , HotelsViewModelModule.class})
    abstract hotels_fragment contributehotelsFragment();

    @ResturantScope
    @ContributesAndroidInjector(modules = {ResturantsViewModelModule.class , ResturantModule.class})
    abstract resturants_fragments contributeresturantsFragment();

    @ProfileScope
    @ContributesAndroidInjector(modules = {ProfileViewModelModule.class , ProfileModule.class})
    abstract profile_fragment contributeprofileFragment();

    @ArtifactsScope
    @ContributesAndroidInjector(modules = {ArtifactsViewModelModule.class , ArtifactsModule.class})
    abstract artifacts_fragment contributeartifactsFragment();

    @LogInScope
    @ContributesAndroidInjector(modules = {LogInModule.class , LogInViewModelModule.class})
    abstract LogIn contributelogActivity();

    @SignUpScope
    @ContributesAndroidInjector(modules = {SignUPViewModelModule.class , SignUpModule.class})
    abstract SignUp contributesignupActivity();

    @EditProfileScope
    @ContributesAndroidInjector(modules = {EditProfileModule.class , EditProfileViewModelModule.class})
    abstract editProfile contributeseditprofileactivity();

    @DetailsViewScope
    @ContributesAndroidInjector(modules = {DetailsViewViewModelModule.class , DetailsViewModule.class})
    abstract DetailsView contributesdetialsviewactivity();

    @VervicationScope
    @ContributesAndroidInjector(modules = {VervicationModule.class})
    abstract VervicationActivity contributesVervicationActivity();

    @ScanScope
    @ContributesAndroidInjector(modules = {ScanViewModelModule.class , ScanModule.class})
    abstract ScannedBarcodeActivity contributesscanActivity();

}
