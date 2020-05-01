package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RegisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new PilihAkunFragment()).commit();
    }
}
