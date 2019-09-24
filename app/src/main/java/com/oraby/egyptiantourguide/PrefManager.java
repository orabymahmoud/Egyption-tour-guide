package com.oraby.egyptiantourguide;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.auth.login.AuthResource;

import javax.inject.Inject;

public class PrefManager {

    Context context;
    private  User cachedUser =  new User();


    @Inject
    public PrefManager() {
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void saveLoginDetails(User user){
        cachedUser = user;
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name", user.getName());
        editor.putString("Mobile", user.getMobile());
        editor.putString("Password", user.getPassword());
        editor.commit();
    }

    public User getLoginDetails() {
        User user = new User();

        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        user.setName(sharedPreferences.getString("Name", ""));
        user.setMobile(sharedPreferences.getString("Mobile", ""));
        user.setPassword(sharedPreferences.getString("Password", ""));

        return user;
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        boolean isEmailEmpty = sharedPreferences.getString("Mobile", "").isEmpty();
        boolean isPasswordEmpty = sharedPreferences.getString("Password", "").isEmpty();
        return isEmailEmpty || isPasswordEmpty;
    }

    public void LogedOut()
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

}