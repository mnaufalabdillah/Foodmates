package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class ChefDetail extends AppCompatActivity {

    private TextView tvNama, tvUmur, tvSpesialisasi, tvSkill1, tvSkill2, tvSkill3, tvSalary;

    private static final String URL = "https://f3e00244.ngrok.io/foodmates/readchef.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_detail);

        tvNama = findViewById(R.id.tvNama);
        tvUmur = findViewById(R.id.tvUmur);
        tvSpesialisasi = findViewById(R.id.tvSpesialisasi);
        tvSkill1 = findViewById(R.id.tvSkill1);
        tvSkill2 = findViewById(R.id.tvSkill2);
        tvSkill3 = findViewById(R.id.tvSkill3);
        tvSalary = findViewById(R.id.tvSalary);

        loadChef();
    }

    private void loadChef(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            int Extraid = getIntent().getIntExtra("id", 0);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject chef = array.getJSONObject(i);
                                int id = chef.getInt("id");
                                if(Extraid == id){
                                    String nama = chef.getString("nama");
                                    String spesialisasi = chef.getString("spesialisasi");
                                    int umur = chef.getInt("umur");
                                    String skill1 = chef.getString("skill1");
                                    String skill2 = chef.getString("skill2");
                                    String skill3 = chef.getString("skill3");
                                    int salary = chef.getInt("salary");

                                    tvNama.setText(nama);
                                    tvUmur.setText(Integer.toString(umur));
                                    tvSpesialisasi.setText(spesialisasi);
                                    tvSkill1.setText(skill1);
                                    tvSkill2.setText(skill2);
                                    tvSkill3.setText(skill3);
                                    tvSalary.setText(Integer.toString(salary));
                                }

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
