package application;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GUIController {

    @FXML
    private MenuItem menuRestart;

    @FXML
    private MenuItem menuExit;

    @FXML
    private TextArea ta_chatbotArea;

    @FXML
    private TextField tf_userArea;

    @FXML
    private Button bsend;

    @FXML
    private RadioButton rb1;

    @FXML
    private ToggleGroup rb;

    @FXML
    private RadioButton rb2;

    @FXML
    private RadioButton rb3;

    @FXML
    private RadioButton rb4;

    @FXML
    private RadioButton rb5;

    @FXML
    private RadioButton rb6;

    @FXML
    private RadioButton rb9;

    @FXML
    private RadioButton rb8;

    @FXML
    private RadioButton rb7;

    @FXML
    private RadioButton rb10;

    @FXML
    private Button bupdate;
    
    private String heslo = "heslo";

    @FXML
    void exit(ActionEvent event) {
    	Platform.exit();
    }

    @FXML
    void restart(ActionEvent event) {
    	initialize();
    }

    @FXML
    void sendMessageButton(ActionEvent event) {
    	if(tf_userArea.getText().equals(heslo)) {
    		bupdate.setVisible(true);
    	}
    	if(rb.getSelectedToggle() != null) {
    		actionWithRB();
    	}else if(!tf_userArea.getText().isEmpty()) {
    		actionWithTF();
    	}
    }

    private void actionWithTF() {	
    	// TODO Action with TextField
		
	}

	private void actionWithRB() {
		// TODO Action with RadioButtons
		
	}

	@FXML
    void sendMessageEnter(KeyEvent event) {
    	if(event.getCode().equals(KeyCode.ENTER)) {
    		bsend.fire();
    	}
    }

    @FXML
    void updateData(ActionEvent event) {
    	// TODO
    }
    
    @FXML
    void initialize() {
        
        bupdate.setVisible(false);
        ta_chatbotArea.setEditable(false);
        ta_chatbotArea.setWrapText(true);
        ta_chatbotArea.setText("Ahoj, já jsem ultra super výkoný chatbot pro objednávání telefonù. Chcete si s mojí pomocí koupit nìjaký telefon? ");
        rb1.setText("Ano");
        rb2.setText("Ne");
        rb3.setVisible(false);
        rb4.setVisible(false);
        rb5.setVisible(false);
        rb6.setVisible(false);
        rb7.setVisible(false);
        rb8.setVisible(false);
        rb9.setVisible(false);
        rb10.setVisible(false);
        tf_userArea.setEditable(false);
    
    }
    

}

