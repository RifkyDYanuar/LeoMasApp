package com.leo.leomasapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.leo.leomasapp.Adapter.ViewPagerAdapter;
import com.leo.leomasapp.Data.DataClass;

public class MainActivity extends AppCompatActivity {
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String Name = intent.getStringExtra("name");
        String Email = intent.getStringExtra("email");
        String Username = intent.getStringExtra("username");
        String Password = intent.getStringExtra("password");

        DataClass data = new DataClass(Name, Email, Username, Password);
        viewPager2 = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.menu_navbottom);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, data);
        viewPager2.setAdapter(adapter);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.dashboard) {
                    viewPager2.setCurrentItem(0);
                    return true;
                } else if (item.getItemId() == R.id.history) {
                    viewPager2.setCurrentItem(1);
                    return true;
                }else if(item.getItemId()== R.id.product){
                    Intent intent1 = new Intent(MainActivity.this, AllProduct.class);
                    startActivity(intent1);
                    return true;
                }else if(item.getItemId()== R.id.favorite){
                    viewPager2.setCurrentItem(2);
                    return true;
                }else if(item.getItemId()== R.id.account){
                    viewPager2.setCurrentItem(3);
                    return true;
                }
                return false;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.dashboard);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.history);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.favorite);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.account);
                        break;

                }
            }
        });
    }
}