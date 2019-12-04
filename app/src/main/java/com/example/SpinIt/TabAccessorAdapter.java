package com.example.SpinIt;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAccessorAdapter extends FragmentPagerAdapter {
    public TabAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                PGroupsFragment pgroupsFragment = new PGroupsFragment();
//                pgroupsFragment.setPerson(currentPerson);
                return pgroupsFragment;

            case 1:
                GroupsFragment groupsFragment = new GroupsFragment();
//                groupsFragment.setPerson(currentPerson);
                return groupsFragment;

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
                return "public groups";

            case 1:

                return "private groups";


            default:
                return null;
        }


    }
}
