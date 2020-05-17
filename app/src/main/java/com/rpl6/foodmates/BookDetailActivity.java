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

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.rpl6.foodmates.R.color.abuabu;

public class BookDetailActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    Calendar waktuAwal = Calendar.getInstance();
    Calendar waktuAkhir = Calendar.getInstance();

    private EditText edtDate, edtStart, edtEnd;
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

        edtDate = findViewById(R.id.book_date);
        edtStart = findViewById(R.id.time_start);
        edtEnd = findViewById(R.id.time_end);
        btnBook = findViewById(R.id.btn_book);
        tvTotal = findViewById(R.id.total_harga);

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
                Toast.makeText(BookDetailActivity.this, "Halo", Toast.LENGTH_SHORT).show();
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
                tvTotal.setText(Integer.toString(total));
                return true;
            } else{
                total = hour * extraSalary;
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


}
