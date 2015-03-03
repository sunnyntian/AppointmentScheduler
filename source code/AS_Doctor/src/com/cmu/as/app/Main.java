package com.cmu.as.app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.cmu.as.entities.appointment.Appointment;
import com.cmu.as.entities.patient.Patient;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws UnknownHostException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		
		Scanner reader = new Scanner(System.in);
		String docID; //Doctor ID
		

		
		Socket socket = new Socket("localhost", 8888);
		ObjectOutputStream out;
		ObjectInputStream in;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        

		System.out.println("Welcome to the Appointment Scheduler System.");
		
		
		while(true){
			System.out.println("Please choose an option you want to do: 1.Log In 2.Exit");
			String beforeLogInChoice = reader.nextLine();

			if(beforeLogInChoice.equals("1"))
			{
			//login, 
				System.out.println("Please Log in.");

				String logIn = "0";
				
				System.out.println("Enter your Doc ID:");
				docID = reader.nextLine();
				System.out.println("Enter your password:");
				String userPsw = reader.nextLine();
				
		        Map<Integer,ArrayList<String>> m = new HashMap<Integer,ArrayList<String>>();
				ArrayList<String> value = new ArrayList<String>();
		        value.add(docID);
		        value.add(userPsw);

		        m.put(10, value);
		        
		       // System.out.println("Send id and psw:"+docID+userPsw);
		        
		        out.writeObject(m);
		        logIn = (String) in.readObject();
		       // System.out.println("logIn value :"+logIn);
		
				if(logIn.equals("0")){
					
					System.out.println("Wrong password or ID.");
				}
				else{
					System.out.println("------------------------------");
				System.out.println("You are Logged In!!");
								
				int option = 0; 
				
				while(option != 6){
					
					System.out.println("Please Choose your option:");
					System.out.println("1. Add an appointment.");
					System.out.println("2. Check all appointment.");
					System.out.println("3. Check in a patient.");
					System.out.println("4. Delete an available appointment.");
					System.out.println("5. Check info of an patient.");
					System.out.println("6. Log out");
					option = Integer.parseInt(reader.nextLine());
					//add app, delete app, check all booked app, check patient, update info
					
					if(option>6 || option<1){
						System.out.println("Wrong choice. Please choose again!");
					}
					else{
					switch(option){
					
					//Add appointment
					case 1:
				        Map<Integer,ArrayList<String>> m2 = new HashMap<Integer,ArrayList<String>>();
						ArrayList<String> value2 = new ArrayList<String>();
						
						value2.add(docID);
						System.out.println("Please enter the appointment date:(MMM-DD-YYYY)");
						String time = reader.nextLine();
						value2.add(time);
						System.out.println("Please enter the appointment time:(HH:MM)");
						String date = reader.nextLine();
						value2.add(date);
						
						m2.put(11, value2);
						
						out.writeObject(m2);
					    String feedback = (String) in.readObject();
						
					    if(feedback.equals("1")){
					    	System.out.println("Appointment successfully added!");
					    }
					    else
					    	System.out.println("Appointment already exist");
						
						break;

					
					
					case 2:
						
				        Map<Integer,ArrayList<String>> m3 = new HashMap<Integer,ArrayList<String>>();
						ArrayList<String> value3 = new ArrayList<String>();
						
						value3.add(docID);
						
						m3.put(12, value3);
						
						out.writeObject(m3);
					    @SuppressWarnings("unchecked")
						ArrayList<Appointment> docApp= (ArrayList<Appointment>) in.readObject();
					    
					    for(Appointment a: docApp){
					    	String checkin;
				    		if((a.getAppCheckIn()).equals("1")){
				    			checkin = "Past Appointment ";
				    			
				    		}
				    		else{
				    			checkin = "Future Appointment ";
				    		}

					    	if((a.getAppAvailability()).equals("0")){
					    		
					    	System.out.println("Appointment ID:"+a.getAppID()+" Date:"+a.getAppDate()+" Time:"+a.getAppTime()
					    			+" Booked by patient:"+" Patient ID:"+a.getPatID()+" "+checkin);
					    	}
					    	else
					    	{
						    	System.out.println("Appointment ID:"+a.getAppID()+" Date:"+a.getAppDate()+" Time:"+a.getAppTime()
						    			+" Available Appointmen "+checkin);

					    	}
					    }
						break;
						
						
						
					case 3:
						
				        Map<Integer,ArrayList<String>> m4 = new HashMap<Integer,ArrayList<String>>();
						ArrayList<String> value4 = new ArrayList<String>();
						
						System.out.println("Please input the Patient ID you want to check in: ");
						String PID3 = reader.nextLine();
						value4.add(PID3);
						
						System.out.println("Here are the available appointment for this patient: ");
						m4.put(7, value4);
						out.writeObject(m4);
						@SuppressWarnings("unchecked")
						ArrayList<Appointment> patFutureApp= (ArrayList<Appointment>) in.readObject();
						 for(Appointment a: patFutureApp){
							 System.out.println("Appointment ID:"+a.getAppID()+" Date:"+a.getAppDate()+" Time:"+a.getAppTime()
						    			+" Available Appointmen ");
						 }
						
					        Map<Integer,ArrayList<String>> m5 = new HashMap<Integer,ArrayList<String>>();
							ArrayList<String> value5 = new ArrayList<String>();

						System.out.println("Please input the Appointment ID you want to check in: ");
						
						value5.add(PID3);
						value5.add(reader.nextLine());
						
						m5.put(13, value5);
						
						out.writeObject(m5);
						
						System.out.println("Appointment checked In for the patient!");

						break;
						
						
						
					case 4:
				        Map<Integer,ArrayList<String>> m6 = new HashMap<Integer,ArrayList<String>>();
						ArrayList<String> value6 = new ArrayList<String>();
						
						System.out.println("Please input the Appointment ID you want to delete: ");
						String AID4 = reader.nextLine();
						value6.add(AID4);
						
						m6.put(14, value6);
						
						
						out.writeObject(m6);
						System.out.println("Appointment deleted!");
						
						
						break;
						
						
						
					case 5:
				        Map<Integer,ArrayList<String>> m7 = new HashMap<Integer,ArrayList<String>>();
						ArrayList<String> value7 = new ArrayList<String>();
						
						System.out.println("Please input the Patient ID: ");
						String PID5 = reader.nextLine();
						value7.add(PID5);
						
						m7.put(15, value7);
						
						
						out.writeObject(m7);
						Patient p5 = (Patient)in.readObject();
						
						System.out.println("Patient ID "+PID5+" Name:"+p5.getPatName()+" Gender:"+p5.getPatGender()+" Age:"+p5.getPatAge()+
								" Insurance:"+p5.getPatInsurance());
						
						break;
					case 7:
						
						System.out.println("You are logged out!");
						break;
					}
					
				}
				}
				}
				
				
			
			}
			
			else if(beforeLogInChoice.equals("2")){
				System.out.println("Thanks for using the system! Bye");
				break;
			}
			
			else
				System.out.println("Wrong Choice!");
			
		}
        socket.close();

        out.close();
        in.close();
        reader.close();

	}

}
