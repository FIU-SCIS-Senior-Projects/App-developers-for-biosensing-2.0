package com.example.biosensing;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static android.content.ContentValues.TAG;

public class ConnectionClass {
    private String ip = "192.168.1.243";
    private String classs = "net.sourceforge.jtds.jdbc.Driver";
    private String db = "biosensor";
    private String un = "client";
    private String password = "bio";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("SQLERROR: DIDNT CONNECT", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("CLASSNOTFOUND", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        Log.i(TAG, "Connected to SQL Server");
        return conn;
    }
}
