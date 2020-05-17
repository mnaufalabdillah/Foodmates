package com.rpl6.foodmates;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class UserDetail extends AppCompatActivity {

    private static final String TAG = UserDetail.class.getSimpleName();
    String getId;
    private TextView fullname;
    private EditText Email, Password, Alamat, Umur;
    private static final String URL = "http://f71e4b44.ngrok.io/foodmates/userdetail.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_profile);

        fullname = findViewById(R.id.fullname);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        Alamat = findViewById(R.id.Alamat);
        Umur = findViewById(R.id.Umur);
        getUserDetail();
    }

    private void getUserDetail() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("nama").trim();
                                    String strEmail = object.getString("email").trim();
                                    String strPassword = object.getString("password").trim();
                                    int intUmur = object.getInt("umur");
                                    String strAlamat = object.getString("email").trim();

                                    fullname.setText(strName);
                                    Email.setText(strEmail);
                                    Password.setText(strPassword);
                                    Umur.setText(Integer.toString(intUmur));
                                    Alamat.setText(strAlamat);

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(UserDetail.this, "Error Reading Detail " + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(UserDetail.this, "Error Reading Detail " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}