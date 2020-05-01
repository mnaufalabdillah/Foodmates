package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class RegisActivity extends AppCompatActivity {

    private ImageView imgUser, imgChef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        imgUser = findViewById(R.id.imgUser);
        imgChef = findViewById(R.id.imgChef);

        imgChef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegisActivity.this, "Belom ada gan, sabar", Toast.LENGTH_SHORT).show();
            }
        });

        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisActivity.this, UserRegisActivity.class);
                startActivity(i);
            }
        });

    }
}
