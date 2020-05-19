package com.rpl6.foodmates;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class EditProfileFragment extends Fragment {

    private static final String TAG = EditProfileFragment.class.getSimpleName();
    private Button btn_logout, save;
    SessionManager sessionManager;
    String getemail;
    private EditText editnama, editemail, editalamat, editumur;
    private static final String URL_SAVE = "http://c196e879.ngrok.io/foodmates/updateuserdetail.php";
    private static final String URL = "http://c196e879.ngrok.io/foodmates/userdetail.php";

    public EditProfileFragment() {

    }

    @Override
    public View onCreateView (final LayoutInflater inflater, final ViewGroup container,
                              Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        editnama = view.findViewById(R.id.fullname);
        editemail = view.findViewById(R.id.Email);
        editalamat = view.findViewById(R.id.Alamat);
        editumur = view.findViewById(R.id.Umur);
        save = view.findViewById(R.id.btn_save);
        btn_logout = view.findViewById(R.id.btn_logout);
        btn_logout.setVisibility(View.GONE);

        getUserDetail();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    saveUserDetail();
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        HashMap<String, String> user = sessionManager.getUserDetail();
        getemail = user.get(sessionManager.EMAIL);

    }

    private void getUserDetail() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.rpl6.foodmates.URL.UserDetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("detail");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("nama").trim();
                                    String strEmail = object.getString("email").trim();
                                    int intUmur = object.getInt("umur");
                                    String strAlamat = object.getString("alamat").trim();

                                    editnama.setText(strName);
                                    editemail.setText(strEmail);
                                    editumur.setText(Integer.toString(intUmur));
                                    editalamat.setText(strAlamat);

                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "Error Reading Detail " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Error Reading Detail " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", getemail);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void saveUserDetail() {

        final String name = this.editnama.getText().toString().trim();
        final String email = this.editemail.getText().toString().trim();
        final String alamat = this.editalamat.getText().toString().trim();
        final String age = this.editumur.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Menyimpan...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.rpl6.foodmates.URL.UpdateUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                                sessionManager.createSession(email);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error Saving Detail " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error Saving Detail " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("nama", name);
                params.put("emailnew", email);
                params.put("alamat", alamat);
                params.put("umur", age);
                params.put("emailold", getemail);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
