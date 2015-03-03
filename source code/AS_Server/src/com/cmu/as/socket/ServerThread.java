package com.cmu.as.socket;


import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.entities.appointment.AppointmentInterface;
import com.cmu.as.entities.department.Department;
import com.cmu.as.entities.department.DepartmentInterface;
import com.cmu.as.entities.doctor.Doctor;
import com.cmu.as.entities.doctor.DoctorInterface;
import com.cmu.as.entities.hospital.Hospital;
import com.cmu.as.entities.hospital.HospitalInterface;
import com.cmu.as.entities.patient.*;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by SafenZhai on 4/19/14.
 */
public class ServerThread extends Thread {

    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInput in;
    private Object inObject;
    
    public ServerThread(Socket sock){
    	this.clientSocket = sock;
    }

    @SuppressWarnings("unchecked")
	public void run() {

        try {
            
            System.out.println("New Connection!");

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            int key = -1;
            ArrayList<String> value = null;
            
            while((inObject = in.readObject())!= null){
            	Map<Integer,ArrayList<String>> m = new HashMap<Integer,ArrayList<String>>();
            	m = (Map<Integer, ArrayList<String>>) inObject;
            
            	for (Map.Entry<Integer, ArrayList<String>> entry : m.entrySet()) {
            		key =  entry.getKey();
            		System.out.println("Choice:"+key);
            		value = entry.getValue();
            		

            		
            	}
            
            	switch(key){
            
            	case 1: //patient Login
            		String PatID = value.get(0);
            		System.out.println(PatID);
            		String PatPsw = value.get(1);
            		System.out.println(PatPsw);
            		PatientInterface PI = new PatientInterface();
            		Boolean result;
            		result = PI.logIn(PatID, PatPsw);
            		out.writeObject(result);
            		
            		if (result == true){
            		//send patient
            		
                    out.writeObject(PI.getPatient(PatID));
                    System.out.println("Send patient! ");
                    
            		}
            		
            		break;
            	case 50:
                	//send manually selected hospital

                    ArrayList<Hospital> MallHos = new ArrayList<Hospital>();
                    
                    HospitalInterface HI5 = new HospitalInterface();
                    
                    MallHos = HI5.getHospitalList();

                    out.writeObject(MallHos);
                    
                    System.out.println("Send all hospital! ");
                    
            		break;
            	
            	case 51:
            		
                    //send hospital within 3 miles
                    
                    Double lat = Double.parseDouble(value.get(0));
                    Double lon = Double.parseDouble(value.get(1));
                    
                    ArrayList<Hospital> hos = new ArrayList<Hospital>();
                    ArrayList<Hospital> allHos = new ArrayList<Hospital>();
                    
                    HospitalInterface HI = new HospitalInterface();
                    
                    allHos = HI.getHospitalList();
                    
                    for(Hospital p: allHos){
                    	Double hLat = Double.parseDouble(p.getHosLat());
                    	Double hLon = Double.parseDouble(p.getHosLon());
                    	
                    	double earthRadius = 3958.75;
                        double dLat = Math.toRadians(lat-hLat);
                        double dLng = Math.toRadians(lon-hLon);
                        double sindLat = Math.sin(dLat / 2);
                        double sindLng = Math.sin(dLng / 2);
                        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                                * Math.cos(Math.toRadians(hLat)) * Math.cos(Math.toRadians(lat));
                        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
                        double dist = earthRadius * c;
                        
                        DecimalFormat df = new DecimalFormat("0.00");
                        String num = df.format(dist);
                        
                        
                        p.setHosDis(num);
                        
                        System.out.print("Mile"+dist);
                        
                        System.out.println("Dis"+p.getHosDis());
                        System.out.println("Name"+p.getHosName());
                        if (dist <3)
                        {
                        	hos.add(p);
                        }

                    }
                    out.writeObject(hos);
                    System.out.println("Send hospital! ");
            		
            		
            		break;
            		
            	//Patient sign up
            	case 52:
            		
            		String patID52 = value.get(0);
            		String patName52 = value.get(1);
            		String patAge52 = value.get(2);
            		String patGen52 = value.get(3);
            		String patInsurance52 = value.get(4);
            		String patPsw52 = value.get(5);
            		
            		PatientInterface PI52 = new PatientInterface();
            		if(PI52.isExist(patID52)){
            			out.writeObject("0");
            		}
            		else{
            			Patient p52 = new Patient();
            			p52.setPatID(patID52);
            			p52.setPatName(patName52);
            			p52.setPatPsw(patPsw52);
            			p52.setPatAge(patAge52);
            			p52.setPatGender(patGen52);
            			p52.setPatInsurance(patInsurance52);
            			
            			PI52.insertPatient(p52);
            			out.writeObject("1");
            		}
            		
            		break;
            		
            		
            	//send department and doctor in the hospital 	
            	case 2:
            		String qHosID = value.get(0);
            		System.out.println(qHosID);
            		DepartmentInterface DI = new DepartmentInterface();
            		ArrayList<Department> depList = new ArrayList<Department>();
            		ArrayList<String> depName = new ArrayList<String>();
            		depList = DI.getDepList(qHosID);
            		
            		for(Department dep: depList){
            			depName.add(dep.getDepName());
            		}
            		
            		out.writeObject(depName);
            		
            		
            		for(String d: depName){
            			System.out.println("DepName:"+d);
            		}
            		
            		
            		System.out.println("Send department! ");
            		
            		ArrayList<Doctor> docList = new ArrayList<Doctor>();
            		ArrayList<ArrayList<String>> depDoc = new ArrayList<ArrayList<String>>();
        			DoctorInterface DoI = new DoctorInterface();
            		for(Department dep: depList){
                		ArrayList<String> docName = new ArrayList<String>();
            			docList = DoI.getDoctorList(dep.getDepID());
            			for(Doctor d: docList){
                    		docName.add(d.getDocName());
            			}
            			depDoc.add(docName);
            		}
            		
            		for(ArrayList<String> a: depDoc){
            			for(String s: a){
            				System.out.println("DocName:"+s);
            			}
            		}
            		
            		
            		out.writeObject(depDoc);
            		System.out.println("Send department doctor! ");
            		break;
            	
            	//send selected doctor 	
            	case 3:
            		String DocName = value.get(0);
            		System.out.println(DocName);
            		Doctor d = new Doctor();
            		DoctorInterface DocI = new DoctorInterface();
            		d = DocI.getDoctorByName(DocName);
            		
            		out.writeObject(d);
            		System.out.println("DocID:"+d.getDocID());
            		System.out.println("send Doctor!!!");
            		break;
            	
            	//send available appointment
            	case 4:
            		String DID = value.get(0);
            		System.out.println("DoctorID:"+DID);
            		ArrayList<Appointment> appList = new ArrayList<Appointment>();
            		AppointmentInterface AI = new AppointmentInterface();
            		appList = AI.getAppListByDoc(DID);
            		
            		for(Appointment a: appList){
            			System.out.println("App"+a.getAppID());
            		}
            		
            		out.writeObject(appList);
            		break;
            	
            	//update appointment mark unavailable and set patID
            	case 5:
            		String AppID = value.get(0);
            		String PatID2 = value.get(1); 
            		System.out.println("AppID"+AppID);
            		System.out.println("PatID"+PatID2);
            		AppointmentInterface AI4 = new AppointmentInterface();
            		AI4.updateAppMarkUnA(AppID, PatID2);
            		
            		String success = "1";
            		out.writeObject(success);
            		
            		break;
            	
            	//send past appointment
            	case 6:
            		String PID = value.get(0);
            		System.out.println("PatientID:"+PID);
            		ArrayList<Appointment> pastAppList = new ArrayList<Appointment>();
            		AppointmentInterface AI2 = new AppointmentInterface();
            		pastAppList = AI2.getAppListPast(PID);
            		
            		for(Appointment a: pastAppList){
            			System.out.println("App"+a.getAppID());
            		}
            		
            		out.writeObject(pastAppList);
            		break;
            	
            	//send future appointment
            	case 7:
            		String PID2 = value.get(0);
            		System.out.println("PatientID:"+PID2);
            		ArrayList<Appointment> futureAppList = new ArrayList<Appointment>();
            		AppointmentInterface AI3 = new AppointmentInterface();
            		futureAppList = AI3.getAppListFuture(PID2);
            		
            		for(Appointment a: futureAppList){
            			System.out.println("App"+a.getAppID());
            		}

            		out.writeObject(futureAppList);
            		break;
            	
            	//cancel appointment
            	case 8:
            		String PatID3 = value.get(0);
            		String AppID2 = value.get(1);
            		System.out.println("AppID2:"+AppID2);
            		AppointmentInterface AI5 = new AppointmentInterface();
            		AI5.cancelApp(AppID2, PatID3);
            		
            		String success2 = "1";
            		out.writeObject(success2);
            		
            		break;
                 	
            	//patient update
            	case 9:
            		String PatName = value.get(0);
            		String PatAge = value.get(1);
            		String PatGender = value.get(2);
            		String PatInsurance = value.get(3);
            		String PatID4 = value.get(4);
            		System.out.println("PatName"+PatName);
            		System.out.println("PatAge"+PatAge);
            		System.out.println("PatGender"+PatGender);
            		System.out.println("PatInsurance"+PatInsurance);
            		System.out.println("PatID4"+PatID4);
            		
            		Patient p2 = new Patient();
            		PatientInterface PI2 = new PatientInterface();
            		p2.setPatAge(PatAge);
            		p2.setPatID(PatID4);
            		p2.setPatGender(PatGender);
            		p2.setPatInsurance(PatInsurance);
            		p2.setPatName(PatName);
            		p2.setPatPsw(PI2.getPatient(PatID4).getPatPsw());
            		
            		PI2.updatePatient(p2);
            		
            		out.writeObject("1");
            		out.writeObject(p2);
            		            		
            		break;
            	
            		//doctor sign in
            	case 10:
            		System.out.println("In case 10");
            		
            		Boolean logIn = false;
            		
            		String docID = value.get(0);
            		String docPsw = value.get(1);
            		//System.out.println("receive docID+docPsw:"+docID+docPsw);
            		
            		DoctorInterface DocI2 = new DoctorInterface();
            		
            		logIn = DocI2.logIn(docID, docPsw);
            		//System.out.println("logIn Value"+logIn);
            		if(logIn){
            			out.writeObject("1");
            			System.out.println("write 1");
            			}
            		else{
            			out.writeObject("0");
            		}
            		
            		break;
            		
            		//Doctor add an appointment
            	case 11:
            		
            		System.out.println("In case 11!");
            		String docID11 = value.get(0);
            		String date = value.get(1);
            		String time = value.get(2);
            		
            		System.out.println("ID+Date+time:"+docID11+date+time);
            		AppointmentInterface AI11 = new AppointmentInterface();
            		
            		if(AI11.appExist(date, time,docID11))
            		{
            			out.writeObject("0");
            		}
            		
            		else{
            			out.writeObject("1");
            			
            			Appointment app11 = new Appointment();
            			app11.setAppAvailability("1");
            			app11.setAppDate(date);
            			app11.setAppTime(time);
            			app11.setAppID(docID11+date+time);
            			
            			app11.setDocID(docID11);
            			DoctorInterface DI11 = new DoctorInterface();
            			Doctor d11 = DI11.getDoctor(docID11);
            			app11.setDocName(d11.getDocName());
            			
            			DepartmentInterface DepI11 = new DepartmentInterface();
            			Department Dep11 = DepI11.getDeptByID(d11.getDepID());
            			
            			HospitalInterface HosI11 = new HospitalInterface();
            			Hospital Hos11 = HosI11.getHotByID(Dep11.getHosID());
            			            			
            			app11.setHosName(Hos11.getHosName());
            			
            			AI11.insertApp(app11);
            			
            		}
            		
            		break;
            		
            		//Doctor check all appointment
            	case 12:
            		
            		String DID12 = value.get(0);
            		System.out.println("DoctorID:"+DID12);
            		ArrayList<Appointment> appList12 = new ArrayList<Appointment>();
            		AppointmentInterface AI12 = new AppointmentInterface();
            		appList12 = AI12.getAppListForDoctor(DID12);
            		
            		for(Appointment a: appList12){
            			System.out.println("AppFforDoc"+a.getAppID());
            		}
            		
            		out.writeObject(appList12);
            		break;
            		
            		//Doctor checkin patient
            	case 13:
            		System.out.println("In case 13!!!!");
            		String PID13 = value.get(0);
            		String AID13 = value.get(1);
            		System.out.println("PID+AID"+PID13+AID13);
            		AppointmentInterface AI13 = new AppointmentInterface();
            		
            		AI13.checkinApp(AID13, PID13);
            		
            		break;
            		
            		//Doctor delete appointment
            	case 14:
            		System.out.println("In case 14!!!!");
            		String AID14 = value.get(0);
            		System.out.println("AID"+AID14);
            		AppointmentInterface AI14 = new AppointmentInterface();
            		AI14.deleteApp(AID14);
            		
            		break;
            		
            		//Doctor check patient info
            	case 15:
            		System.out.println("In case 15!!!!");
            		String PID15 = value.get(0);
            		System.out.println("PID"+PID15);
            		PatientInterface PI15 = new PatientInterface();
            		Patient P15 = PI15.getPatient(PID15);
            		out.writeObject(P15);
            		
            		break;
            		
            		
            		}
            	}
            
            
            out.close();
            in.close();


            clientSocket.close();

            out.close();
        } catch (UnknownHostException e1) {
            //System.out.println("A user exit the system");
        } catch (IOException e1) {
            System.out.println("A user exit the system");
        } catch (Exception e1) {
        }
    }

}
