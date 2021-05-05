package application;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class GUIController {

	@FXML
	private VBox vBox;
	
	@FXML
	private AnchorPane ap;
	
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
	
    @FXML
    private ImageView iv;

    @FXML
    private Button bleft;

    @FXML
    private Button bright;

    
	private String heslo = "heslo";
	private String name;
	private String lastName;
	private String phoneNumber;
	private String address;
	private String[] rbButtons = new String[10];
	private XMLReader xml;
	private int[] options;
	private int tfOption = 0;
	private int rbOption = 0;
	private int indexOfResult = 0;
	private int remainingNumberOfPhones;
	private boolean skip = false;
	private ArrayList<Integer> result = new ArrayList<Integer>();
	


	
	@FXML
	void exit(ActionEvent event) {
		Platform.exit();
	}

	@FXML
	void restart(ActionEvent event) {
		initialize();
		tfOption = 0;
		rbOption = 0;
		indexOfResult = 0;
		skip = false;
		result.clear();
	}

	@FXML
	void phoneView(MouseEvent event) {
		try {
		    Desktop.getDesktop().browse(new URL(xml.getPhones()[result.get(indexOfResult)][0][1]).toURI());
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (URISyntaxException e) {
		    e.printStackTrace();
		}
	}
	
	@FXML
	void left(ActionEvent event) {
		indexOfResult--;
		if(indexOfResult < 0) {
			indexOfResult = result.size() - 1;
		}
		String url = xml.getPhones()[result.get(indexOfResult)][1][1];
		Image image = new Image(url);
		iv.setImage(image);
	}
	
	@FXML
	void right(ActionEvent event) {
		indexOfResult++;
		if(indexOfResult > result.size() - 1) {
			indexOfResult = 0;
		}
		String url = xml.getPhones()[result.get(indexOfResult)][1][1];
		Image image = new Image(url);
		iv.setImage(image);
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
		if(rbOption > 7 && tfOption == 0) {
			rb1.setVisible(false);
			rb2.setVisible(false);
			rb3.setVisible(false);
			rb4.setVisible(false);
			rb5.setVisible(false);
			rb6.setVisible(false);
			rb7.setVisible(false);
			rb8.setVisible(false);
			rb9.setVisible(false);
			rb10.setVisible(false);
			iv.setDisable(false);
			iv.setVisible(true);
			tf_userArea.setEditable(true);
			bleft.setVisible(true);
			bright.setVisible(true);
			getResult();
			ta_chatbotArea.appendText("Vybrali jsme vám " + result.size());
			if(result.size() == 1) {
				ta_chatbotArea.appendText("mobilní telefon.");
			}else if(result.size() < 5) {
				ta_chatbotArea.appendText("mobilní telefony. Pro výbìr mezi nimi použijte prosím tlaèítka \"Pøedchozí\" a \"Další\". ");
			}else {
				ta_chatbotArea.appendText("mobilních telefonù. Pro výbìr mezi nimi použijte prosím tlaèítka \"Pøedchozí\" a \"Další\". ");
			}
			ta_chatbotArea.appendText("Telefon, který vidíte bude objednán. Abychom mohli úspìšnì vytvoøit objednávku Vašeho vysnìného telefonu, jsou tøeba ještì Vaše osobní údaje. Zadejte prosím vaše køestní jméno\n\n");
		}
	}

	
	
	public Button getBupdate() {
		return bupdate;
	}

	private void getResult() {
		for (int i = 0; i < options.length; i++) {
			if (options[i] == 1) {
				result.add(i);
			}
		}
		String url = xml.getPhones()[result.get(indexOfResult)][1][1];
		Image image = new Image(url);
		iv.setImage(image);
		
	}

	private void actionWithTF() {	
		switch (tfOption) {
		case 0:
			name = tf_userArea.getText();
			tf_userArea.clear();
			ta_chatbotArea.appendText("Jaké je vaše pøíjmení?\n\n");
			tfOption++;
			break;
		case 1:
			lastName = tf_userArea.getText();
			tf_userArea.clear();
			ta_chatbotArea.appendText("Jaké je vaše telefonní èíslo?\n\n");
			tfOption++;
			break;
		case 2:
			phoneNumber = tf_userArea.getText();
			tf_userArea.clear();
			ta_chatbotArea.appendText("Na jakou adresu chcete zboží zaslat?\n\n");
			tfOption++;
			break;
		case 3:
			address = tf_userArea.getText();
			tf_userArea.clear();
			createOrder();
			ta_chatbotArea.appendText("Gratuluju, Váš vysnìný telefon je objednán. V následujících dnech vám jej doruèí kurýr.\n\n");
			break;
		}

	}

	private void createOrder() {
		// TODO Auto-generated method stub		
		System.out.println("Jméno: " + name);
		System.out.println("Pøíjmení: " + lastName);
		System.out.println("Telefon: " + phoneNumber);
		System.out.println("Adresa: " + address);
	}

	private void actionWithRB() {
		switch (rbOption) {
		case 0:
			if(rb.getSelectedToggle().equals(rb1)) {
				findOptions("Konstrukce");	
				if(rbButtons[0] == null) {
					skip = true;
					rbOption++;
				}else {
					ta_chatbotArea.appendText("Výbornì, abychom Vám mohli vybrat skvìlý telefon, potøebujeme nejprve pár parametrù. Zvolte prosím jaká kontrukce by vám nejvíce vyhovovala\n\n");
					updateRB();
					rbOption++;
				}
			}else if(rb.getSelectedToggle().equals(rb2)) {
				ta_chatbotArea.appendText("Omlouvám se, ale jediné co umím je objednávání telefnù.\n\n");
			}
			break;
		case 1:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Konstrukce", rb.getSelectedToggle().getUserData().toString());
				if(remainingNumberOfPhones == 1) {
					rbOption = Integer.MAX_VALUE;
					System.out.println(remainingNumberOfPhones);
					break;
				}
			}
			findOptions("Výrobce");
			if(rbButtons[0] == null) {
				skip = true;
				rbOption++;
			}else {
				ta_chatbotArea.appendText("Teï je na øadì vybrat výrobce.\n\n");
				updateRB();
				rbOption++;
			}				
			break;
		case 2:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Výrobce", rb.getSelectedToggle().getUserData().toString());
				if(remainingNumberOfPhones == 1) {
					rbOption = Integer.MAX_VALUE;
					System.out.println(remainingNumberOfPhones);
					break;
				}
			}
			findOptions("Rozlišení displeje");
			if(rbButtons[0] == null) {
				skip = true;
				rbOption++;
			}else {
				ta_chatbotArea.appendText("Teï je na øadì vybrat rozlišení displeje.\n\n");
				updateRB();
				rbOption++;
			}
			break;
		case 3:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Rozlišení displeje", rb.getSelectedToggle().getUserData().toString());
				if(remainingNumberOfPhones == 1) {
					rbOption = Integer.MAX_VALUE;
					System.out.println(remainingNumberOfPhones);
					break;
				}
			}
			findOptions("Rozlišení fotoaparátu");
			if(rbButtons[0] == null) {
				skip = true;
				rbOption++;
			}else {
				ta_chatbotArea.appendText("Teï je na øadì vybrat rozlišení fotoaparátu.\n\n");
				updateRB();
				rbOption++;
			}			
			break;
		case 4:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Rozlišení fotoaparátu", rb.getSelectedToggle().getUserData().toString());
				if(remainingNumberOfPhones == 1) {
					rbOption = Integer.MAX_VALUE;
					System.out.println(remainingNumberOfPhones);
					break;
				}
			}
			findOptions("Pamì RAM");
			if(rbButtons[0] == null) {
				skip = true;
				rbOption++;
			}else {
				ta_chatbotArea.appendText("Jakou velkou pamì RAM chcete?\n\n");
				updateRB();
				rbOption++;
			}			
			break;
		case 5:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Pamì RAM", rb.getSelectedToggle().getUserData().toString());
				if(remainingNumberOfPhones == 1) {
					rbOption = Integer.MAX_VALUE;
					System.out.println(remainingNumberOfPhones);
					break;
				}
			}
			findOptions("Kapacita baterie");
			if(rbButtons[0] == null) {
				skip = true;
				rbOption++;
			}else {
				ta_chatbotArea.appendText("Jakou požadujete kapacitu baterie?\n\n");
				updateRB();
				rbOption++;
			}			
			break;
		case 6:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Kapacita baterie", rb.getSelectedToggle().getUserData().toString());
				if(remainingNumberOfPhones == 1) {
					rbOption = Integer.MAX_VALUE;
					System.out.println(remainingNumberOfPhones);
					break;
				}
			}
			findOptions("Typ displeje");
			if(rbButtons[0] == null) {
				skip = true;
				rbOption++;
			}else {
				ta_chatbotArea.appendText("Poledním parametrem, který musíte zvolit je typ displeje?\n\n");
				updateRB();
				rbOption++;
			}			
			break;
		case 7:
			if(skip) {
				skip = false;
			}else {
				updateOptions("Typ displeje", rb.getSelectedToggle().getUserData().toString());
			}	
			rb.getSelectedToggle().setSelected(false);
			System.out.println(remainingNumberOfPhones);
			rbOption++;
			return;
		
		}
		if(skip) {
			bsend.fire();
		}else {
			rb.getSelectedToggle().setSelected(false);
			
		}
		//Sem kdyžtak pøedèasný vykopnutí
		System.out.println(remainingNumberOfPhones);
		
	}
	

	private void updateRB() {
		rb1.setVisible(false);
		rb2.setVisible(false);
		rb3.setVisible(false);
		rb4.setVisible(false);
		rb5.setVisible(false);
		rb6.setVisible(false);
		rb7.setVisible(false);
		rb8.setVisible(false);
		rb9.setVisible(false);
		rb10.setVisible(false);
		
		for (int i = 0; i < rbButtons.length; i++) {
			if(rbButtons[i]==null) {
				break;
			}else {
				switch(i) {
				case 0:
					rb1.setText(rbButtons[i]);
					rb1.setVisible(true);
					rb1.setUserData(rbButtons[i]);
					break;
				case 1:
					rb2.setText(rbButtons[i]);
					rb2.setVisible(true);
					rb2.setUserData(rbButtons[i]);
					break;
				case 2:
					rb3.setText(rbButtons[i]);
					rb3.setVisible(true);
					rb3.setUserData(rbButtons[i]);
					break;
				case 3:
					rb4.setText(rbButtons[i]);
					rb4.setVisible(true);
					rb4.setUserData(rbButtons[i]);
					break;
				case 4:
					rb5.setText(rbButtons[i]);
					rb5.setVisible(true);
					rb5.setUserData(rbButtons[i]);
					break;
				case 5:
					rb6.setText(rbButtons[i]);
					rb6.setVisible(true);
					rb6.setUserData(rbButtons[i]);
					break;
				case 6:
					rb7.setText(rbButtons[i]);
					rb7.setVisible(true);
					rb7.setUserData(rbButtons[i]);
					break;
				case 7:
					rb8.setText(rbButtons[i]);
					rb8.setVisible(true);
					rb8.setUserData(rbButtons[i]);
					break;
				case 8:
					rb9.setText(rbButtons[i]);
					rb9.setVisible(true);
					rb9.setUserData(rbButtons[i]);
					break;
				case 9:
					rb10.setText(rbButtons[i]);
					rb10.setVisible(true);
					rb10.setUserData(rbButtons[i]);
					break;
				}
				rbButtons[i] = null;
			}
		}
	}
	
	private void updateOptions(String attr, String value) {
		for (int i = 0; i < options.length; i++) {
			if(options[i] == 1) {
				for (int j = 0; j < xml.getPhones()[i].length; j++) {
					if(xml.getPhones()[i][j][0] == null) {
						options[i]=0;
						remainingNumberOfPhones--;
						break;
					}else if(xml.getPhones()[i][j][0].equals(attr)){
						if(xml.getPhones()[i][j][1].equals(value)) {
							break;
						}else {
							options[i]=0;
							remainingNumberOfPhones--;
							break;
						}
					}
				}	
			}
		}
		

	}

	private void findOptions(String attr) {
		int i = 0;
//		for (int j = 0; j < xml.getPhones().length; j++) {
//			if(options[j] == 0) {
//				continue;
//			}
//			for (int k = 0; k < xml.getPhones()[j].length; k++) {
//				if(xml.getPhones()[j][k][0] == null) {
//					break;
//				}
//				if(xml.getPhones()[j][k][0].equals(attr)) {
//					for (String s : rbButtons) {
//						if(s == null) {
//							rbButtons[i] = xml.getPhones()[j][k][1];
//							i++;
//							break;
//						}else if(s.equals(xml.getPhones()[j][k][1])) {
//							break;
//						}
//					}
//					break;
//				}
//			}	
//		}
		HashMap<String, Integer> list = new HashMap<String, Integer>();
		for (int j = 0; j < xml.getPhones().length; j++) {
			if(options[j] == 0) {
				continue;
			}
			for (int k = 0; k < xml.getPhones()[j].length; k++) {
				if(xml.getPhones()[j][k][0] == null) {
					break;
				}
				if(xml.getPhones()[j][k][0].equals(attr)) {
					if(list.containsKey(xml.getPhones()[j][k][1])) {
						int value = list.get(xml.getPhones()[j][k][1])+1;
						list.replace(xml.getPhones()[j][k][1], value);
					}else{
						list.put(xml.getPhones()[j][k][1], 1);
					}
				break;
				}
			}	
		}
		
		Map<String, Integer> sortedList = sortByComparator(list, false);
		
		for(String key : sortedList.keySet()) {		
			//System.out.println("Key: " + key + "\nValue: " + sortedList.get(key) + "\n\n" );
			
			rbButtons[i] = key;
			i++;
			if(i >= rbButtons.length) {
				break;
			}
		}
		
	}
	
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order)
    {

        List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<String, Integer>>()
        {
            public int compare(Entry<String, Integer> o1,
                    Entry<String, Integer> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
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
		ta_chatbotArea.clear();
		ta_chatbotArea.appendText("Ahoj, já jsem ultra super výkonný chatbot pro objednávání telefonù. Chcete si s mojí pomocí koupit nìjaký telefon?\n\n");
		rb1.setText("Ano");
		rb2.setText("Ne");
		rb1.setVisible(true);
		rb1.setSelected(false);
		rb2.setVisible(true);
		rb2.setSelected(false);
		rb3.setVisible(false);
		rb3.setSelected(false);
		rb4.setVisible(false);
		rb4.setSelected(false);
		rb5.setVisible(false);
		rb5.setSelected(false);
		rb6.setVisible(false);
		rb6.setSelected(false);
		rb7.setVisible(false);
		rb7.setSelected(false);
		rb8.setVisible(false);
		rb8.setSelected(false);
		rb9.setVisible(false);
		rb9.setSelected(false);
		rb10.setVisible(false);
		rb10.setSelected(false);
		tf_userArea.setEditable(false);
		bleft.setVisible(false);
		bright.setVisible(false);
		iv.setDisable(true);
		iv.setVisible(false);
		initPhones();
	}

	private void initPhones() {
		xml = new XMLReader("data.xml");
		xml.readXML();	
		options = new int[xml.getPhones().length];
		Arrays.fill(options, 1);
		remainingNumberOfPhones = options.length;
		System.out.println(remainingNumberOfPhones);
	}
	@FXML
	public void admin() {
		bupdate.setVisible(true);;
	}
	
	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getAddress() {
		return address;
	}




}

