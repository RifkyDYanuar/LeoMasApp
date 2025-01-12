package com.leo.leomasapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leo.leomasapp.AllProductFragment;
import com.leo.leomasapp.NeklaceFragment;
import com.leo.leomasapp.RingsFragment;

public class ViewPagerAllProductAdapter extends FragmentStateAdapter {
    public ViewPagerAllProductAdapter(@NonNull FragmentManager fragmentActivity, @NonNull Lifecycle lifecycle) {
        super(fragmentActivity,lifecycle);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0 :
                return new AllProductFragment();

            case 1:
                return new RingsFragment();
            case 2 :
                return new NeklaceFragment();
        }
        return new AllProductFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
