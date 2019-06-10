package com.example.movieplanner.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;

import com.example.movieplanner.R;
import com.example.movieplanner.datacontrol.SingletonClass;
import com.example.movieplanner.model.AttendeesImpl;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.model.MovieImpl;
import com.example.movieplanner.viewmodel.EventModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 1
 */

public class EventEditSubmitListener implements View.OnClickListener {

    private Activity activity;
    private EventModel model1;
    private String id;
    private AttendeesImpl a;
    private MovieImpl movie;
    private EditText movieedit;

    public EventEditSubmitListener(Activity activity, String id,AttendeesImpl a,MovieImpl movie, EditText movieedit){
        this.activity=activity;
        this.model1= SingletonClass.instance.getEventModel();
        this.id=id;
        this.a=a;
        this.movie=movie;
        this.movieedit=movieedit;
    }

    @Override
    public void onClick(View v){
            SimpleDateFormat fm = new SimpleDateFormat("d/MM/yyyy h:mm:ss a");
            //Initialize current view
            EditText ename = (EditText) activity.findViewById(R.id.ename);
            EditText evenue = (EditText) activity.findViewById(R.id.evenue);
            EditText esdate = (EditText) activity.findViewById(R.id.esdate);
            EditText eedate = (EditText) activity.findViewById(R.id.eedate);
            EditText addid = (EditText) activity.findViewById(R.id.addid);
            EditText estime=(EditText)activity.findViewById(R.id.estime);
            EditText eendtime=(EditText)activity.findViewById(R.id.eendtime);
            EditText locationedit=(EditText)activity.findViewById(R.id.locationedit);
            String newname=ename.getText().toString();
            String newvenue=evenue.getText().toString();
            String newsdate=esdate.getText().toString();
            String newedate=eedate.getText().toString();
            String newstime=estime.getText().toString();
            String newendtime=eendtime.getText().toString();
            String location=locationedit.getText().toString();
            try {
                //Prevent user fron submitting changes if some properties of the event are empty
                if (id.equals("") || newname.equals("") || newvenue.equals("") || newsdate.equals("") || newsdate.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage(R.string.sumbit_message).
                            setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.cancel();
                                }
                            }).
                            create().show();
                }
                //In the case user want to edit a event that already exists in the dataset
                else if (model1.getmap().getValue().containsKey(id)) {
                    model1.getmap().getValue().get(id).setTitle(newname);
                    model1.getmap().getValue().get(id).setVenue(newvenue);
                    model1.getmap().getValue().get(id).setSdate(fm.parse(newsdate + " " + newstime));
                    model1.getmap().getValue().get(id).setEdate(fm.parse(newedate + " " + newendtime));
                    model1.getmap().getValue().get(id).setLocation(location);
                    if(movieedit.getText().toString().equals("")||movie==null)
                        model1.getmap().getValue().get(id).setMovie(null);
                    else
                        model1.getmap().getValue().get(id).setMovie(movie);
//                    if(a!=null) {
//                        model1.getmap().getValue().get(id).addatten(a);
//                    }
                    activity.finish();
                }
                //In the case that user want to add a new event
                else {
                    //Prevent user from submitting a new event without an id
                    if(addid.getText().toString().equals("")){
                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                        builder.setMessage(R.string.sumbit_message).
                                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {
                                        dialog.cancel();
                                    }
                                }).
                                create().show();
                    }
                    //All information are provided by the user then create a new event
                    else {
                        String newid = addid.getText().toString();
                        EventImpl ne = new EventImpl(newid, newname, fm.parse(newsdate + " " + newstime), fm.parse(newedate + " " + newendtime), newvenue, location);
                        model1.getmap().getValue().put(newid, ne);
                        if (movieedit.getText().toString().equals("") || movie == null)
                            model1.getmap().getValue().get(newid).setMovie(null);
                        else
                            model1.getmap().getValue().get(newid).setMovie(movie);
                        if (a != null) {
                            model1.getmap().getValue().get(newid).addatten(a);
                        }
                        activity.finish();
                    }
                }
            }
            catch (ParseException e){

            }
    }
}
