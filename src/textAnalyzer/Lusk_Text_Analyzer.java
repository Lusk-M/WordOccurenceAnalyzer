package textAnalyzer;
//Author Name: Matthew Lusk
//Date: 10/18/2020
//Program Name: Lusk_Text_Analyzer
//Purpose: Class to analyze the number of occurrences of words in the file


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


/**
 * The Lusk_Text_Analyzer class provides methods for splitting 
 * a text file into a hash map, sorting the map in 
 * various ways, and returning a formatted list of words
 * and how many times they occur
 * 
 * @author Matthew Lusk
 * @version 3.0
 * @since 2020-09-13
 */
public class Lusk_Text_Analyzer {
	
	//Method that converts a given file into a HashMap
	/**
	 * This method takes a file input and converts the file into
	 * individual words in a HashMap, which includes the word and the number 
	 * of times the word occurs
	 * 
	 * @param file the file to be converted to a HashMap
	 * @return the HashMap of words and how many times they occur created from the specified file
	 * @throws FileNotFoundException if there is an issue retrieving the file
	 */
	public HashMap<String, Integer> textFileToMap(File file) throws FileNotFoundException {
		HashMap<String, Integer> map = new HashMap<>();
		
		//Initialize the scanner variable for use with the input file
		Scanner fileScanner = null;
				
		//Open a scanner, import the text file, and filter the file by characters 
		//that are not uppercase, lowercase, or apostrophes using REGEX.

		fileScanner = new Scanner(file).useDelimiter("[^a-zA-Z']+");//[\b\n\r\s-.?;:]+ [^a-zA-Z']+
				
		//While the file has another item, update the map		
		while (fileScanner.hasNext()){
			//Convert input string to lowercase
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
				
		//Close the scanner and return the map
		fileScanner.close();
		return map;
	}
	
	//Method to sort the provided map into a linked map by the map Value
	/**
	 * A method to sort a HashMap into a LinkedHashMap by the integer in the map.
	 * 
	 * @param unsortedList an unsorted HashMap of words and how many times they occur
	 * @return a LinkedHashMap sorted by the int number of times they occur in the map based on the provided HashMap
	 */
	public LinkedHashMap<String, Integer> sortMapByValue(HashMap<String, Integer> unsortedList){
		//Create the map the new sorted values will be placed into
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		
		//Put the map entries into a linked list for easier sorting
		List<Map.Entry<String, Integer> > list = 
	               new LinkedList<Map.Entry<String, Integer> >(unsortedList.entrySet()); 
		   
		//Sort the list by comparing the values, which in this case is the Integer number of times the word occurs
		Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() { 
			public int compare(Map.Entry<String, Integer> word1,  Map.Entry<String, Integer> word2) { 
				return (word2.getValue()).compareTo(word1.getValue()); 
	        } 
		}); 
		   
		//For each map item in the list, put it into an entry in the new sorted map to be returned
		for (Map.Entry<String, Integer> listItem : list) {
			sortedMap.put(listItem.getKey(), listItem.getValue());
		}
		
		//Return the new map which has been sorted by value
		return sortedMap;
	}
	
	//Method to place the specified number, numOfWords, from the provided map into a sorted list
	/**
	 * A method to create a list of formatted strings of the specified number of top words
	 * that occur in the provided map
	 * 
	 * @param numOfWords The number of words the list returned by the method should contain.
	 * @param listOfWords A list of words and the number of times they occur.
	 * @return A formatted list of strings containing the number of words specified by numOfWords 
	 */
	public List<String> printTopWords(int numOfWords, LinkedHashMap<String, Integer> listOfWords) {
		//Create variable for counting
		List<String> topWords = new LinkedList<>();
		
		//If the specified number of words is less than one return the empty list
		if(numOfWords < 1) {
			return topWords;
		}
		
		int i = 1;
		//Add each map item stating the word and how many times it occurred to the topWords list
		for(Map.Entry<String, Integer> countedWordItem : listOfWords.entrySet()) {
			topWords.add("The #" + i + " word is '" + countedWordItem.getKey() + "' and it occurs " + countedWordItem.getValue() + " times");
			i++;
			//Once the specified number of words are reached break out of the loop
			if(i > numOfWords) {
					break;
			}	
		}
		
		//Return the list with the top words
		return topWords;
	}
	
	
}
