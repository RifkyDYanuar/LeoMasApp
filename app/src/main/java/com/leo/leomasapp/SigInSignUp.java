package com.leo.leomasapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.leo.leomasapp.Adapter.FragmentAdapter;
import com.leo.leomasapp.Adapter.Resettable;
public class SigInSignUp extends AppCompatActivity {
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sig_in_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);
            return insets;
        });
        viewPager2 = findViewById(R.id.viewpager);

        FragmentAdapter adapter = new FragmentAdapter(this);
        viewPager2.setAdapter(adapter);
       viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
           @Override
           public void onPageSelected(int position) {
               super.onPageSelected(position);

               FragmentManager manager = getSupportFragmentManager();
               Fragment fragment = manager.findFragmentByTag("f" + viewPager2.getCurrentItem());
               ((Resettable)fragment).reset();
           }
       });

    }
}