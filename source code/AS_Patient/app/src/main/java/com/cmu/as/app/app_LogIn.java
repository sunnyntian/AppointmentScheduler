package com.cmu.as.app;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.patient.Patient;
import com.cmu.as.socket.PatSocket;


public class app_LogIn extends ActionBarActivity {
    PatSocket patSocket;
    EditText id = null;
    EditText pw = null;
    private PointF startPoint = new PointF();
    RelativeLayout layOut = null;

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        this.layOut = (RelativeLayout) super.findViewById(R.id.log_in_layout);
        this.id = (EditText) super.findViewById(R.id.id);
        this.pw = (EditText) super.findViewById(R.id.pw);
        patSocket = (PatSocket) getApplicationContext();
        layOut.setOnTouchListener(new OnTouchListenerImpl());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logIn(View view) {

        patSocket.sendOutput(patSocket.packSocket2(1,id.getText().toString(),pw.getText().toString()));

        if (((Boolean)patSocket.getInput())) {
            patSocket.creatPatient((Patient) (patSocket.getInput()));
            Intent intent = new Intent(this, app_SelectHospital.class);
            startActivity(intent);
        } else {
            //show a toast
            Context context = getApplicationContext();
            CharSequence text = "Please try again!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    private class OnTouchListenerImpl implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Log.i("In touch","In touch");

            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    //save current point
                    startPoint.set(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_UP:
                    if ((startPoint.x - event.getX()) > 10f) {
                        Log.i("Finger", "Finger");
                        Intent intent = new Intent(app_LogIn.this, app_RegisterActivity.class);
                        startActivity(intent);
                    }
                    break;

                default:
                    break;
            }
            return true;
        }
    }

}
