package cs22_phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;



public class ListDirectory implements Directory {

	//private LinkedLs entries = new LinkedLs(); // All the objects are stored in this linked list
	private LinkedList<Entry> entries = new LinkedList<Entry>();

	public boolean insertEntry(String surname, String initials, int extention) {

		if( !(surname.matches("[a-zA-Z]+$"))) {	// Don't insert if surname is different than letters
			return false;
		}

		if(!(surname.toLowerCase().charAt(0) == initials.toLowerCase().charAt(1))){	// Reject adding if initials don't match surname
			return false;
		}


		if(!(initials.matches("[a-zA-Z]+$")) || initials.length()>2){	// Reject if adding initials that are longer than 2 characters
			return false;
		}

		String length = ""+extention;	// Reject if adding an extention that is larger than 4 digits
		if(length.length()>4){
			return false;
		}

		Entry newEntry = new Entry(surname,initials,extention);	// If gone so far, it's fine to add
		entries.add(newEntry);
		return true;	// Say that it was successfull

	}

	@Override
	public boolean deleteEntry(int number) {	// Deletes entry by number,"forgets" the link that holds this number
		ListIterator<Entry> iterator = entries.listIterator();


		while(iterator.hasNext()){	// Surch till hit the end of the linked list

			Entry lookup = iterator.next();
			if(lookup.getExtention()==number){	// if found, remove it
				iterator.remove();
				return true;
			}
		}

		return false;	// Otherwise not found after iterating through it all

	}

	public boolean deleteEntry(String surname) {	// DELETES ENTRY BY SURNAME
		ListIterator<Entry> iterator = entries.listIterator();

		while(iterator.hasNext()) {	// if not a corner case, start searching
			Entry lookup = iterator.next(); 
			if(lookup.getName().equalsIgnoreCase(surname)){	// if found, delete
				iterator.remove();
				return true;
			}

		}
		return false;
	}

	public int lookup(String searchTerm) {
		ListIterator<Entry> iterator = entries.listIterator();
		
		if(!iterator.hasNext()){
			return -1;
		}

		while(iterator.hasNext()){																					// While there are objects to search
			Entry lookup = iterator.next(); 
			if(lookup.getName().toLowerCase().equalsIgnoreCase(searchTerm)){	// compare their names to search term
				return lookup.getExtention();																		// return the extention if matched

			}
		}

		return -1;	// Otherwise not found, return -1 flat

	}

	public void reset(){	// Empties everything in the hash array when called

		while (!entries.isEmpty()) {
			entries.removeFirst();
		}
	}

	public boolean changeNumber(String person, int newNumber) {
		ListIterator<Entry> iterator = entries.listIterator();

		while(iterator.hasNext()){	// Search till hit the end
			Entry lookup = iterator.next(); 
			if(lookup.getName().toLowerCase().equalsIgnoreCase(person)){
				lookup.setNumber(newNumber);	// If found, change the number to the parameter in the method
				return true;									// And say you did your job right
			} 

		}
		return false;	// Otherwise not found, exit

	}

	public String printDirectory() {
		ListIterator<Entry> iterator = entries.listIterator();
		String content = "";



		while(iterator.hasNext()){	// Print until you hit the end of the linked list
			Entry lookup = iterator.next();
			content+= lookup.getName() + "   \t";
			content+=	lookup.getInitials() + "\t";
			content+=	lookup.getExtention() + "\n";	
		}

		if(content.length()==0){
			return "Empty directory";
		}
		return content;

	}


	// reads the dictionary file
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

		int increment = 0;	//	Points to the index of the next object 
		//	incremented every loop
		while (input.hasNextLine()){

			//	Splits the current read line into Name, initial and number
			String[] propertySplit = input.nextLine().split("\t");
			//	Feeds the splitted elements into a temporary object
			Entry newEntry = new Entry(propertySplit[0], propertySplit[1], Integer.parseInt(propertySplit[2]));
			entries.add(newEntry);

			increment++;	//	prepares the loop for the next array location
		}

	}

}
