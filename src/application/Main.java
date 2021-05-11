package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	GUIController cont;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			/****
			 * Maven run:
			 java -jar --module-path="C:\Users\JanRychlik\Downloads\openjfx-16_windows-x64_bin-sdk\javafx-sdk-16\lib" --add-modules="javafx.graphics,javafx.controls,javafx.fxml"  EITMDataMaven-1.0-SNAPSHOT-jar-with-dependencies.jar


			 *
			 */



			Pane root; //= FXMLLoader.load(getClass().getClassLoader().getResource("/GUI.fxml"));
			URL url = new File("C:\\Users\\JanRychlik\\Documents\\Projects\\IntegracniProjekt\\ZCU-KIV-chatbot\\src\\application\\GUI.fxml").toURI().toURL();
			root = FXMLLoader.load(url);



			Scene scene = new Scene(root,700,375);
			primaryStage.setMinHeight(400);
			primaryStage.setMinWidth(725);
			primaryStage.setMaxHeight(400);
			primaryStage.setMaxWidth(725);
			//scene.getStylesheets().add(getClass().getResource("C:\\Users\\JanRychlik\\Documents\\Projects\\IntegracniProjekt\\ZCU-KIV-chatbot\\src\\application\\application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ultra super výkonný chatbot");
			primaryStage.show();




			root.setOnKeyPressed( keyEvent -> {
				if (keyEvent.getCode() == KeyCode.H && keyEvent.isControlDown()) {

				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
