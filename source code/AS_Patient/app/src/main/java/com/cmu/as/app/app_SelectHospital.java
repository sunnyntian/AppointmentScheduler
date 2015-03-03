package com.cmu.as.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.hospital.Hospital;
import com.cmu.as.socket.PatSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class app_SelectHospital extends ActionBarActivity {
    PatSocket patSocket;
//    Spinner distance;
    private ListView datalist = null;
    private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    private SimpleAdapter simpleAdapter = null;
    private ArrayList<String> hosNames = new ArrayList<String>();
    private ArrayList<String> hosDists = new ArrayList<String>();
    String latitude = "38";
    String longitude = "77";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_hospital);
        patSocket = (PatSocket) getApplicationContext();
        app_MyLocationListener location = new app_MyLocationListener(app_SelectHospital.this);

        // Check if GPS enabled
        if (location.canGetLocation()) {

            latitude = Double.toString(location.getLatitude());
            longitude = Double.toString(location.getLongitude());
            Log.i("", "latitude:" + latitude);
            Log.i("","longitude:"+longitude);
        }
        patSocket.sendOutput(patSocket.packSocket2(51,latitude, longitude));
        ArrayList<Hospital> selHos = (ArrayList< Hospital>)(patSocket.getInput());
        if (selHos.size() == 0){
            //show a toast
            Context context = getApplicationContext();
            CharSequence text = "Oops, no hospital found!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        patSocket.creatHosList(selHos);
        this.datalist = (ListView) super.findViewById(R.id.listhospital);
        hosNames = patSocket.getHosInerface().getHospitalNames();
        hosDists = patSocket.getHosInerface().getHospitalDistances();
        for (int x = 0; x < this.hosNames.size();x++){
            Map<String,String> map = new HashMap<String, String>();
            map.put("hosName", hosNames.get(x));
            map.put("hosDistance",hosDists.get(x));
            this.list.add(map);
        }
        this.simpleAdapter = new SimpleAdapter(this,this.list, R.layout.hospital_list,
                new String[]{"hosName","hosDistance"}, new int[]{R.id.hosname, R.id.hosdistance});
        this.datalist.setAdapter(this.simpleAdapter);
        this.datalist.setOnItemClickListener(new OnItemClickListenerImpl());
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //if user click on one of the songs in the list, get which song is selected
            //and pass song name to the player
            Map<String, String> map = (Map<String, String>) app_SelectHospital.this.
                    simpleAdapter.getItem(position);
            String hosName = map.get("hosName");
            String hosId = patSocket.getHosInerface().getHosIdByName(hosName);
            patSocket.sendOutput(patSocket.packSocket1(2,hosId));
            patSocket.getHosInerface().setDepts((ArrayList<String>)patSocket.getInput());
            patSocket.getHosInerface().setDoctors((ArrayList<ArrayList<String>>) patSocket.getInput());
            Intent it = new Intent(app_SelectHospital.this, app_SelectDoc.class);
            app_SelectHospital.this.startActivity(it);
            app_SelectHospital.this.finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_hospital, menu);
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

    public void msButton(View view) {
        patSocket.sendOutput(patSocket.packSocket1(50, ""));
        patSocket.getHosInerface().setHospitalList((ArrayList<Hospital>)(patSocket.getInput()));
        Intent intent = new Intent(this, app_SelectHosManually.class);
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
