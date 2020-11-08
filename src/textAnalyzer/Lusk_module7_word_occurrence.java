package textAnalyzer;

//Author Name: Matthew Lusk
//Date: 10/11/2020
//Program Name: Lusk_Text_Analyzer
//Purpose: Analyze the number of occurrences of words in the file and display the results in a GUI
//The program allows the user to specify how many of the top words they would like to display

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * The Lusk_module7_word_occurence extends application and creates a GUI interface
 * to display word occurrences based on user input
 * 
 * @author Matthew Lusk
 * @version 2.0
 * @since 10/11/2020
 *
 */
public class Lusk_module7_word_occurrence extends Application {
	//Create listview for displaying the top words
	private static ListView<String> topWordsList = new ListView<String>();
	
	@Override
	public void start(Stage mainStage) {

		
		//Initiate the map, the instance of the file analyzer class, and call the method to convert the file to a map
		HashMap<String, Integer> map = new HashMap<>();
		Lusk_Text_Analyzer parseFile = new Lusk_Text_Analyzer();
		try {
			map = parseFile.textFileToMap(new File("src/textAnalyzer/macbeth.txt"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			topWordsList.getItems().add("There was an issue loading the file");
		}



		//Call the method to sort the map and place it into a new linked map
		LinkedHashMap<String, Integer> sortedMap = parseFile.sortMapByValue(map);
		
		//Call the method to print out the top 20 words and pass the list into the updateUI method
		updateUiWords(parseFile.printTopWords(20, sortedMap));
		
		//Create the text to prompt the user for an input number
		Text numOfWordsLabel = new Text("Enter Number of Words to Count:");
		
		//Create a field that allows the user to enter a number of words to count
		TextField numOfWordsInput = new TextField();
		
		//Create a button to submit the input
		Button submitButton = new Button("Submit");
		
		//Create VBox with the number input elements
		VBox numberInputBox = new VBox(numOfWordsLabel,numOfWordsInput, submitButton);
		//Add top margin to the number input box and the submit button
		VBox.setMargin(numOfWordsInput, new Insets(5,0,0,0));
		VBox.setMargin(submitButton, new Insets(25,0,0,0));
		//Horizontally center the items in the numberInputBox VBox and add padding
		numberInputBox.setAlignment(Pos.CENTER);
		numberInputBox.setPadding(new Insets(20,10,20,20));
		
		//Create title text for the number of words and set the font properties
		Text topWordsText = new Text("Top 20 Words in Macbeth");
		topWordsText.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 15));
		
		//Create event handler for submit button press
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	//Clear the words list
		    	topWordsList.getItems().clear();
		    	//Create int for use with user input number
		    	int wordCount = 0;
		    	//Try to pull input number, catch exception if input other than int
		    	try {wordCount = Integer.valueOf(numOfWordsInput.getText());
		    	}
		    	catch(Exception eInput) {
		    		System.out.println("Invalid Input");
		    		eInput.printStackTrace();
		    	}
		    	//If the word count is greater than 0, set the title text and call the method to print the specified number of words
		    	if(wordCount > 0) {
		    		topWordsText.setText("Top " + wordCount + " Words in Macbeth");
		    		updateUiWords(parseFile.printTopWords(wordCount, sortedMap));
		    	}
		    	//If the number entered is invalid or less than zero, tell the user the input was invalid
		    	else {
		    		topWordsText.setText("");
		    		topWordsList.getItems().add("Invalid Input");
		    		topWordsList.getItems().add("Input must be a positive Integer");
		    	}
		    }
		});
		
		//Create a vbox with the word column elements and set padding
		VBox wordColumnBox = new VBox(topWordsText, topWordsList);
		wordColumnBox.setPadding(new Insets(20,20,20,10));
		//Specify the height and width of the topWordsList ListView
		topWordsList.setPrefHeight(500);
		topWordsList.setPrefWidth(350);
		//Add the two vbox's that contain the UI elements to the HBox
		HBox appBox = new HBox(numberInputBox, wordColumnBox);
		//Set the scene, title, and show the scene
		Scene mainScene = new Scene(appBox, 550, 550);
		mainStage.setTitle("Top Word Occurences");
		mainStage.setScene(mainScene);
		mainStage.show();
	}


	public static void main(String[] args) {
		//Launch Application
		launch(args);	
	}
	
	//Method to update the UI when the user inputs a number of words to display
	/**
	 * This method updates the UI to reflect the top words using a
	 * provided list of words
	 * 
	 * @param topWords a list with the top words
	 */
	public static void updateUiWords(List<String> topWords) {
		//Loop through the list of words and add them to the topWordsList listview
		for(int i = 0; i < topWords.size(); i++) {
		topWordsList.getItems().add(topWords.get(i));
		}
	}


}
