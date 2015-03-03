package com.cmu.as.socket;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.StrictMode;
import com.cmu.as.entities.appointment.AppointmentInterface;
import com.cmu.as.entities.doctor.DoctorInterface;
import com.cmu.as.entities.hospital.Hospital;
import com.cmu.as.entities.hospital.HospitalInterface;
import com.cmu.as.entities.patient.Patient;
import com.cmu.as.entities.patient.PatientInterface;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tina on 14-4-18.
 */
public class PatSocket extends Application {
    private PatientInterface patInerface = null;
    private DoctorInterface docInterface = null;
    private AppointmentInterface appInterface = null;
    private HospitalInterface hosInterface = null;
    private Socket sock = null;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    String strHost = null;
    private static final boolean DEBUG = true;

    public Socket getSock(){
        return sock;
    }

    public PatientInterface getPatInerface(){
        return this.patInerface;
    }

    public DoctorInterface getDocInerface(){
        return this.docInterface;
    }

    public HospitalInterface getHosInerface(){
        return this.hosInterface;
    }

    public AppointmentInterface getAppInterface(){
        return this.appInterface;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public void onCreate(){

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
//            sock = new Socket("128.237.161.21", 8888);
            sock = new Socket("128.237.170.152", 8888);
        } catch (IOException e) {
            if (DEBUG) System.err.println("Unable to connect to " + strHost);
        }
        try {
            in = new ObjectInputStream(sock.getInputStream());  //get info from server with ObjectInputStream
            out = new ObjectOutputStream(sock.getOutputStream());  //send info to server with ObjectOutputStream
        } catch (IOException e) {
            if (DEBUG) System.err.println("Unable to obtain stream to/from " + strHost);
        }
        docInterface = new DoctorInterface();
        appInterface = new AppointmentInterface();

    }

    public void creatPatient (Patient pat) {
        patInerface = new PatientInterface(pat);
    }


    public void creatHosList (ArrayList<Hospital> hosList) {
        hosInterface = new HospitalInterface(hosList);
    }

    public Object getInput(){
        try {
            return (in.readObject());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void sendOutput(Object outObj){
        try {
            out.writeObject(outObj);  //send info to server using ObjectOutputStream
        }
        catch (IOException e){
            if (DEBUG) System.out.println("Error writing to " + strHost);
        }
    }

    public Map<Integer,ArrayList<String>> packSocket1 (int i, String st) {
        Map<Integer,ArrayList<String>> socksend = new HashMap<Integer,ArrayList<String>>();
        ArrayList<String> content  = new ArrayList<String>();
        content.add(st);
        socksend.put(i,content);
        return socksend;
    }

    public Map<Integer,ArrayList<String>> packSocket2 (int i, String st1, String st2) {
        Map<Integer,ArrayList<String>> socksend = new HashMap<Integer,ArrayList<String>>();
        ArrayList<String> content  = new ArrayList<String>();
        content.add(st1);
        content.add(st2);
        socksend.put(i,content);
        return socksend;
    }

    public Map<Integer,ArrayList<String>> packSocket3 (int i, String st1, String st2, String st3) {
        Map<Integer,ArrayList<String>> socksend = new HashMap<Integer,ArrayList<String>>();
        ArrayList<String> content  = new ArrayList<String>();
        content.add(st1);
        content.add(st2);
        content.add(st3);
        socksend.put(i,content);
        return socksend;
    }

    public Map<Integer,ArrayList<String>> packSocket4 (int i, String st1, String st2, String st3, String st4) {
        Map<Integer,ArrayList<String>> socksend = new HashMap<Integer,ArrayList<String>>();
        ArrayList<String> content  = new ArrayList<String>();
        content.add(st1);
        content.add(st2);
        content.add(st3);
        content.add(st4);
        socksend.put(i,content);
        return socksend;
    }

    public Map<Integer,ArrayList<String>> packSocket5 (int i, String st1, String st2, String st3, String st4, String st5) {
        Map<Integer,ArrayList<String>> socksend = new HashMap<Integer,ArrayList<String>>();
        ArrayList<String> content  = new ArrayList<String>();
        content.add(st1);
        content.add(st2);
        content.add(st3);
        content.add(st4);
        content.add(st5);
        socksend.put(i,content);
        return socksend;
    }

    public Map<Integer,ArrayList<String>> packSocket6 (int i, String st1, String st2, String st3, String st4, String st5, String st6) {
        Map<Integer,ArrayList<String>> socksend = new HashMap<Integer,ArrayList<String>>();
        ArrayList<String> content  = new ArrayList<String>();
        content.add(st1);
        content.add(st2);
        content.add(st3);
        content.add(st4);
        content.add(st5);
        content.add(st6);
        socksend.put(i,content);
        return socksend;
    }

    public void onTerminate(){
        try {
            in.close();
            out.close();
            sock.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

