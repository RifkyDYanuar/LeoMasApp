package com.leo.leomasapp.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.leo.leomasapp.SignInFragment;
import com.leo.leomasapp.SignUpFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        if (position == 1) {
            return new SignUpFragment();
        }
        return new SignInFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
