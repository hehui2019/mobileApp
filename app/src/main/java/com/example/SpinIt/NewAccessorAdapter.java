package com.example.SpinIt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewAccessorAdapter extends FragmentPagerAdapter {
    public NewAccessorAdapter (@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                FriendsFragment friendsFragment = new FriendsFragment();
                return friendsFragment;

            case 1:
                RFriendsFragment rfriendsFragment = new RFriendsFragment();
                return rfriendsFragment;

            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "friends";

            case 1:

                return "people in the room";
            default:
                return null;
        }


    }


}