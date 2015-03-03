package com.cmu.as.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.socket.PatSocket;


public class app_FutureActivity extends ActionBarActivity {
    PatSocket patSocket;
    ArrayList<ArrayList<String>> f_data;
    ArrayList<Appointment> app;

    private ListView f_datalist = null;
    private List<Map<String,String>> f_list = new ArrayList<Map<String,String>>();
    private SimpleAdapter simpleAdapter = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_future);
        patSocket = (PatSocket) getApplicationContext();
        app = new ArrayList<Appointment>();
        patSocket.sendOutput(patSocket.packSocket1(7, patSocket.getPatInerface().getPatID()));
        app = (ArrayList<Appointment>) patSocket.getInput();
        if (!app.isEmpty()) {
            patSocket.getAppInterface().setFuture(app);
            f_data = new ArrayList<ArrayList<String>>();
            int i = 0;
            for (Appointment ap : patSocket.getAppInterface().getFuture()) {
                ArrayList<String> temp1 = new ArrayList<String>();
                temp1.add(ap.getHosName());
                temp1.add(ap.getDocName());
                temp1.add(ap.getAppDate());
                temp1.add(ap.getAppTime());
                temp1.add(Integer.toString(i));
                f_data.add(temp1);
                i++;
            }
            this.f_datalist = (ListView) super.findViewById(R.id.future_datalist);
            for (int x = 0; x < this.f_data.size(); x++) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("f_hos", this.f_data.get(x).get(0));
                map.put("f_doc", this.f_data.get(x).get(1));
                map.put("f_date", this.f_data.get(x).get(2));
                map.put("f_time", this.f_data.get(x).get(3));
                map.put("appInd", this.f_data.get(x).get(4));
                this.f_list.add(map);
            }

            this.simpleAdapter = new SimpleAdapter(this, this.f_list, R.layout.future_data_list,
                    new String[]{"f_hos", "f_doc", "f_date", "f_time"}, new int[]{R.id.f_hos, R.id.f_doc, R.id.f_date, R.id.f_time});
            this.f_datalist.setAdapter(this.simpleAdapter);
            this.f_datalist.setOnItemClickListener(new OnItemClickListenerImpl());
        }
    }

    private class OnItemClickListenerImpl implements OnItemClickListener{
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, String> map = (Map<String, String>) app_FutureActivity.this.
                    simpleAdapter.getItem(position);
            String appInd = map.get("appInd");
            Bundle bundle = new Bundle();
            bundle.putString("appInd",appInd);
            Intent it = new Intent(app_FutureActivity.this, app_FutureDetailActivity.class);
            it.putExtras(bundle);
            app_FutureActivity.this.startActivity(it);
            app_FutureActivity.this.finish();
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
