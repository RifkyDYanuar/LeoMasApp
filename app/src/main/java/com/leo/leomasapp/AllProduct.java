package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.leo.leomasapp.Adapter.ViewPagerAllProductAdapter;
import com.leo.leomasapp.Data.ProductClass;

public class AllProduct extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_product);
        back = findViewById(R.id.back);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2= findViewById(R.id.viewpager2);
        tabLayout.addTab(tabLayout.newTab().setText("All"));
        tabLayout.addTab(tabLayout.newTab().setText("Rings"));
        tabLayout.addTab(tabLayout.newTab().setText("Necklace"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewPagerAllProductAdapter allProductAdapter = new ViewPagerAllProductAdapter(fragmentManager,getLifecycle());
        viewPager2.setAdapter(allProductAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllProduct.this, MainActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("email", getIntent().getStringExtra("email"));
                intent.putExtra("name", getIntent().getStringExtra("name"));
                intent.putExtra("target_page", 0);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

}