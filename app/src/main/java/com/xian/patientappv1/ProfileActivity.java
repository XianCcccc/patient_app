package com.xian.patientappv1;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.Bind;

public class ProfileActivity extends AppCompatActivity {

    Button btDob;
    int dob_year, dob_month, dob_day;
    static final int DILOG_ID = 0;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dob_year = 1998;
        dob_month = 8;
        dob_day = 12;

        btDob = (Button)findViewById(R.id.btn_dob);
        btDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DILOG_ID);
                //Log.i("test", "onclick");

            }
        });

    }





    @Override
    protected Dialog onCreateDialog(int id) {
        if(id == DILOG_ID)
            return new DatePickerDialog(this, datepickerListener, dob_year, dob_month, dob_day);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datepickerListener
            = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dob_year = year;
            dob_month = monthOfYear;
            dob_day = dayOfMonth;
            Calendar calendar = Calendar.getInstance();
            calendar.set(dob_year, dob_month, dob_day);


            btDob.setText(dateFormat.format(calendar.getTime()));
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home)
            NavUtils.navigateUpFromSameTask(this);
        return super.onOptionsItemSelected(item);
    }
}
