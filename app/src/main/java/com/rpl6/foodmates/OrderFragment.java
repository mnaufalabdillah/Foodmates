package com.rpl6.foodmates;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {
    private static final String TAG = OrderFragment.class.getSimpleName();
    SessionManager sessionManager;
    String getEmail;

    private RecyclerView rvActiveOrd, rvPastOrd;
    List<Chef> listActiveOrd, listPastOrd;
    private static final String URL_READACTIVE = "http://87d155c6.ngrok.io/foodmates/read_activeorder.php";
    private static final String URL_READPAST = "http://87d155c6.ngrok.io/foodmates/read_pastorder.php";

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_order, container, false);
        rvActiveOrd = v.findViewById(R.id.recycler_activeorders);
        rvActiveOrd.setLayoutManager(new LinearLayoutManager(getActivity()));

        rvPastOrd = v.findViewById(R.id.recycler_pastorders);
        rvPastOrd.setLayoutManager(new LinearLayoutManager(getActivity()));

        Button pending = (Button) v.findViewById(R.id.btn_pending);

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), PendingActivity.class);
                startActivity(i);
            }
        });

        return v;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);
        listActiveOrd = new ArrayList<>();
        loadActiveOrd();
        listPastOrd = new ArrayList<>();
        loadPastOrd();

    }

       private void loadActiveOrd() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.ActiveOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                             if (success.equals("1")) {
                                 for (int i=0; i < jsonArray.length(); i++) {
                                     JSONObject object = jsonArray.getJSONObject(i);

                                     listActiveOrd.add(new Chef(
                                             object.getInt("id"),
                                             object.getString("nama"),
                                             object.getInt("umur"),
                                             object.getString("spesialisasi")
                                     ));
                                 }

                                 OrderFragmentAdapter adapter = new OrderFragmentAdapter(getActivity(), listActiveOrd);
                                 rvActiveOrd.setAdapter(adapter);
                             }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error Loading Orders" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Loading Orders" + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void loadPastOrd() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.PastOrder,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {
                                for (int i=0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    listPastOrd.add(new Chef(
                                            object.getInt("id"),
                                            object.getString("nama"),
                                            object.getInt("umur"),
                                            object.getString("spesialisasi")
                                    ));
                                }

                                OrderFragmentAdapter adapter = new OrderFragmentAdapter(getActivity(), listPastOrd);
                                rvPastOrd.setAdapter(adapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error Loading Orders" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Loading Orders" + error.toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
