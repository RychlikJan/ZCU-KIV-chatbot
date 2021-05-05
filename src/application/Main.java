package application;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		try {
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("/GUI.fxml"));
			VBox root = loader.load();
			Scene scene = new Scene(root,700,375);
			primaryStage.setMinHeight(400);
			primaryStage.setMinWidth(725);
			primaryStage.setMaxHeight(400);
			primaryStage.setMaxWidth(725);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Ultra super výkonný chatbot");
			primaryStage.show();
			System.out.println(root.getChildren());
			
			GUIController cont = loader.getController();
			
			root.setOnKeyPressed( keyEvent -> {
				if (keyEvent.getCode() == KeyCode.H && keyEvent.isControlDown()) {
					cont.admin();
				}
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	

}
