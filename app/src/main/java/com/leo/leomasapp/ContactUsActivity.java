package com.leo.leomasapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ContactUsActivity extends AppCompatActivity {
    LinearLayout WhatApps, Email, Call;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact_us);

        WhatApps= findViewById(R.id.whatsapps);
        Email= findViewById(R.id.email);
        Call= findViewById(R.id.call);
        back= findViewById(R.id.back);

        WhatApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+62 89657786880";
                String Message ="Hallo admin Leo Mas!,";
                String url = "https://api.whatsapp.com/send?phone="+number+ "&text=" + Uri.encode(Message);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                i.setPackage("com.whatsapp");
                startActivity(i);
            }
        });
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:rifkydewanistemga10@gmail.com"));
                startActivity(intent);
            }
        });
        Call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:089657786880"));
                startActivity(intent);
            }
        });

        back.setOnClickListener(v -> {
            onBackPressed();
        });

    }
}