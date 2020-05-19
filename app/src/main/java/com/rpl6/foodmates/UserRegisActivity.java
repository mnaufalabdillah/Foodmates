package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRegisActivity extends AppCompatActivity {

    private EditText email, password, namaLengkap, c_password, address, phoneNum, age;
    private Button btn_regist;
    private ProgressBar loading;
    private RadioGroup pilihjk;
    private RadioButton jkdipilih;
    private static String URL_REGIST = "http://c196e879.ngrok.io/foodmates/register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_regis);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        namaLengkap = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        phoneNum = findViewById(R.id.phonenumber);
        age = findViewById(R.id.age);
        pilihjk = findViewById(R.id.pilihjk);
        btn_regist = findViewById(R.id.btn_regis);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = pilihjk.getCheckedRadioButtonId();
                jkdipilih = findViewById(selectedId);
                Regist();
            }
        });
    }

    private void Regist(){
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);

        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String nama_lengkap = this.namaLengkap.getText().toString().trim();
        final String address = this.address.getText().toString().trim();
        final String no_telp = this.phoneNum.getText().toString().trim();
        final String umur = this.age.getText().toString().trim();
        final String jk = this.jkdipilih.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.UserRegis,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(UserRegisActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UserRegisActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserRegisActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserRegisActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);
                    }
                })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                params.put("nama_lengkap", nama_lengkap);
                params.put("umur", umur);
                params.put("jenis_kelamin", jk);
                params.put("alamat", address);
                params.put("no_telp", no_telp);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}
