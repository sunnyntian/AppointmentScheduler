package com.cmu.as.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.socket.PatSocket;

import java.util.Calendar;


public class app_FutureDetailActivity extends ActionBarActivity {

    PatSocket patSocket;
    private TextView detail = null;
    private Button ret = null;
    private Button cancel = null;
    Appointment future;
    private Button setAlarmBtn;
    private AlarmManager alarmManager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_future_detail);
        patSocket = (PatSocket) getApplicationContext();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        setAlarmBtn = (Button) findViewById(R.id.main_btn_set);

        Bundle bundle = this.getIntent().getExtras();
        String appInd = bundle.getString("appInd");
        this.detail = (TextView) super.findViewById(R.id.f_detail);
        future = new Appointment();
        future =  patSocket.getAppInterface().getFuture().get(Integer.parseInt(appInd));
        this.detail.setText("Hospital:"+future.getHosName()+"\n"+
                "Doctor:"+future.getDocName()+"\n"+
                "Date:"+ future.getAppDate()+"\n"+
                "Time:"+future.getAppTime());
        this.ret = (Button) super.findViewById(R.id.f_ret);
        this.cancel = (Button) super.findViewById(R.id.f_cancel_apmt);
        this.ret.setOnClickListener(new OnClickListenerImpl());
        this.cancel.setOnClickListener(new OnClickCancel());
        this.setAlarmBtn.setOnClickListener(new OnAlarm());
    }

    private class OnAlarm implements OnClickListener{
        public void onClick(View v) {
            // current time of the device
            Calendar cal = Calendar.getInstance();
            // show time setting window
            new TimePickerDialog(app_FutureDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    // start widget
                    Intent intent = new Intent(app_FutureDetailActivity.this,
                            app_AlarmActivity.class);
                    // create PendingIntent object，wrap Intent
                    PendingIntent pi = PendingIntent.getActivity(
                            app_FutureDetailActivity.this, 0, intent, 0);
                    Calendar setCal = Calendar.getInstance();
                    // set timer time according to the user choice
                    setCal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    setCal.set(Calendar.MINUTE, minute);
                    // AlarmManager will start Calendar at the right time
                    alarmManager.set(AlarmManager.RTC_WAKEUP,
                            setCal.getTimeInMillis(), pi);
                    // show a toast
                    Toast.makeText(app_FutureDetailActivity.this, "Alarm set successfully!",
                            Toast.LENGTH_SHORT).show();
                }
            }, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true)
                    .show();
        }
    }

    private class OnClickListenerImpl implements OnClickListener {

        //if user press return button, the current activity will be finished
        //and return to the previous activity
        //because startActivityForResult is used in the previous activity
        public void onClick(View v){
            Intent it = new Intent(app_FutureDetailActivity.this, app_FutureActivity.class);
            app_FutureDetailActivity.this.startActivity(it);
            app_FutureDetailActivity.this.finish();
        }
    }
    private class OnClickCancel implements OnClickListener {

        //if user press return button, the current activity will be finished
        //and return to the previous activity
        //because startActivityForResult is used in the previous activity
        public void onClick(View v){
            patSocket.sendOutput(patSocket.packSocket2(8,patSocket.getPatInerface().getPatID(), future.getAppID()));
            if ((patSocket.getInput()).equals("1")){
                //show a toast
                Context context = getApplicationContext();
                CharSequence text = "Cancel success!";
                int duration = Toast.LENGTH_LONG;
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            Intent it = new Intent(app_FutureDetailActivity.this, app_FutureActivity.class);
            app_FutureDetailActivity.this.startActivity(it);
            app_FutureDetailActivity.this.finish();
        }
    }

    public void newTab(View view) {
        Intent intent = new Intent(this, app_SelectHospital.class);
        startActivity(intent);
    }

    public void past(View view){
        Intent intent = new Intent(this, app_PastActivity.class);
        startActivity(intent);

    }

    public void future(View view){
        Intent intent = new Intent(this, app_FutureActivity.class);
        startActivity(intent);

    }

    public void info(View view){
        Intent intent = new Intent(this, app_InfoActivity.class);
        startActivity(intent);

    }

}
