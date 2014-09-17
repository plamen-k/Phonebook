package cs22_phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;


// This class is not very important but it is helpful for the benchmark test
// it's purpouse is to create an array of people's names so that all kinds of tests can be
// performed dynamically

public class PeopleNames {
	
	
	private Entry[] names;
	
	public PeopleNames(String path) throws IOException{
		readFile(path);
	}
	
	public void readFile(String path) throws IOException{
		FileReader records = new FileReader(path);
		Scanner input = new Scanner(records);

		// counts the number of lines (also determins the size of the returnedEntry)
		BufferedReader reader = new BufferedReader(new FileReader(path));
		int lineCount = 0;					//	Initialize the counter
		while(reader.readLine() != null){	//	while there are lines to be read,
			lineCount++;					//	increment lineCount
		}
		reader.close();						//	Close the reader buffer
		//	Create the object array with length of entries from the file
		Entry[] entryArray = new Entry[lineCount];

		int increment = 0;	//	Points to the index of the object array, 
		//	incremented every loop
		while (input.hasNextLine()){

			//	Splits the current read line into Name, initial and number
			String[] propertySplit = input.nextLine().split("\t");
			//	Feeds the splitted elements into a temporary object
			Entry tempPerson = new Entry(propertySplit[0], propertySplit[1], Integer.parseInt(propertySplit[2]));
			entryArray[increment] = tempPerson;	//	Puts the temp object into the current array possition
			increment++;	//	prepares the loop for the next array location
		}

		Arrays.sort(entryArray);	//Sorts them at the end by the surname of the person
		this.setNames(entryArray);	// Sets the object's field to the newly added entry
	}

	public Entry[] getNames() {
		return names;
	}

	public void setNames(Entry[] names) {
		this.names = names;
	}
	
}
