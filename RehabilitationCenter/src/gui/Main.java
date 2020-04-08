package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application 
{
	Scene scene1,scene2;
	@Override
	public void start(Stage window) throws Exception 
	{
		window.setTitle("primera aplicacion");
		Button b=new Button("Go to scene 2");
		b.setOnAction(e->window.setScene(scene2));
		StackPane layout=new StackPane();
		layout.getChildren().add(b);
		Scene scene1 = new Scene(layout,400,550);
		StackPane layout2=new StackPane();
		Button b2=new Button("Back to 1");
		b2.setOnAction(e->window.setScene(scene1));
		layout2.getChildren().add(b2);
		scene2=new Scene(layout2,600,300);
		window.setScene(scene1);
		window.show();
	}
	public static void main (String [] args)
	{
		launch(args);
	}

}
