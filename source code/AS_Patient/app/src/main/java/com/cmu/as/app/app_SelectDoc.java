package com.cmu.as.app;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import com.cmu.apointmentscedulerpatient.app.R;
import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.entities.doctor.Doctor;
import com.cmu.as.socket.PatSocket;

public class app_SelectDoc extends Activity {
    PatSocket patSocket;
    private Spinner city = null;
    private Spinner area = null;
    private TextView text1 = null;
    private ArrayList<ArrayList<String>> areaData = null;
    private ArrayAdapter<String> adapterEdu = null;
    private List<String> dataEdu = null;
    private ArrayAdapter<String> adapterArea = null;
    private ShakeListenerUtils shakeUtils;
    private SensorManager mSensorManager;
    private int docPos = -1;
    private int depPos = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_doc);
        patSocket = (PatSocket) getApplicationContext();
        this.city = (Spinner) super.findViewById(R.id.sel_doc_dept);

        city.setSelected(false);
        this.area = (Spinner) super.findViewById(R.id.sel_doc_doc);
        area.setSelected(false);

        shakeUtils = new ShakeListenerUtils(this);

        this.dataEdu = new ArrayList<String>();
        dataEdu = patSocket.getHosInerface().getDepts();
        areaData = new ArrayList<ArrayList<String>>();
        areaData = patSocket.getHosInerface().getDoctors();

        this.city = (Spinner) super.findViewById(R.id.sel_doc_dept);
        city.setSelected(false);
        this.adapterEdu = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, this.dataEdu);
        this.adapterEdu
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.city.setAdapter(this.adapterEdu);
        this.text1 = (TextView) super.findViewById(R.id.sel_doc_info);
        OnItemSelectedListenerImpl it = new OnItemSelectedListenerImpl();
        this.city.setOnItemSelectedListener(it);
        this.area.setOnItemSelectedListener(it);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //get sensor manager service
        mSensorManager = (SensorManager) this
                .getSystemService(Service.SENSOR_SERVICE);
        //accelerate sensor
        mSensorManager.registerListener(shakeUtils,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                //others like SENSOR_DELAY_UI、SENSOR_DELAY_FASTEST、SENSOR_DELAY_GAME
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause()
    {
        mSensorManager.unregisterListener(shakeUtils);
        super.onPause();
    }

    private class OnItemSelectedListenerImpl implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (parent.getId() == R.id.sel_doc_dept) {
                app_SelectDoc.this.adapterArea = new ArrayAdapter<String>(
                        app_SelectDoc.this,
                        android.R.layout.simple_spinner_item,
                        app_SelectDoc.this.areaData.get(position));
                app_SelectDoc.this.area
                        .setAdapter(app_SelectDoc.this.adapterArea);
                depPos = position;
            } else if (parent.getId() == R.id.sel_doc_doc) {
                docPos = position;

                String doctor = parent.getItemAtPosition(position).toString();
                patSocket.sendOutput(patSocket.packSocket1(3,doctor));
               patSocket.getDocInerface().setDoc((Doctor) patSocket.getInput());
                text1.setText("Name:" + patSocket.getDocInerface().getDocName() + "\n"
                        + "Speciality:" + patSocket.getDocInerface().getDocSpeciality() + "\n"
                        + "Phone:" + patSocket.getDocInerface().getDocPhone());
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) { //

        }

    }

    public void sel_doc_confirm(View view) {
        patSocket.sendOutput(patSocket.packSocket1(4,patSocket.getDocInerface().getDocID()));
        patSocket.getAppInterface().setAppList((ArrayList<Appointment>)patSocket.getInput());
        Intent intent = new Intent(this, app_SelectDate.class);
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

    public class ShakeListenerUtils implements SensorEventListener
    {
        private Activity context;


        public ShakeListenerUtils(Activity context)
        {
            super();
            this.context = context;
        }

        @Override
        public void onSensorChanged(SensorEvent event)
        {
            int sensorType = event.sensor.getType();
            //values[0]:X，values[1]：Y，values[2]：Z
            float[] values = event.values;

            if (sensorType == Sensor.TYPE_ACCELEROMETER)
            {

                if ((Math.abs(values[0]) > 17 || Math.abs(values[1]) > 17 || Math
                        .abs(values[2]) > 17))
                {

                    if ((++docPos) == patSocket.getHosInerface().getDoctors().get(depPos).size()){
                        docPos = 0;
                    }
                    area.setSelection(docPos);
                    docPos = docPos + 1;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
            //Do nothing.
        }

    }

}