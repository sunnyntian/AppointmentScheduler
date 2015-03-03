package com.cmu.as.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.socket.PatSocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class app_PastActivity extends Activity {
    PatSocket patSocket;
    ArrayList<Appointment> app;
    private ArrayList<ArrayList<String>> data;
    private ListView datalist = null;
    private List<Map<String,String>> list = new ArrayList<Map<String,String>>();
    private SimpleAdapter simpleAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_past);
        patSocket = (PatSocket) getApplicationContext();
        patSocket.sendOutput(patSocket.packSocket1(6, patSocket.getPatInerface().getPatID()));
        app = new ArrayList<Appointment>();
        app = (ArrayList<Appointment>) patSocket.getInput();
        if (!app.isEmpty()) {
            patSocket.getAppInterface().setPast(app);
            data = new ArrayList<ArrayList<String>>();
            int i = 0;
            for (Appointment ap : patSocket.getAppInterface().getPast()) {
                ArrayList<String> temp1 = new ArrayList<String>();
                temp1.add(ap.getHosName());
                temp1.add(ap.getDocName());
                temp1.add(ap.getAppDate());
                temp1.add(ap.getAppTime());
                temp1.add(Integer.toString(i));
                data.add(temp1);
                i++;
            }
            this.datalist = (ListView) super.findViewById(R.id.datalist);
            for (int x = 0; x < this.data.size(); x++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("hos", this.data.get(x).get(0));
                map.put("doc", this.data.get(x).get(1));
                map.put("date", this.data.get(x).get(2));
                map.put("time", this.data.get(x).get(3));
                map.put("appInd", this.data.get(x).get(4));
                this.list.add(map);
            }
            this.simpleAdapter = new SimpleAdapter(this, this.list, R.layout.data_list,
                    new String[]{"hos", "doc", "date", "time"}, new int[]{R.id.hos, R.id.doc, R.id.date, R.id.time});
            this.datalist.setAdapter(this.simpleAdapter);
            this.datalist.setOnItemClickListener(new OnItemClickListenerImpl());
        }
    }

    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> map = (Map<String, String>) app_PastActivity.this.
                    simpleAdapter.getItem(position);
            String appInd = map.get("appInd");
            Bundle bundle = new Bundle();
            bundle.putString("appInd",appInd);
            Intent it = new Intent(app_PastActivity.this, app_PastDetialActivity.class);
            it.putExtras(bundle);
            app_PastActivity.this.startActivity(it);
            app_PastActivity.this.finish();
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
