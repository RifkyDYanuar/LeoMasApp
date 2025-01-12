package com.leo.leomasapp.Adapter;

import android.os.Bundle;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leo.leomasapp.AccountFragment;
import com.leo.leomasapp.DashboardFragment;
import com.leo.leomasapp.Data.DataClass;
import com.leo.leomasapp.FavoriteFragment;
import com.leo.leomasapp.HistoryFragment;

import java.io.Serializable;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final DataClass data;
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, DataClass data) {
        super(fragmentActivity);
        this.data = data;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){
            case 0:
                DashboardFragment dashboardFragment = new DashboardFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("data", data);
                dashboardFragment.setArguments(bundle);
                return dashboardFragment;
            case 1:
                return new HistoryFragment();
            case 2:
                return new FavoriteFragment();
            case 3:
                AccountFragment accountFragment = new AccountFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("data", data);
                accountFragment.setArguments(bundle1);
                return accountFragment;
        }
        return new DashboardFragment();
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
