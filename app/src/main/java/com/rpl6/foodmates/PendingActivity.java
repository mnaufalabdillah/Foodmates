package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PendingActivity extends AppCompatActivity {

    private static final String TAG = PendingActivity.class.getSimpleName();
    RecyclerView rvPendingOrd;
    List<Chef> listPendingOrd;
    SessionManager sessionManager;
    String getEmail;
    private static final String URL_READPENDING ="http://87d155c6.ngrok.io/foodmates/read_pendingorders.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pending);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);

        rvPendingOrd = findViewById(R.id.recycler_pendingorders);
        rvPendingOrd.setLayoutManager(new LinearLayoutManager(this));

        listPendingOrd = new ArrayList<>();

    }

    private void loadPendingOrders() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.PendingOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {

                                for (int i=0; i<jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    listPendingOrd.add(new Chef(
                                            object.getInt("id"),
                                            object.getString("nama"),
                                            object.getInt("umur"),
                                            object.getString("spesialisasi")
                                    ));
                                }
                                OrderFragmentAdapter adapter = new OrderFragmentAdapter(PendingActivity.this, listPendingOrd);
                                rvPendingOrd.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PendingActivity.this, "Error Reading Orders " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PendingActivity.this, "Error Reading Orders " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", getEmail);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPendingOrders();
    }

}