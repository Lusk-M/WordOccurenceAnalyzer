//Author Name: Matthew Lusk
//Date: 10/15/2020
//Program Name: Lusk_module8_unit_test
//Purpose: Class to perform unit tests on the text analyzer class and ensure its methods are properly working

package textAnalyzer;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Scanner;

import org.junit.Test;


/**
 * The Lusk_module8_unit_test is a class that performs unit tests
 * on the methods from the Lusk_Text_Analyzer class
 * 
 * 
 * @author Matthew Lusk
 * @version 1.0
 * @since 10/18/2020
 */
public class Lusk_module8_unit_test {
	
	
	/**
	 * A jUnit test to test the method that converts a file to a HashMap
	 * this tests if the method properly splits the file and how it
	 * handles invalid files.
	 */
	@Test
	public void testFileToMap() {
			HashMap<String, Integer> map = new HashMap<>();
		
		//Initialize the scanner variable for use with the input file
				Scanner fileScanner = null;
				
				//Try to open a scanner, import the text file, and filter the file by characters 
				//that are not uppercase, lowercase, or apostrophes using REGEX.
				try {
					fileScanner = new Scanner(new File("src/textAnalyzer/macbeth.txt")).useDelimiter("[^a-zA-Z']+");//[\b\n\r\s-.?;:]+ [^a-zA-Z']+
				} catch (FileNotFoundException e) {
					System.out.println("There was an issue retrieving the file:\n");
					e.printStackTrace();
				}
				
				while (fileScanner.hasNext()){
					String nextWord = fileScanner.next().toLowerCase();
					
					//If the word exists in the map, increment the count
				    if (map.containsKey(nextWord)) {
				    	map.put(nextWord, map.get(nextWord) + 1);
				    }
				    //Create instance of new word in map
				    else {
				    	map.put(nextWord, 1);
				    }
				}
				
				fileScanner.close();
				
		Lusk_Text_Analyzer tester = new Lusk_Text_Analyzer();
		
		//Create instance of file with a non-existent file and the macbeth file
		File fileNone = new File("test.txt");
		File macbethFile = new File("src/textAnalyzer/macbeth.txt");
		
		//Test handling of invalid file by textFileToMap function to see if it throws the proper exception
		assertThrows(FileNotFoundException.class, () -> {
	        tester.textFileToMap(fileNone);
	    });
		
		//Test that the file is properly split by comparing the properly split file map length to the map length from the textFileToMap function
		try {
			assertEquals(map.size(), tester.textFileToMap(macbethFile).size(), "Properly splits file");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * a jUnit test to test that the method correctly trims the list to the specified value
	 * This tests if the method trims the list down to the specified size and how 
	 * it handles edge cases
	 */
	@Test
	public void testListSize() {

		//Create new instance of Lusk_Text_Analyzer and an integer to specify the number of words added to the array;
		Lusk_Text_Analyzer tester = new Lusk_Text_Analyzer();
		int numOfWords = 20;
		
		//Create a map that has the specified number of words plus 10 to ensure the map must be cut in size
		LinkedHashMap<String, Integer> largerTestList = new LinkedHashMap<>();
		for(int i = 0; i < (numOfWords+10); i++) {
			largerTestList.put("word"+i, i);
		}
		
		//Fail the unit test with deliberately wrong values to test that the unit test is properly working
		//assertEquals(15, tester.printTopWords(numOfWords,largerTestList).size());
		
		//Test that the printTopWords method properly creates a list with the specified number of words
		assertEquals(numOfWords, tester.printTopWords(numOfWords,largerTestList).size());
		
		//Set words
		numOfWords = 0;
		
		//Test an edge case of zero
		assertEquals(numOfWords, tester.printTopWords(numOfWords,largerTestList).size());
	}

}
