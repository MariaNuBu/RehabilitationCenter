package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application
{
	Button b;
	@Override
	public void start(Stage s) throws Exception 
	{
		s.setTitle("primera aplicacion");
		b=new Button();
		b.setText("Hola");
		StackPane layout=new StackPane();
		layout.getChildren().add(b);
		Scene scene = new Scene(layout,400,550);
		s.setScene(scene);
		s.show();
	}
	public static void main (String [] args)
	{
		launch(args);
	}

}
