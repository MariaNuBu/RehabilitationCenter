package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;



public class ControllerRegisterPatient implements Initializable
{
	private Button registerButton;
	private TextField name,surname,dob,email,phone,diseases,surgeries,allergies,sport,address,weight,height,disabilities,ptid;
	
	public ArrayList<String> clickRegisterButton()
	{
		String namePatient=name.getText();
		String surnamePatient=surname.getText();
		String DOB=dob.getText();
		String emailPatient=email.getText();
		String phonePatient=phone.getText();
		String disease=diseases.getText();
		String surgery=surgeries.getText();
		String allergy=allergies.getText();
		String sportType=sport.getText();
		String addressPatient=address.getText();
		String weightkg=weight.getText();
		String heightcm=height.getText();
		String disability=disabilities.getText();
		String PTID=ptid.getText();
		ArrayList <String>resultado = new ArrayList<String>();
		resultado.add(namePatient);
		resultado.add(surnamePatient);
		resultado.add(DOB);
		resultado.add(emailPatient);
		resultado.add(disease);
		resultado.add(surgery);
		resultado.add(allergy);
		resultado.add(sportType);
		resultado.add(addressPatient);
		resultado.add(weightkg);
		resultado.add(heightcm);
		resultado.add(disability);
		resultado.add(PTID);			
		return resultado;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}
