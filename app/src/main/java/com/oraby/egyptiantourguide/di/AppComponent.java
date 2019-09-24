package com.oraby.egyptiantourguide.di;

import android.app.Application;

import com.bumptech.glide.RequestManager;
import com.oraby.egyptiantourguide.BaseApplication;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.models.User;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidSupportInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,
        }
)
public interface AppComponent extends AndroidInjector<BaseApplication> {

    RequestManager requestManager();

    PrefManager prefManger();

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}







