package com.example.movieplanner.datacontrol;

import android.content.Context;

import com.example.movieplanner.model.EventImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 2
 */

public class Savetodb implements Runnable {
    Context context;
    Map<String,EventImpl> events;

    public Savetodb(Context context,Map<String,EventImpl> events){
        this.context=context;
        this.events=events;
    }
    @Override
    public void run() {
        try {
            SimpleDateFormat fm = new SimpleDateFormat("d/MM/yyyy h:mm:ss a");
            String db = "jdbc:sqldroid:" + context.getDatabasePath("test.db").getAbsolutePath();
            Class.forName("org.sqldroid.SQLDroidDriver");
            Connection con = DriverManager.getConnection(db);
            Statement st = con.createStatement();
            st.executeUpdate("delete from Events");
            for (String keys : events.keySet()) {
                String share = "insert or ignore into Events values('" + keys + "'," + "'" + events.get(keys).getTitle() + "'," + "'" + fm.format(events.get(keys).getSdate()) + "'," + "'" + fm.format(events.get(keys).getEdate()) + "'," + "'" + events.get(keys).getVenue() + "'," + "'" + events.get(keys).getLocation() + "',";
                if (events.get(keys).getMovie() == null) {
                    share = share + "null,";
                } else {
                    share = share + "'" + events.get(keys).getMovie().getId() + "',";
                }
                if (events.get(keys).getcontacts().size() == 0) {
                    share=share+"null)";
                }
                else{
                    String constring="";
                    for(String k:events.get(keys).getcontacts().keySet()){
                        constring=constring+k+"/"+events.get(keys).getcontacts().get(k).getName()+"/"+events.get(keys).getcontacts().get(k).getPhone()+"%next%";
                    }
                    share=share+"'"+constring+"')";
                }
                st.executeUpdate(share);
            }
        }
        catch (ClassNotFoundException | SQLException e) {

        }
    }
}
