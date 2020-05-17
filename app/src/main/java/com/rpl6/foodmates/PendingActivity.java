package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PendingActivity extends AppCompatActivity {

    RecyclerView rvPendingOrd;
    List<Chef> listPendingOrd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pending);

        rvPendingOrd = findViewById(R.id.recycler_pendingorders);

        listPendingOrd = new ArrayList<>();
        listPendingOrd.add(new Chef(1, "Kapten", 57, "Mage Specialist"));
        listPendingOrd.add(new Chef(2, "Franco", 22, "Tank Specialist"));
        listPendingOrd.add(new Chef(3, "Helcurt", 39, "Assasin Specialist"));
        listPendingOrd.add(new Chef(4, "Bambang", 17, "All Role Specialist"));
        OrderFragmentAdapter orderFragmentAdapter2 = new OrderFragmentAdapter(PendingActivity.this, listPendingOrd);
        rvPendingOrd.setLayoutManager(new LinearLayoutManager(this));
        rvPendingOrd.setAdapter(orderFragmentAdapter2);
    }

}
