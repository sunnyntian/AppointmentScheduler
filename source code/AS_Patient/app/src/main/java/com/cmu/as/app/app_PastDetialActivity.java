package com.cmu.as.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.socket.PatSocket;


public class app_PastDetialActivity extends ActionBarActivity {

    PatSocket patSocket;
    private TextView detail = null;
    private Button ret = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_past_detial);
        patSocket = (PatSocket) getApplicationContext();
        Bundle bundle = this.getIntent().getExtras();
        String appInd = bundle.getString("appInd");
        Log.i("", appInd);
        this.detail = (TextView) super.findViewById(R.id.p_detail);
        Appointment past = patSocket.getAppInterface().getPast().get(Integer.parseInt(appInd));
        Log.i("",past.getAppTime());
        this.detail.setText("Hospital:"+past.getHosName()+"\n"+
                "Doctor:"+past.getDocName()+"\n"+
                "Date:"+ past.getAppDate()+"\n"+
                "Time:"+past.getAppTime());
        this.ret = (Button) super.findViewById(R.id.p_ret);
        this.ret.setOnClickListener(new OnClickListenerImpl());
    }

    private class OnClickListenerImpl implements OnClickListener {

        //if user press return button, the current activity will be finished
        //and return to the previous activity
        //because startActivityForResult is used in the previous activity
        public void onClick(View v){
            Intent it = new Intent(app_PastDetialActivity.this, app_PastActivity.class);
            app_PastDetialActivity.this.startActivity(it);
            app_PastDetialActivity.this.finish();
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
