package com.rpl6.foodmates;

import androidx.appcompat.app.AppCompatActivity;

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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class BookDetailActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    Calendar waktuAwal = Calendar.getInstance();
    Calendar waktuAkhir = Calendar.getInstance();

    private EditText edtDate, edtStart, edtEnd;
    private Button btnBook;
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        edtDate = findViewById(R.id.book_date);
        edtStart = findViewById(R.id.time_start);
        edtEnd = findViewById(R.id.time_end);
        btnBook = findViewById(R.id.btn_book);
        tvTotal = findViewById(R.id.total_harga);

        String myFormat = "HH:mm";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        final int extraSalary = getIntent().getIntExtra("salary", 0);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        tvTotal.setText(Integer.toString(total));
                    } else{
                        total = hour * extraSalary;
                        tvTotal.setText(Integer.toString(total));
                    }
                }else if(hour < 0){
                    Toast.makeText(BookDetailActivity.this, "Kebalik", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BookDetailActivity.this, "Minimal sejam", Toast.LENGTH_SHORT).show();
                }
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                edtStart.setText(sdf.format(myCalendar.getTime()));
                waktuAwal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                waktuAwal.set(Calendar.MINUTE, minute);
            }
        };


        final TimePickerDialog.OnTimeSetListener timeEnd = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                edtEnd.setText(sdf.format(myCalendar.getTime()));
                waktuAkhir.set(Calendar.HOUR_OF_DAY, hourOfDay);
                waktuAkhir.set(Calendar.MINUTE, minute);
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
                new TimePickerDialog(BookDetailActivity.this, time, waktuAwal.get(Calendar.HOUR_OF_DAY),
                        waktuAwal.get(Calendar.MINUTE), true).show();
            }
        });

        edtEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(BookDetailActivity.this, timeEnd, waktuAkhir.get(Calendar.HOUR_OF_DAY),
                        waktuAkhir.get(Calendar.MINUTE), true).show();

            }
        });

    }

    private void updateLabel(){
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

        edtDate.setText(sdf.format(myCalendar.getTime()));
    }


}
