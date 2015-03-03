package com.cmu.as.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.socket.PatSocket;
import java.util.ArrayList;
import java.util.List;


public class app_SelectDate extends ActionBarActivity {

    PatSocket patSocket;
    private Spinner sel_time = null;
    private ArrayAdapter<String> adapterTime = null;
    private List<String> dataTime = null;
    private String dataTimeString;
    private String time;
    private String appIDUse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_date);
        dataTime = new ArrayList<String>();
        patSocket = (PatSocket) getApplicationContext();
        for (int i = 0; i < patSocket.getAppInterface().getAppList().size(); i++){
//            Log.i("bula", patSocket.getAppInterface().getApp(i).getAppDate());

//            dataTimeString = patSocket.getAppInterface().getApp(i).getAppID()+","+
//                    patSocket.getAppInterface().getApp(i).getAppDate()+","+
//                    patSocket.getAppInterface().getApp(i).getAppTime();
            dataTimeString = patSocket.getAppInterface().getApp(i).getAppDate()+","+
                    patSocket.getAppInterface().getApp(i).getAppTime();

            dataTime.add(dataTimeString);
        }
        sel_time = (Spinner) super.findViewById(R.id.sel_time);
        adapterTime = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,dataTime);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sel_time.setAdapter(adapterTime);
        sel_time.setAdapter(adapterTime);
        sel_time.setOnItemSelectedListener(new OnItemSelected());
    }

    private class OnItemSelected implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (parent.getId() == R.id.sel_time){
                time = parent.getItemAtPosition(position).toString();
                appIDUse = patSocket.getAppInterface().getApp(position).getAppID();
            }
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_date, menu);
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
    public void confirm(View view) {
        String[] component = time.split(",");
//        patSocket.sendOutput(patSocket.packSocket2(5, component[0], patSocket.getPatInerface().getPatID()));
        patSocket.sendOutput(patSocket.packSocket2(5, appIDUse, patSocket.getPatInerface().getPatID()));
        if ((patSocket.getInput()).equals("1")){
            //show a toast
            Context context = getApplicationContext();
            CharSequence text = "Make new appointment success!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        Intent intent = new Intent(this, app_FutureActivity.class);
        startActivity(intent);
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
