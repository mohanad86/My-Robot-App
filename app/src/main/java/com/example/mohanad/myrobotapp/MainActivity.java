package com.example.mohanad.myrobotapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    //This the function that request the url from the robot
    private void RobotDo(String action) {
        URL url = null;
        try {
            url = new URL("http://192.168.12.5:5000/"+action);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            Log.d("RobotDo", total.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }

    private void getBattery(){
        URL url = null;
        try {
            url = new URL("http://192.168.12.5:5000/batterystatus");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }

            try {
                JSONObject reader = new JSONObject(total.toString());
                TextView myAwesomeTextView = (TextView)findViewById(R.id.status);

                //in your OnCreate() method
                myAwesomeTextView.setText("enemy_right: "+Integer.toString(reader.getInt("enemy_right")) + "\nenemy_left: "+Integer.toString(reader.getInt("enemy_left")));
                Button leftButton = (Button) findViewById(R.id.left);
                if (reader.getInt("enemy_left")== 1) {
                    leftButton.setBackgroundColor(Color.RED);

                }
                else{
                    leftButton.setBackgroundColor(Color.BLUE);
                }
                Button rightButton = (Button) findViewById(R.id.right);
                if (reader.getInt("enemy_right")== 1) {
                    rightButton.setBackgroundColor(Color.RED);

                }
                else{
                    rightButton.setBackgroundColor(Color.BLUE);
                }
                Button goButton = (Button) findViewById(R.id.go);
                if (reader.getInt("enemy_left")== 1 & reader.getInt("enemy_right")== 1 ) {
                    goButton.setBackgroundColor(Color.RED);

                }
                else{
                    goButton.setBackgroundColor(Color.BLUE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Function request the camera feed
        final String webcam_url = "http://192.168.12.5:5000/static/camera.html";
        final WebView myWebView = (WebView) findViewById(R.id.webview);
        myWebView.loadUrl(webcam_url);



        Timer autoUpdate = new Timer();
        autoUpdate.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Actions goes here
                        myWebView.loadUrl(webcam_url);
                        getBattery();
                    }
                });
            }
        }, 0, 500);
        // Old function that if you want OnClick
        //Button button3 = (Button) findViewById(R.id.button3);
        //button3.setOnClickListener(new View.OnClickListener() {
        //  public void onClick(View v) {
        //    RobotDo("right");
        //}
        // });

        Button button = (Button) findViewById(R.id.go);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("go");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("stop");
                        break;
                }

                return true;
            }
        });
        final Button button1 = (Button) findViewById(R.id.left);
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("left");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("stop");
                        break;
                }

                return true;
            }
        });

        Button button2 = (Button) findViewById(R.id.back);
        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("back");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("stop");
                        break;
                }

                return true;
            }
        });
        Button button3 = (Button) findViewById(R.id.right);
        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("right");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("stop");
                        break;
                }

                return true;
            }
        });

        Button button4 = (Button) findViewById(R.id.blue);
        button4.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("blues");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("blues");
                        break;
                }

                return true;
            }

        });
        Button button5 = (Button) findViewById(R.id.red);
        button5.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("reds");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("reds");
                        break;
                }

                return true;
            }


        });
        Button button6 = (Button) findViewById(R.id.all);
        button6.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        RobotDo("lightall");
                        break;

                    case MotionEvent.ACTION_UP:
                        RobotDo("lightall");
                        break;
                }

                return true;
            }

        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
