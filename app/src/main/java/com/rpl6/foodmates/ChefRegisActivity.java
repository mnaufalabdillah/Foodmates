package com.rpl6.foodmates;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

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

public class ChefRegisActivity extends AppCompatActivity {

    private EditText name, email, password, c_password, addresschef, phoneNumchef, agechef;
    private Button btn_regis_chef;
    private RadioGroup pilihjkchef;
    private RadioButton jkdipilihchef;
    private Spinner spesialis, skill_1, skill_2, skill_3;
    private ProgressBar loading;
    private static String URL_REGIST = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chef_regis);

        loading = findViewById(R.id.loadingchef);
        name = findViewById(R.id.fullnamechef);
        email = findViewById(R.id.emailchef);
        password = findViewById(R.id.passwordchef);
        c_password = findViewById(R.id.c_passwordchef);
        addresschef = findViewById(R.id.addresschef);
        phoneNumchef = findViewById(R.id.phonenumberchef);
        agechef = findViewById(R.id.agechef);
        pilihjkchef = findViewById(R.id.pilihjkchef);
        btn_regis_chef = findViewById(R.id.btn_regischef);
        spesialis = findViewById(R.id.spinner_skills_chef);
        skill_1 = findViewById(R.id.spinner_skills1);
        skill_2 = findViewById(R.id.spinner_skills2);
        skill_3 = findViewById(R.id.spinner_skills3);

        btn_regis_chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = pilihjkchef.getCheckedRadioButtonId();
                jkdipilihchef = findViewById(selectedId);
                Regist();
            }
        });

    }

    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_regis_chef.setVisibility(View.GONE);

        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String nama_lengkap = this.name.getText().toString().trim();
        final String umur = this.agechef.getText().toString().trim();
        final String jenis_kelamin = this.jkdipilihchef.getText().toString().trim();
        final String no_telp = this.phoneNumchef.getText().toString().trim();
        final String alamat = this.addresschef.getText().toString().trim();
        final String spesialist = spesialis.getSelectedItem().toString().trim();
        final String skill1 = skill_1.getSelectedItem().toString().trim();
        final String skill2 = skill_2.getSelectedItem().toString().trim();
        final String skill3 = skill_3.getSelectedItem().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {
                                Toast.makeText(ChefRegisActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(ChefRegisActivity.this, LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChefRegisActivity.this, "Register Error! " + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regis_chef.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChefRegisActivity.this, "Register Error! " + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regis_chef.setVisibility(View.VISIBLE);
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
                params.put("jenis_kelamin", jenis_kelamin);
                params.put("alamat", alamat);
                params.put("no_telp", no_telp);
                params.put("spesialist", spesialist);
                params.put("skill1", skill1);
                params.put("skill2", skill2);
                params.put("skill3", skill3);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }
