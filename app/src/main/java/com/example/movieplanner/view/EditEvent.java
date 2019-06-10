package com.example.movieplanner.view;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movieplanner.R;
import com.example.movieplanner.controller.DatePickerListener;
import com.example.movieplanner.controller.DeleteEventListener;
import com.example.movieplanner.controller.EventEditSubmitListener;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.controller.TimePickerListener;
import com.example.movieplanner.model.AttendeesImpl;
import com.example.movieplanner.model.MovieImpl;
import com.example.movieplanner.viewmodel.EventModel;
import com.example.movieplanner.viewmodel.MovieModel;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class EditEvent extends AppCompatActivity implements Serializable {
    private EventModel eventModel;
    private String itemId;
    private EditText esdate;
    private EditText eedate;
    private EditText newstime;
    private EditText newendtime;
    private EditText origstarttime;
    private EditText originalendtime;
    private EditText location;
    private LinearLayout lly;
    private EditText addmovie;
    private TextView movies;
    private MovieModel movieModel;
    private EditText attendees;
    private AttendeesImpl a;
    private MovieImpl movieidresult;
    private Button submit;

    //Set action for menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //If user want to add a new event
            case R.id.addnewevent:
                Intent editItemIntent = new Intent(this, EditEvent.class);
                editItemIntent.putExtra(Intent.EXTRA_TEXT, "-1");
                this.startActivity(editItemIntent);
                return true;
                //If user want to add movie to an existing event
            case R.id.addmovie:
                Intent movieintent = new Intent(this, MovieList.class);
                this.startActivityForResult(movieintent,1);
                return true;
                //If user want to add attendee to an existing event
            case R.id.addattendee:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            6);
                }
                Intent intent =new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 2);
                return super.onOptionsItemSelected(item);
            //If user want to remove attendee from an existing event
            case R.id.removeattendee:
                if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            6);
                }
                Intent intent2 =new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent2, 3);
                return super.onOptionsItemSelected(item);

        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Get result from select movie action
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                addmovie.setText(movieModel.getmap().getValue().get(result).getTitle()+"");
                movieidresult=movieModel.getmap().getValue().get(result);
                submit.setOnClickListener(new EventEditSubmitListener(this,itemId,a,movieidresult,addmovie));
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
        //Get result from select attendees from add attendee from contacts action
        if(requestCode == 2){
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                String id = contactData.getLastPathSegment();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                Cursor emailCur = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                cursor.moveToFirst();
                emailCur.moveToFirst();
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID));
                String name=cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                int emailIdx = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                String email = emailCur.getString(emailIdx);
                a=new AttendeesImpl(email,name,number);
                eventModel.getmap().getValue().get(itemId).addatten(a);
                attendees.setText(attendees.getText()+name+",");
            }
        }
        //Get result from select attendees from remove attendee from contacts action
        if(requestCode == 3){
            if (resultCode == RESULT_OK) {
                //Open contacts view from phone
                Uri contactData = data.getData();
                String id = contactData.getLastPathSegment();
                Cursor cursor = getContentResolver().query(contactData, null, null, null, null);
                Cursor emailCur = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{id}, null);
                cursor.moveToFirst();
                emailCur.moveToFirst();
                //Get contacts details user selected
                String number = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NAME_RAW_CONTACT_ID));
                String name=cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                int emailIdx = emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                String email = emailCur.getString(emailIdx);
                eventModel.getmap().getValue().get(itemId).removeatten(email);
                //Set name of attendees user selected to current view
                String atd="";
                for(String ak:eventModel.getmap().getValue().get(itemId).getcontacts().keySet()){
                    atd=atd+eventModel.getmap().getValue().get(itemId).getcontacts().get(ak).getName()+",";
                }
                attendees.setText(atd);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        movieModel= ViewModelProviders.of(this).get(MovieModel.class);
