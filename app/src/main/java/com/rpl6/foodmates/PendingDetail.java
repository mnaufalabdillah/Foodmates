package com.rpl6.foodmates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PendingDetail extends AppCompatActivity {
    private TextView tvNama,tvUmur,tvSpesialisasi,tvSkill1,tvSkill2,tvSkill3,date,timeStart,timeEnd,total;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_cancel);

        tvNama = findViewById(R.id.tvNama);
        tvUmur = findViewById(R.id.tvUmur);
        tvSpesialisasi = findViewById(R.id.tvSpesialisasi);
        tvSkill1 = findViewById(R.id.tvSkill1);
        tvSkill2 = findViewById(R.id.tvSkill2);
        tvSkill3 = findViewById(R.id.tvSkill3);
        date = findViewById(R.id.tvDate);
        timeStart = findViewById(R.id.tvtimestr);
        timeEnd = findViewById(R.id.tvtimeend);
        total = findViewById(R.id.tvTotal);
        btnCancel = findViewById(R.id.btn_cancel);

        loadSelectedOrder();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
            }
        });

    }

    private void cancel() {
        final int Extraid = getIntent().getIntExtra("id",0);
        Toast.makeText(PendingDetail.this, "id: "+Extraid, Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.CancelPendingOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(PendingDetail.this, "Cancel Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(PendingDetail.this, HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PendingDetail.this, "Cancel Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PendingDetail.this, "Cancel Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", Integer.toString(Extraid));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void loadSelectedOrder() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.DetailPendingOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            int Extraid = getIntent().getIntExtra("id", 0);
                            Toast.makeText(PendingDetail.this, "id: "+Extraid, Toast.LENGTH_SHORT).show();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject pending = array.getJSONObject(i);
                                int id = pending.getInt("id");
                                if (Extraid == id) {
                                    String nama = pending.getString("nama");
                                    String spesialisasi = pending.getString("spesialisasi");
                                    int umur = pending.getInt("umur");
                                    String skill1 = pending.getString("skill1");
                                    String skill2 = pending.getString("skill2");
                                    String skill3 = pending.getString("skill3");
                                    int totalh = pending.getInt("total_harga");
                                    String status_order = pending.getString("status_order");


                                    if (status_order.equals("active")  || status_order.equals("done") ) {
                                        btnCancel.setVisibility(View.GONE);
                                    }

                                    tvNama.setText(nama);
                                    tvUmur.setText(Integer.toString(umur));
                                    tvSpesialisasi.setText(spesialisasi);
                                    tvSkill1.setText(skill1);
                                    tvSkill2.setText(skill2);
                                    tvSkill3.setText(skill3);
                                    total.setText(Integer.toString(totalh));


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PendingDetail.this, "Error " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PendingDetail.this, "Error " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
