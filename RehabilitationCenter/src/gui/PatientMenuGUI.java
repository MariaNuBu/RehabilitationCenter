package gui;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;
import db.sqlite.SQLiteManager;
import db.sqlite.SQLitePhysicalTherapistManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pojos.*;


public class PatientMenuGUI 
{
		private static DBManager db;
		private static PatientManager pm;
		private static PhysicalTherapistManager ptm;

		public static void registerPatient() throws Exception
		{
			db= new SQLiteManager();
			db.connect();
			pm = db.getPatientManager();
			ptm=db.getPhysicalTherapistManager();
			Stage window = new Stage();
			window.setTitle("Paralimpic's Rehabilitation Center");
			GridPane grid = new GridPane();
			grid.setPadding(new Insets(10,10,10,10));
			grid.setVgap(8);
			grid.setHgap(10);
			Button register=new Button("Register");
			GridPane.setConstraints(register, 1, 13);
			Label label1= new Label("Name");
			GridPane.setConstraints(label1, 0, 0);
			TextField nameField = new TextField();
			nameField.setPromptText("Daniel Caverzaschi");
			GridPane.setConstraints(nameField, 1, 0);
			Label label2= new Label("Address");
			GridPane.setConstraints(label2, 0, 4);
			TextField addressField = new TextField();
			addressField.setPromptText("Antonio Lopez");
			GridPane.setConstraints(addressField, 1, 4);
			Label label3=new Label("Email");
			GridPane.setConstraints(label3, 0, 2);
			TextField emailField = new TextField();
			emailField.setPromptText("danie@gmail.com");
			GridPane.setConstraints(emailField, 1, 2);
			Label label4= new Label("Phone");
			GridPane.setConstraints(label4, 0,3);
			TextField phoneField = new TextField();
			phoneField.setPromptText("678392838");
			GridPane.setConstraints(phoneField, 1, 3);
			Label label5= new Label("Date of birth");
			GridPane.setConstraints(label5, 0,1);
			TextField DOBField = new TextField();
			DOBField.setPromptText("2000-11-22");
			GridPane.setConstraints(DOBField, 1, 1);
			Label label6= new Label("Sport Type");
			GridPane.setConstraints(label6, 0,5);
			TextField sportField = new TextField();
			sportField.setPromptText("Tennis");
			GridPane.setConstraints(sportField, 1, 5);
			Label label7= new Label("Disability");
			GridPane.setConstraints(label7, 0,6);
			TextField disabilityField = new TextField();
			disabilityField.setPromptText("Blind");
			GridPane.setConstraints(disabilityField, 1, 6);
			Label label8= new Label("Diseases");
			GridPane.setConstraints(label8, 0,7);
			TextField diseasesField = new TextField();
			diseasesField.setPromptText("Chron");
			GridPane.setConstraints(diseasesField, 1, 7);
			Label label9= new Label("Allergies");
			GridPane.setConstraints(label9, 0,8);
			TextField allergiesField = new TextField();
			allergiesField.setPromptText("Polen");
			GridPane.setConstraints(allergiesField, 1, 8);
			Label label10= new Label("Surgeries");
			GridPane.setConstraints(label10, 0,9);
			TextField surgeriesField = new TextField();
			surgeriesField.setPromptText("Panchreatitis");
			GridPane.setConstraints(surgeriesField, 1, 9);
			Label label11= new Label("Weight(kg)");
			GridPane.setConstraints(label11, 0,10);
			TextField weightField = new TextField();
			weightField.setPromptText("54.50");
			GridPane.setConstraints(weightField, 1, 10);
			Label label12= new Label("Height(cm)");
			GridPane.setConstraints(label12, 0,11);
			TextField heightField = new TextField();
			heightField.setPromptText("158");
			GridPane.setConstraints(heightField, 1, 11);
			///*
			Label label13=new Label("PhysicalTherapist ID");
			GridPane.setConstraints(label13, 0,12);
			TextField ptidField = new TextField();
			ptidField.setPromptText("1");
			GridPane.setConstraints(ptidField, 1, 12);
			Button showPT=new Button("Show Physical Therapists");
			GridPane.setConstraints(showPT, 0, 13);
			//*/
			grid.getChildren().addAll(label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11,label12,label13,
					nameField,DOBField,addressField,emailField,phoneField,sportField,disabilityField,diseasesField,allergiesField,
					surgeriesField,heightField,weightField,register,showPT);
			/*
			ArrayList<TextField> data = new ArrayList();
			data.add(nameField);
			data.add(DOBField);
			data.add(addressField);
			data.add(emailField);
			data.add(phoneField);
			data.add(sportField);
			data.add(disabilityField);
			data.add(diseasesField);
			data.add(allergiesField);
			data.add(surgeriesField);
			data.add(weightField);
			data.add(heightField);
			*/
			//TODO vale aqui lo que quiero es que al darle al boton este salga una lista de los physiical therapist y qque asi lo pueda meter la id
			//y con el id llamamos al metodo de getPhysicalTherapist de manager y asi conseguimos el pt y con esos 3 ya llamamos al 
			//addPatientandMedicalHistory del manager de patient con el patient y la mh que hemos creado con el boton de register
			///*
			showPT.setOnAction(e-> 
			{
				ArrayList<PhysicalTherapist> pt= new ArrayList();
				//TODO este es el error, lo comento porque no se si da error hacer push con errores
				String sport=sportField.getText();
				pt=ptm.showPhysicalTherapists(sport);
				if (pt.isEmpty())
				{
					pt=ptm.showAllPhysicalTherapists();
				}
				VBox layout=new VBox();
				layout.setPadding(new Insets(20,20,20,20));
				layout.setAlignment(Pos.CENTER);
				Scene scene2=new Scene(layout,400,400);
				
				
			});
			//*/
			register.setOnAction(e->{
				String name = nameField.getText();
				String address=addressField.getText();
				String dob=DOBField.getText();
				//Date DOB=formatStringToSQLDate(dob);
				java.sql.Date DOB = new java.sql.Date(new Date(0).getTime());
				String email=emailField.getText();
				Integer phone=Integer.parseInt(phoneField.getText());
				String sport=sportField.getText();
				String disability=disabilityField.getText();
				Patient p = new Patient(name,address,DOB,phone,email,sport,disability);
				System.out.println(p);
				String diseases=diseasesField.getText();
				String allergies=allergiesField.getText();
				String surgeries=surgeriesField.getText();
				Float weight=Float.parseFloat(weightField.getText());
				Integer height=Integer.parseInt(heightField.getText());						
				MedicalHistory mh =new MedicalHistory(name,DOB,diseases,allergies,surgeries,weight,height);
				window.close();
			});
			
			
			Scene scene = new Scene (grid,400,550);
			window.setScene(scene);
			window.show();
		}
		/*
		public static java.sql.Date formatStringToSQLDate(String strDate) throws Exception
		{
		        Date utilDate = new Date(0); //DateFormat
		        SimpleDateFormat dfFormat = new SimpleDateFormat("dd.MM.yyyy"); // parse string into a DATE format      
		        utilDate = (java.sql.Date) dfFormat.parse(strDate); // convert a util.Date to milliseconds via its getTime() method         
		        long time = utilDate.getTime(); // get the long value of java.sql.Date 
		        java.sql.Date sqlDate = new java.sql.Date(time);
		        return sqlDate;   

		}
		*/
		

}
