package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
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
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.rpl6.foodmates.R.color.abuabu;

public class BookDetailActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    Calendar tanggal = Calendar.getInstance();
    Calendar waktuAwal = Calendar.getInstance();
    Calendar waktuAkhir = Calendar.getInstance();

    SessionManager sessionManager;
    String email, idc, idp;
    int idpay;
    int totalHarga;
    private static String URL = "http://c196e879.ngrok.io/foodmates/userdetail.php";
    private static String URL_Payment = "http://c196e879.ngrok.io/foodmates/payment.php";
    private static String URL_Order = "http://c196e879.ngrok.io/foodmates/postorder.php";

    private EditText edtDate, edtStart, edtEnd, edtNotes;
    private Button btnBook;
    private TextView tvTotal;


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (edtStart.getText().length() > 0 && edtEnd.getText().length() > 0) {
                Toast.makeText(BookDetailActivity.this, "From: " + edtStart.getText() + " Until: " + edtEnd.getText(), Toast.LENGTH_SHORT).show();
                Hitung();

                if (edtStart.getText().length() > 0 && edtEnd.getText().length() > 0 && edtDate.getText().length() > 0 && Hitung()) {
                    btnBook.setEnabled(true);
                    if (btnBook.isEnabled()) {
                        btnBook.setBackgroundResource(R.drawable.roundedbutton);
                    }
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        sessionManager = new SessionManager(this);
        email = sessionManager.getUserDetail().get("EMAIL");

        UserDetail(email);

        edtDate = findViewById(R.id.book_date);
        edtStart = findViewById(R.id.time_start);
        edtEnd = findViewById(R.id.time_end);
        btnBook = findViewById(R.id.btn_book);
        tvTotal = findViewById(R.id.total_harga);
        edtNotes = findViewById(R.id.edtNotes);

        btnBook.setEnabled(false);
        btnBook.setBackgroundResource(R.drawable.buttondisable);

        String myFormat = "HH:mm";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        edtDate.addTextChangedListener(textWatcher);
        edtStart.addTextChangedListener(textWatcher);
        edtEnd.addTextChangedListener(textWatcher);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Payment();
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                tanggal.set(Calendar.YEAR, year);
                tanggal.set(Calendar.MONTH, monthOfYear);
                tanggal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                waktuAwal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                waktuAwal.set(Calendar.MINUTE, minute);
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                edtStart.setText(sdf.format(myCalendar.getTime()));
            }
        };


        final TimePickerDialog.OnTimeSetListener timeEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                waktuAkhir.set(Calendar.HOUR_OF_DAY, hourOfDay);
                waktuAkhir.set(Calendar.MINUTE, minute);
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                edtEnd.setText(sdf.format(myCalendar.getTime()));
            }
        };

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(BookDetailActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        edtStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(BookDetailActivity.this, time, myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(BookDetailActivity.this, timeEnd, myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();

            }
        });

    }

    private void updateLabel(){
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        edtDate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean Hitung(){
        final int extraSalary = getIntent().getIntExtra("salary", 0);
        long diff = waktuAkhir.getTimeInMillis() - waktuAwal.getTimeInMillis();
        long diffInSec = TimeUnit.MILLISECONDS.toSeconds(diff);
        int hour = (int) (diffInSec / (60 * 60));
        int minremaining = (int) (diffInSec % (60 * 60));
        int min = (int) (minremaining / (60));
        int secondsRemaining = (int) (minremaining % (60));
        int total;

        if(hour > 0){
            if(min > 1){
                total = (hour + 1) * extraSalary;
                totalHarga = (hour + 1) * extraSalary;
                tvTotal.setText(Integer.toString(total));
                return true;
            } else{
                total = hour * extraSalary;
                totalHarga = hour * extraSalary;
                tvTotal.setText(Integer.toString(total));
                return true;
            }
        }else if(hour < 0){
            Toast.makeText(BookDetailActivity.this, "Kebalik", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            Toast.makeText(BookDetailActivity.this, "Minimal sejam", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void UserDetail(final String email) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.rpl6.foodmates.URL.UserDetail,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("detail");

                            if(success.equals("1")){
                                for(int i=0; i<jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id").trim();
                                    idp = object.getString("id").trim();

                                    Toast.makeText(BookDetailActivity.this, id, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDetailActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookDetailActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Payment() {
        final String totalHarga = String.valueOf(this.totalHarga);
        final String status = "pending".trim();
        final String email = this.email.trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.rpl6.foodmates.URL.Payment,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            int id = jsonObject.getInt("id");
                            idpay = jsonObject.getInt("id");

                            if(success.equals("1")){
                                Toast.makeText(BookDetailActivity.this, "Payment Pending!" + id, Toast.LENGTH_SHORT).show();
                                Order();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDetailActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookDetailActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("total_harga", totalHarga);
                params.put("payment_status", status);
                params.put("userEmail", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void Order() {
        SimpleDateFormat fmt1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fmt2 = new SimpleDateFormat("HH:mm:ss");
        final String userid = String.valueOf(this.idp);
        final String chefid = String.valueOf(this.getIntent().getIntExtra("idc", 0));
        final String date = fmt1.format(tanggal.getTime());
        final String timestart = fmt2.format(waktuAwal.getTime());
        final String timeend = fmt2.format(waktuAkhir.getTime());
        final String note = this.edtNotes.getText().toString().trim();
        final String idpay = String.valueOf(this.idpay);
        final String status = "pending".trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, com.rpl6.foodmates.URL.Order,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            int id = jsonObject.getInt("id");

                            if(success.equals("1")){
                                Toast.makeText(BookDetailActivity.this, "Order Pending! id: " + id, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookDetailActivity.this, "Error "+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookDetailActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userid", userid);
                params.put("chefid", chefid);
                params.put("date", date);
                params.put("timestart", timestart);
                params.put("timeend", timeend);
                params.put("note", note);
                params.put("idpay", idpay);
                params.put("status", status);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
