package com.example.movieplanner.view;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.movieplanner.R;
import com.example.movieplanner.controller.CalanderAdapter;
import com.example.movieplanner.controller.CalanderClickListener;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class CalenderView extends AppCompatActivity {
    private GridView calander;
    private Spinner year;
    private Spinner month;
    private LinearLayout xingqi;
    private ConstraintLayout root;
    private Integer monthselect;
    private Integer yearselect;
    private MonthDisplayHelper monthDisplayHelper;
    private Context context;
    private Button addevent;
    private String date;
    private ListView eventbycal;
    private TextView dateselect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_view);
        context=this;
        //Get data from viewmodel
        monthselect=0;
        yearselect=2019;
        date="1";
        monthDisplayHelper=new MonthDisplayHelper(yearselect,monthselect,7);
        year=findViewById(R.id.calander_year);
        month=findViewById(R.id.calander_month);
        xingqi=findViewById(R.id.calendar_xingqi);
        calander=(GridView) findViewById(R.id.calendar_grid);
        addevent=(Button) findViewById(R.id.button_addeventonthisdate);
        dateselect=(TextView)findViewById(R.id.dateselected);
        //Initialize date to 1/01/2019
        dateselect.setText("1/01/2019");
        ArrayAdapter<CharSequence> monthadapter=ArrayAdapter.createFromResource(this,R.array.month,android.R.layout.simple_spinner_item);
        monthadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(monthadapter);
        ArrayAdapter<CharSequence> yearadapter=ArrayAdapter.createFromResource(this,R.array.year,android.R.layout.simple_spinner_item);
        year.setAdapter(yearadapter);
        //Update month when user select a month
        month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monthselect=position;
                monthDisplayHelper=new MonthDisplayHelper(yearselect,monthselect,7);
                calander.setAdapter(new CalanderAdapter(monthDisplayHelper));
                dateselect.setText(date+"/"+(position+1)+"/"+yearselect);
                Integer temmon=monthselect+1;
                String monthstring=temmon.toString();
                if(monthstring.length()==1)
                    monthstring="0"+monthstring;
                //Set action when user clicking a date on calander
                calander.setOnItemClickListener(new CalanderClickListener(context,monthstring,yearselect.toString(),eventbycal,dateselect,yearselect,monthselect));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //Update year when user select a year
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yearselect=Integer.parseInt(year.getItemAtPosition(position).toString());
                monthDisplayHelper=new MonthDisplayHelper(yearselect,monthselect,7);
                calander.setAdapter(new CalanderAdapter(monthDisplayHelper));
                dateselect.setText(date+"/"+(monthselect+1)+"/"+yearselect);
                Integer temmon=monthselect+1;
                String monthstring=temmon.toString();
                if(monthstring.length()==1)
                    monthstring="0"+monthstring;
                //Set action when user clicking a date on calander
                calander.setOnItemClickListener(new CalanderClickListener(context,monthstring,yearselect.toString(),eventbycal,dateselect,yearselect,monthselect));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        eventbycal=findViewById(R.id.eventviewoncalander);
        calander.setAdapter(new CalanderAdapter(monthDisplayHelper));
        Integer temmon=monthselect+1;
        String monthstring=temmon.toString();
        if(monthstring.length()==1)
            monthstring="0"+monthstring;
        //Set action when user clicking a date on calander
        calander.setOnItemClickListener(new CalanderClickListener(this,monthstring,yearselect.toString(),eventbycal,dateselect,yearselect,monthselect));
        addevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editItemIntent = new Intent(context, EditEvent.class);
                //Pass the date user selected to next view
                editItemIntent.putExtra(Intent.EXTRA_TEXT, "-1");
                editItemIntent.putExtra("dateselect",dateselect.getText().toString());
                //Start edit activity
                context.startActivity(editItemIntent);
            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


}
