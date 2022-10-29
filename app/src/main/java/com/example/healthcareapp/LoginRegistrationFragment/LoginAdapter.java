package com.example.healthcareapp.LoginRegistrationFragment;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.healthcareapp.LoginRegistrationFragment.LoginFragment;
import com.example.healthcareapp.LoginRegistrationFragment.RegistrationFragment;

public class LoginAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalCount;


    public LoginAdapter(@NonNull FragmentManager fm, Context context, int totalCount) {
        super(fm);
        this.context = context;
        this.totalCount = totalCount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LoginFragment loginFragement = new LoginFragment();
                return loginFragement;
            case 1:
                RegistrationFragment registrationFragment = new RegistrationFragment();
                return registrationFragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalCount;
    }
}

