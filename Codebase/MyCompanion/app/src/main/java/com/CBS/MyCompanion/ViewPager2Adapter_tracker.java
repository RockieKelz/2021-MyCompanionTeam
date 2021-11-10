package com.CBS.MyCompanion;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPager2Adapter_tracker extends FragmentStateAdapter
{

    public ViewPager2Adapter_tracker(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 1:
                return new TrackerFragment_month();
            case 2:
                return new TrackerFragment_year();
        }

        return new TrackerFragment();

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
