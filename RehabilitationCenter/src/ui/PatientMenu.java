package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.*;
import pojos.Appointment;
import pojos.Doctor;
import pojos.MedicalHistory;
import pojos.Patient;
import pojos.PhysicalTherapist;
import pojos.users.*;
import javax.xml.bind.*;
public class PatientMenu
{
	private BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public void readAppointments(Integer patID,PatientManager pm,PhysicalTherapistManager ptm,DoctorManager dm,AppointmentManager am)
	{
		System.out.println("----CURRENT APPOINTMENTS-----");
		List<Appointment> currentAppointments= am.getPatientsAppointments(patID, pm, ptm, dm);
		if(currentAppointments.isEmpty())
		{
			System.out.println("You don't have any appointments yet!");
		}
		else
		{
			for (Appointment appointment : currentAppointments)
			{
				System.out.println(appointment);
			}
		}
	}

	public void addAppointment(Integer patID,PatientManager pm,PhysicalTherapistManager ptm,DoctorManager dm,AppointmentManager am)
	{
		readAppointments(patID,pm,ptm,dm,am);
		System.out.println("----AVAILABLE DOCTORS----");
		List<Doctor>currentDoctors = dm.listDoctors();
		for(Doctor doctor:currentDoctors)
		{
			System.out.println("ID: "+doctor.getId()+" Name: "+doctor.getName()+" Specialty "+doctor.getSpeciality());
		}
		Integer docId = DataObtention.readInt("----CHOOSE THE DOCTORS ID ACCORDING TO THE SPECIALTY YOU NEED----");
		Patient p = pm.getPatient(patID);
		Doctor doc = dm.getDoctor(docId);
		PhysicalTherapist pT = p.getPhysicalTerapist();
		boolean inTable = pm.checkDoctor(patID, docId);
		if(inTable == false){
			pm.addDoctorToPatient(p,doc);
		}else{
		}
		Appointment appointment = null;
		boolean taken;
		try
		{
			appointment = introduceDateAndTime();
			List<Appointment> ptApps = am.getPhysicalTherapistAppointments(pT.getId(),pm, ptm, dm);
			taken = checkAppointments(ptApps, appointment);
			while(taken == true)
			{
				System.out.println("Please try with another appointment");
				appointment = introduceDateAndTime();
				taken = checkAppointments(ptApps, appointment);
			}
			List<Appointment>docApps = am.getDoctorsAppointments(doc.getId(),pm, ptm, dm);
			boolean taken2 = checkAppointments(docApps, appointment);
			while(taken2 == true)
			{
				System.out.println("Please try with another appointment");
				appointment = introduceDateAndTime();
				taken2 = checkAppointments(ptApps, appointment);
			}
			am.addAppointment(appointment, p, doc, pT);
			System.out.println("Appointment added successfully!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Error introducing the appointment");
		}
	}

	private static Appointment introduceDateAndTime() throws Exception{
		Boolean dateincorrect = true;
		Boolean timeincorrect = true;
		String dateString="";
		Date appointmentDate=null;
		String timeString="";
		Time appointmentTime=null;
		while(dateincorrect)
		{
			try
			{
				System.out.println("Introduce a date: yyyy-mm-dd");
				dateString = DataObtention.readLine();
				appointmentDate = Date.valueOf(LocalDate.parse(dateString, formatter));
				dateincorrect=false;
			}
			catch (DateTimeParseException e)
			{
				System.out.println("Date wrong introduced");
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Date wrong introduced");
			}
		}
		while(timeincorrect)
		{
			try
			{
				System.out.println("Introduce an hour: hh:mm:ss");
				timeString = DataObtention.readLine();
				appointmentTime = Time.valueOf(timeString);
				timeincorrect=false;
			}
			catch (DateTimeParseException e)
			{
				System.out.println("Time wrong introduced");
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Time wrong introduced");
			}
		}
		Appointment appointment = new Appointment(appointmentDate, appointmentTime);
		return appointment;
}

	private static boolean checkAppointments(List<Appointment>Apps, Appointment appointment) throws Exception{
		Date dateToCheck = appointment.getDate();
		Time timeToCheck = appointment.getTime();
		boolean taken = false;
		for(Appointment app: Apps){
			Date date = app.getDate();
			Time time = app.getTime();
			if(date.equals(dateToCheck) && time.equals(timeToCheck)){
				System.out.println("Ups, looks like this appointment is not available");
				taken = true;
			}
		}
		return taken;
	}

	public  void generateXML(Integer patID,PatientManager pm,AppointmentManager am,PhysicalTherapistManager ptm,DoctorManager dm) throws JAXBException {
		
		Patient p= pm.getPatient(patID);
		MedicalHistory mh= pm.getMedicalHistory(p);
		List<Appointment> appointments= am.getPatientsAppointments(patID, pm, ptm, dm);
		Patient patient2marshall=new Patient(p.getId(),p.getName(),p.getAddress(),p.getDob(),p.getPhoneNumber(),p.geteMail(),p.getSport(),p.getDisability(),mh,appointments);
		//Create JAXBContext
		JAXBContext context = JAXBContext.newInstance(Patient.class);
		//Get the marshaller
		Marshaller marshal = context.createMarshaller();
		//Pretty formatting
		marshal.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
		// Marshall the Patient to a file
		File file =new File("./xmls/Output-Patient.xml");
		marshal.marshal(patient2marshall,file);
		//Marshall the point to the screen
		marshal.marshal(patient2marshall,System.out);
	}
}