//        eventModel=ViewModelProviders.of(this).get(EventModel.class);
        eventModel=SingletonClass.instance.getEventModel();
        lly=(LinearLayout)findViewById(R.id.linear1);
        SimpleDateFormat fm1 = new SimpleDateFormat("d/MM/yyyy");
        SimpleDateFormat fm2 = new SimpleDateFormat("h:mm:ss a");
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar2);
        setSupportActionBar(myToolbar);
        Intent intent = getIntent();
        //Get id of current event being edited
        itemId = intent.getExtras().get(Intent.EXTRA_TEXT).toString();
        Button deleteevent=(Button)findViewById(R.id.button_delete_event);
        deleteevent.setOnClickListener(new DeleteEventListener(this,itemId));
        EditText ename = (EditText) findViewById(R.id.ename);
        EditText evenue = (EditText) findViewById(R.id.evenue);
        esdate = (EditText) findViewById(R.id.esdate);
        //Get selected date from calander
        if(intent.getExtras().getString("dateselect")!=null)
            esdate.setText(intent.getExtras().getString("dateselect"));
        esdate.setOnTouchListener(new DatePickerListener(esdate,this));
        eedate = (EditText) findViewById(R.id.eedate);
        eedate.setOnTouchListener(new DatePickerListener(eedate,this));
        newstime=(EditText)findViewById(R.id.estime);
        newstime.setOnTouchListener(new TimePickerListener(newstime,this));
        newendtime=(EditText)findViewById(R.id.eendtime);
        newendtime.setOnTouchListener(new TimePickerListener(newendtime,this));
        origstarttime=(EditText) findViewById(R.id.estime);
        originalendtime=(EditText) findViewById(R.id.eendtime);
        EditText addid=(EditText)findViewById(R.id.addid);
        TextView showid=(TextView)findViewById(R.id.textView5);
        location=(EditText)findViewById(R.id.locationedit);
        movies=(TextView)findViewById(R.id.movietextview);
        addmovie=(EditText)findViewById(R.id.editmovie);

        //If id=-1 means user want to add new event, prevent user from doing delete event action
        if(itemId.equals("-1")){
            lly.removeView(findViewById(R.id.button_delete_event));
        }
        //If id!=-1 meas user want to edit a event alreay exstis in the dataset, intialise the view with current event
        else {
            if(eventModel.getmap().getValue().get(itemId).getMovie()!=null)
                movieidresult=eventModel.getmap().getValue().get(itemId).getMovie();
            ename.setText(eventModel.getmap().getValue().get(itemId).getTitle());
            evenue.setText(eventModel.getmap().getValue().get(itemId).getVenue());
            esdate.setText(fm1.format(eventModel.getmap().getValue().get(itemId).getSdate()));
            eedate.setText(fm1.format(eventModel.getmap().getValue().get(itemId).getEdate()));
            originalendtime.setText(fm2.format(eventModel.getmap().getValue().get(itemId).getEdate()));
            origstarttime.setText(fm2.format(eventModel.getmap().getValue().get(itemId).getSdate()));
            location.setText(eventModel.getmap().getValue().get(itemId).getLocation());
            addid.setText(eventModel.getmap().getValue().get(itemId).getId());
            //If there is a movie links to this event, pass movie title to the view
            if ((eventModel.getmap().getValue().get(itemId).getMovie())!=null) {
                addmovie.setText(eventModel.getmap().getValue().get(itemId).getMovie().getTitle());
            }
            //Get id of current item being edit, prevent user from changing id of this event
            itemId=addid.getText().toString();
            lly.removeView(findViewById(R.id.addid));
            lly.removeView(findViewById(R.id.textView5));
        }
        TextView attenlabel=(TextView)findViewById(R.id.edit_attendlabel);
        attendees=(EditText)findViewById(R.id.edit_attendees);
        submit=(Button)findViewById(R.id.submit);
        ListView ll=(ListView) findViewById(R.id.eventview);
        submit.setOnClickListener(new EventEditSubmitListener(this,itemId,a,movieidresult,addmovie));
        //Get number of attendees of this event, if no attendees then set attendees number to blank
        try {
            if (eventModel.getmap().getValue().get(itemId).getcontacts().size() != 0) {
                String atd = "";
                for (String ak : eventModel.getmap().getValue().get(itemId).getcontacts().keySet()) {
                    atd = atd + eventModel.getmap().getValue().get(itemId).getcontacts().get(ak).getName() + ",";
                }
                attendees.setText(atd);
            }
        }
        catch (NullPointerException e){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inedit, menu);
        return true;
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
