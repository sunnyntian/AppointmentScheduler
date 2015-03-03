package com.cmu.as.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.socket.PatSocket;

import java.util.ArrayList;
import java.util.List;


public class app_SelectHosManually extends ActionBarActivity {
    PatSocket patSocket;
    Spinner sel_hos_man;
    private ArrayAdapter<String> adapterSel = null;
    private List<String> dataSel = null;
    private TextView sel_hos_text = null;
    String hosId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hos_manually);
        patSocket = (PatSocket) getApplicationContext();
        sel_hos_man = (Spinner) super.findViewById(R.id.sel_hos_man);
        sel_hos_text = (TextView) super.findViewById(R.id.sel_hos_text);

        sel_hos_man.setSelected(false);

        this.dataSel = new ArrayList<String>();
        dataSel = patSocket.getHosInerface().getHospitalNames();
        this.adapterSel = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, this.dataSel);
        this.adapterSel
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.sel_hos_man.setAdapter(this.adapterSel);
        this.sel_hos_man.setOnItemSelectedListener(new OnSelectedListenerImp());

    }
    private class OnSelectedListenerImp implements AdapterView.OnItemSelectedListener{
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (parent.getId() == R.id.sel_hos_man){
                String hos = parent.getItemAtPosition(position).toString();
                hosId = patSocket.getHosInerface().getHosIdByName(hos);
                int hosIndex = patSocket.getHosInerface().getHosIndexByName(hos);
                sel_hos_text.setText("Name: " + hos + "\n"
                        + "Address: " + patSocket.getHosInerface().getHosAdd(hosIndex) + "\n"
                        + "Phone:" + patSocket.getHosInerface().getHos(hosIndex).getHosPhone());
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_hos_manually, menu);
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
        patSocket.sendOutput(patSocket.packSocket1(2,hosId));
        patSocket.getHosInerface().setDepts((ArrayList<String>)patSocket.getInput());
        patSocket.getHosInerface().setDoctors((ArrayList<ArrayList<String>>) patSocket.getInput());
        Intent intent = new Intent(this, app_SelectDoc.class);
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
