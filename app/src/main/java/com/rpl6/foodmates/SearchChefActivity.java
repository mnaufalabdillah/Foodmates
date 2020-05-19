package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchChefActivity extends AppCompatActivity {

    private RecyclerView rvChef;
    List<Chef> chefList;

    private static final String URL = "http://c196e879.ngrok.io/foodmates/readchef.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_chef);

        rvChef = findViewById(R.id.recycler_search_chef);
        rvChef.setHasFixedSize(true);
        rvChef.setLayoutManager(new LinearLayoutManager(this));

        chefList = new ArrayList<>();

        loadChef();
    }

    /*
    private void showRecyclerList(){
        rvChef.setLayoutManager(new LinearLayoutManager(this));
        ChefAdapter listHeroAdapter = new ChefAdapter(list);
        rvChef.setAdapter(listHeroAdapter);
    }
     */

    private void loadChef(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, com.rpl6.foodmates.URL.readChef,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject chef = array.getJSONObject(i);

                                chefList.add(new Chef(
                                        chef.getInt("id"),
                                        chef.getString("nama"),
                                        chef.getInt("umur"),
                                        chef.getString("spesialisasi")
                                ));
                            }

                            ChefAdapter adapter = new ChefAdapter(SearchChefActivity.this, chefList);
                            rvChef.setAdapter(adapter);
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(SearchChefActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SearchChefActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
