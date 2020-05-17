package com.rpl6.foodmates;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 //   private static final String TAG = OrderFragment.class.getSimpleName();
  //  SessionManager sessionManager;

    private RecyclerView rvActiveOrd;
    List<Chef> listActiveOrd;
 //   private static final String URL = "https://fa091e1c.ngrok.io/foodmates/read_activeorder.php";

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        rvActiveOrd = v.findViewById(R.id.recycler_activeorders);
        OrderFragmentAdapter orderFragmentAdapter = new OrderFragmentAdapter(getContext(), listActiveOrd);
        rvActiveOrd.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvActiveOrd.setAdapter(orderFragmentAdapter);
        return v;

        /*
        sessionManager = new SessionManager(v.getContext());
        HashMap<String, String> user = sessionManager.getUserDetail();
        String emailuser = user.get(sessionManager.EMAIL);
        loadActiveOrd(emailuser);
        */
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listActiveOrd = new ArrayList<>();
        listActiveOrd.add(new Chef(1, "Akhirnya", 31, "Marksman Specialist"));
        listActiveOrd.add(new Chef(2, "Bisa Lokal", 31, "Tank Specialist"));
    }

    /*   private void loadActiveOrd(final String emailuser) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String sucess = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                             if (sucess.equals("1")) {
                                 for (int i=0; i < jsonArray.length(); i++) {
                                     JSONObject object = jsonArray.getJSONObject(i);

                                     listActiveOrd.add(new Chef(
                                             object.getInt("id"),
                                             object.getString("nama"),
                                             object.getInt("umur"),
                                             object.getString("spesialisasi")
                                     ));
                                 }

                                 ChefAdapterFragment adapter = new ChefAdapterFragment(listActiveOrd, getView().getContext(), OrderFragment.this);
                                 rvActiveOrd.setAdapter(adapter);
                             }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getView().getContext(), "Error Loading Orders" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getView().getContext(), "Error Loading Orders" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected  Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailuser);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getView().getContext());
        requestQueue.add(stringRequest);
    }
*/

}
