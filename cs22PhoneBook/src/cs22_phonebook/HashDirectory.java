package cs22_phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

public class HashDirectory implements Directory{

	//private LinkedLsRemove[] hashArray = new LinkedLsRemove[26];	// Create 26 linked lists that will be hashed in 26 arrays based on the letter of the alphabet

	private LinkedList<Entry>[] hashArray = new LinkedList[26];


	public HashDirectory(){ // Construct null links inside the array [ avoiding null pointer exception 
		for(int i=0;i<hashArray.length;i++){
			LinkedList nullList = new LinkedList();
			this.hashArray[i] = nullList;
		}
	}

	public void reset(){	// Empties everything in the hash array when called
		for(int i=0;i<hashArray.length;i++){
			while (!hashArray[i].isEmpty()) {
				hashArray[i].removeFirst();
			}
		}
	}

	//	=============	 interface methods	==============

	@Override
	public boolean insertEntry(String surname, String initials, int extention) {
		if( !(surname.matches("[a-zA-Z]+$"))) {	// Don't insert if surname is different than letters
			return false;
		}

		if(!(surname.toLowerCase().charAt(0) == initials.toLowerCase().charAt(1))){
			return false;	// If initials don't match surname, don't add
		}

		String length = ""+extention;
		if(length.length()>4){
			return false;	// If the number is bigger than 4 digits, don't add
		}

		if(!(initials.matches("[a-zA-Z]+$")) || initials.length()>2){
			return false;	// if initials are not letters and bigger than 2 letters, don't add
		}

		// hashCode will determine in which part of the hashArray, a linked list will go
		int hashCode = surname.toLowerCase().charAt(0) % hashArray.length;	// That is determined by the first letter of the surname
		Entry insertEntry = new Entry(surname,initials,extention);
		hashArray[hashCode].add(insertEntry);
		return true;
	}

	public boolean deleteEntry(int number) {

		for(int i=0;i<hashArray.length;i++){
			ListIterator<Entry> iterator = hashArray[i].listIterator();


				while(iterator.hasNext()){	// If the element is not the first one delete as such: 

					Entry lookupEntry = iterator.next();
					if(lookupEntry.getExtention()==number){	// If you get a match with entry to be deleted
						iterator.remove();
						
						return true;	// If all that has been done, job is complete and exit from the method

					}
				}

			}
			return false;
	}

	public boolean deleteEntry(String surname) {
		// The hashing is done by taking the first letter[ASCII] and using modulo to make sure no array out of bound happens
	
		int hashCode = surname.toLowerCase().charAt(0) % hashArray.length;	// find in which of the 26 array items the deleted entry is to be searched
		ListIterator<Entry> iterator = hashArray[hashCode].listIterator();

			while(iterator.hasNext()){	// if there are items to search in, keep looking
				Entry lookup = iterator.next();	// Point to the next location

				if(lookup.getName().equalsIgnoreCase(surname)){	// Compare it to the search term

					iterator.remove();	// If matches, remove the entry
					return true;				// Say that the job is done
				} 
			}
			return false;						// Otherwise not found
		}

	public boolean changeNumber(String person, int newNumber) {
		int hashCode = person.toLowerCase().charAt(0) % hashArray.length;
		ListIterator<Entry> iterator = hashArray[hashCode].listIterator();

		while(iterator.hasNext()){	// search for the person whose number will be changed untill the end is reached
			Entry lookup = iterator.next();
			if(lookup.getName().toLowerCase().equalsIgnoreCase(person)){

				lookup.setNumber(newNumber);	// If found, set the number from the parameters
				return true;
			} 
		}
		return false;
	}

	public String printDirectory() {

		String content = "";

		for(int i=0;i<hashArray.length;i++){
			ListIterator<Entry> iterator = hashArray[i].listIterator();

			
			while(iterator.hasNext()){

				Entry printEntry = iterator.next();

				// Print until you hit the end of the linked list

				content+= printEntry.getName() + "   \t";
				content+=	printEntry.getInitials() + "\t";
				content+=	printEntry.getExtention() + "\n";			

			}
		}

		if(content.length()==0){
			return "Empty directory";
		}

		return content;
	}

	@Override
	public int lookup(String searchTerm) {
		int hashCode = searchTerm.toLowerCase().charAt(0) % hashArray.length;
		ListIterator<Entry> iterator = hashArray[hashCode].listIterator();

		while(iterator.hasNext()){	// Search until you hit the end
			Entry lookupEntry = iterator.next();
			if(lookupEntry.getName().toLowerCase().equalsIgnoreCase(searchTerm)){
				return lookupEntry.getExtention();	// if the term is found, returns the number of the person 
			}
		}
		return -1;	// Otherwise returns -1 that signals not found
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

		int increment = 0;	//	Points to the index of the next object 
		//	incremented every loop
		while (input.hasNextLine()){

			//	Splits the current read line into Name, initial and number
			String[] propertySplit = input.nextLine().split("\t");
			//	Feeds the splitted elements into a temporary object
			int hashCode = propertySplit[0].toLowerCase().charAt(0) % hashArray.length;
			
			Entry newEntry = new Entry(propertySplit[0], propertySplit[1], Integer.parseInt(propertySplit[2]));
			hashArray[hashCode].add(newEntry);

			increment++;	//	prepares the loop for the next array location
		}

	}



}
