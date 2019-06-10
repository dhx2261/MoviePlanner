package com.example.movieplanner.datacontrol;

import android.content.Context;

import com.example.movieplanner.R;
import com.example.movieplanner.model.AttendeesImpl;
import com.example.movieplanner.model.EventImpl;
import com.example.movieplanner.model.MovieImpl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Haixiao Dai(s3678322)
 * Mobile Application Development Assignment 2
 */

public class JDBC {
    private static Context applicationContext;
    Map<String, EventImpl> events = new LinkedHashMap<String, EventImpl>();
    Map<String, MovieImpl> movies=new HashMap<String,MovieImpl>();
    Statement st;
    SimpleDateFormat fm = new SimpleDateFormat("d/MM/yyyy h:mm:ss a");

    public JDBC(){
        init();
    }

            public void init() {
                try {
                    File dbFile = applicationContext.getDatabasePath("test.db");
                    String db = "jdbc:sqldroid:" + applicationContext.getDatabasePath("test.db").getAbsolutePath();
                    Class.forName("org.sqldroid.SQLDroidDriver");
                    //If datbase doesn't exists, initialize database with two text files
                    if(!dbFile.exists()) {
                        Connection con = DriverManager.getConnection(db);
                        st = con.createStatement();
                        st.executeUpdate("CREATE TABLE if not exists Events(id varchar(50),title varchar(50),sdate varchar(100),edate varchar(100),venue varchar(100),location varchar(100), movie varchar(100), attendee varchar(10000),primary key(id))");
                        st.executeUpdate("create table if not exists Movies(id varchar(100),title varchar(100), year integer, poster varchar(100),primary key(id))");
                        Scanner sc = new Scanner(applicationContext.getResources().openRawResource(R.raw.events1));
                        Scanner sc2 = new Scanner(applicationContext.getResources().openRawResource(R.raw.movies1));
                        while (sc2.hasNextLine()) {
                            String input2 = sc2.nextLine();
                            String formatinput2 = input2.replaceAll("\"", "");
                            String[] ein2 = formatinput2.split(",");
                            String mid = ein2[0];
                            String mtitle = ein2[1];
                            Integer year = Integer.parseInt(ein2[2]);
                            String poster = ein2[3];
                            String query2 = "insert or ignore into Movies values('" + mid + "'," + "'" + mtitle + "'," + year.toString() + "," + "'" + poster + "')";
                            st.executeUpdate(query2);
                        }
                        sc2.close();
                        while (sc.hasNextLine()) {
                            String input1 = sc.nextLine();
                            input1 = input1.replaceAll("'", "");
                            String formatinput1 = input1.replaceAll("\"", "");
                            System.out.println(formatinput1);
                            String[] ein = formatinput1.split(",");
                            String id = ein[0];
                            String title = ein[1];
                            String sdate = ein[2];
                            String edate = ein[3];
                            String venue = ein[4];
                            String location = ein[5] + "," + ein[6];
                            String query = "insert or ignore into Events values('" + id + "'," + "'" + title + "'," + "'" + sdate + "'," + "'" + edate + "'," + "'" + venue + "'," + "'" + location + "'," + "null,"+"null)";
                            st.executeUpdate(query);
                        }
                        sc.close();
                    }
                    Connection con = DriverManager.getConnection(db);
                    st = con.createStatement();
                    ResultSet rs2=st.executeQuery("select * from Movies");
                    while(rs2.next()){
                        String mid=rs2.getString(1);
                        String mtitle=rs2.getString(2);
                        Integer year=rs2.getInt(3);
                        String poster=rs2.getString(4);
                        MovieImpl ma = new MovieImpl(mid, mtitle, year, poster);
                        movies.put(mid, ma);
                    }

                    ResultSet rs = st.executeQuery("Select * from Events" );
                    while(rs.next()) {
                        String id = rs.getString(1);
                        String title = rs.getString(2);
                        String sdate = rs.getString(3);
                        String edate = rs.getString(4);
                        String venue = rs.getString(5);
                        String location = rs.getString(6);
                        EventImpl eve = new EventImpl(id, title, fm.parse(sdate), fm.parse(edate), venue, location);
                        if (rs.getString(7) != null) {
                            eve.setMovie(movies.get(rs.getString(7)));
                        }
                        if (rs.getString(8) != null) {
                            String[] attendeestring = rs.getString(8).split("%next%");
                            for (int i = 0; i < attendeestring.length; i++) {
                                String[] attendeesub = attendeestring[i].split("/");
                                String attenemail = attendeesub[0];
                                String attenname = attendeesub[1];
                                String attenphone = attendeesub[2];
                                AttendeesImpl att = new AttendeesImpl(attenemail, attenname, attenphone);
                                eve.addatten(att);
                            }
                        }
                        events.put(id, eve);
                    }
                }
                catch(ClassNotFoundException | SQLException | ParseException e){

                }
            }


    public Map<String,EventImpl> geteventlist(){
        return events;
    }
    public Map<String,MovieImpl> getmovielist(){
        return movies;
    }

    public static JDBC getSingletonInstance(Context appContext) {
        if(applicationContext == null) {
            applicationContext = appContext;
        }
        return JDBC.LazyHolder.INSTANCE;
    }

    private static class LazyHolder {
        static final JDBC INSTANCE = new JDBC();

    }
}
