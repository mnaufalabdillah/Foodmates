package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SearchChefActivity extends AppCompatActivity {

    private RecyclerView rvChef;
    private ArrayList<Chef> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chef);

        rvChef = findViewById(R.id.recycler_search_chef);
        rvChef.setHasFixedSize(true);

        list.addAll(ChefData.getListData());
        showRecyclerList();
    }

    private void showRecyclerList(){
        rvChef.setLayoutManager(new LinearLayoutManager(this));
        ChefAdapter listHeroAdapter = new ChefAdapter(list);
        rvChef.setAdapter(listHeroAdapter);
    }
}
