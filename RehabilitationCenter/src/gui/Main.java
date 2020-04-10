package gui;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;
import db.sqlite.SQLiteManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pojos.Patient;

public class Main extends Application 
{
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	@Override
	public void start(Stage window) throws Exception 
	{
		db= new SQLiteManager();
		db.connect();
		db.createTables();
		pm = db.getPatient();
		ptm=db.getPhysicalTherapist();
		window.setTitle("Paralimpic's Rehabilitation Center");
		Button patient=new Button("Patient");
		Button doctor=new Button("Doctor");
		Button physicalTherapist= new Button("PhysicalTherapist");		
		VBox layout=new VBox();
		layout.setPadding(new Insets(20,20,20,20));
		patient.setOnAction(e-> 
		{
			try {
				PatientMenu.registerPatient();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			window.close();
		});
		layout.getChildren().addAll(patient,doctor,physicalTherapist);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new  Scene(layout,100,300);
		window.setScene(scene);
		window.show();
	}
	
	

	public static void main (String [] args)
	{
		launch(args);
	}

}
