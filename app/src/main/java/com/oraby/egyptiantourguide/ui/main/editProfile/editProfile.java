package com.oraby.egyptiantourguide.ui.main.editProfile;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.oraby.egyptiantourguide.AuthUser;
import com.oraby.egyptiantourguide.PrefManager;
import com.oraby.egyptiantourguide.R;
import com.oraby.egyptiantourguide.models.User;
import com.oraby.egyptiantourguide.ui.Resource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class editProfile extends DaggerAppCompatActivity {

    @Inject
    public editProfileViewModel editProfileViewModel;
    private EditText name;
    private EditText password;
    private Button button;
    private ProgressBar   progressBar;

    @Inject
    PrefManager prefManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        editProfileViewModel.setContext(this);

        name = findViewById(R.id.name_edit_profile);
        password = findViewById(R.id.password_edit_profile);
        button = findViewById(R.id.button_edit_profile);
        progressBar = findViewById(R.id.Progress_edit_profile);

        name.setText(AuthUser.getUser().getName());
        password.setText(AuthUser.getUser().getPassword());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name_ = name.getText().toString();
                final String password_ = password.getText().toString();


                if (name_.isEmpty() && name_.length() <= 7) {
                    name.setError(getString(R.string.invalid_name) +"");
                    name.setFocusable(true);
                    return;

                }

                if (password_.length() <= 7) {
                    password.setError(getString(R.string.inavlid_password) + "");
                    password.setFocusable(true);
                    return;
                }

                editProfileViewModel.updateuser(name_ ,password_);

                editProfileViewModel.getResourceMutableLiveData().observe(editProfile.this, new Observer<Resource<Boolean>>() {
                    @Override
                    public void onChanged(Resource<Boolean> booleanResource) {
                        if(booleanResource != null)
                        {
                            switch (booleanResource.status)
                            {
                                case LOADING:{
                                    progressBar.setVisibility(View.VISIBLE);
                                    break;
                                }

                                case SUCCESS:{
                                    User  user = new User(name_ , AuthUser.getUser().getMobile() , password_  , null ,null , null);
                                    prefManager.saveLoginDetails(user);
                                    Toast.makeText(editProfile.this , getString(R.string.updated_profile ), Toast.LENGTH_SHORT).show();
                                    finish();
                                    progressBar.setVisibility(View.GONE);
                                    break;
                                }

                                case ERROR:{
                                    Toast.makeText(editProfile.this , booleanResource.message , Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                    break;
                                }
                            }
                        }
                    }
                });


            }
        });

    }
}
