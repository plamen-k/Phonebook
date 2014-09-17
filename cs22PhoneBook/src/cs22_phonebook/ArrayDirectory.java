package cs22_phonebook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class ArrayDirectory implements Directory{

	private Entry[] entries;
	private Entry notFoundObj = new Entry("not_found404", "nf", 404); // special entry object that is returned
	// when a person is not found


	//	The method returns a sorted array of entry objects. Those objects are read
	//	from a file
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

			entryArray[increment] = new Entry(propertySplit[0], propertySplit[1], Integer.parseInt(propertySplit[2]));;	//	Puts the temp object into the current array possition
			increment++;	//	prepares the loop for the next array location
		}

		Arrays.sort(entryArray);	//Sorts them at the end by the surname of the person
		this.entries = entryArray;	// Sets the object's field to the newly added entry
	}

	public boolean insertEntry(String surname, String initials, int extention) {
		
		if( !(surname.matches("[a-zA-Z]+$"))) {	// Don't insert if surname is different than letters
		  return false;
		}
		
		if(!(surname.toLowerCase().charAt(0) == initials.toLowerCase().charAt(1))){
			return false;
		}
		
		
		
		if(!(initials.matches("[a-zA-Z]+$")) || initials.length()>2){
			return false;
		}
		
		String length = ""+extention;
		if(length.length()>4){
			return false;
		}
		
		int newSize =0;									// Initialize the the size of the array

		if(this.entries != null){				// Check if the object exists
			newSize= this.entries.length;	// Get the size of the array we want to add to
		}

		Entry[] newArray = new Entry[newSize+1]; //	Temp array for expanding
		for(int i=0; i<newSize; i++){	
			newArray[i] = this.entries[i];	// Fill the temp array with the old array values
		}

		newArray[newSize] = new Entry(surname,initials,extention);	//	Put the new entry on top of the array
		Arrays.sort(newArray);		// Sort the temp phonebook
		this.entries = newArray;
		return true;

	}

	@Override
	public boolean deleteEntry(String surname) {
		// TODO if you delete by name more people will get deleted so that will cause null pointer exception
		if (this.entries == null){	// No point in searching if the directory is empty
			return false;
		}
		 
		Entry[] newEntry = new Entry[this.entries.length-1];	//	New array that is 1 element smaller
		if(this.entries.length-1==0){
			this.entries = null;
			return true;
		}
		int j = 0;								//	Follows the index of the orriginal array
		boolean deleted=false;	// If deletion has been performed, stop deleting

		for(int i = 0; i<newEntry.length;i++){	// Makes a new array with one less element
			if( surname == null){break;}					// Prevent null pointer
			if(!deleted && surname.equalsIgnoreCase(this.entries[i].getName())){						//	if deleted id, skips this index 
				deleted=true;
				j++;
			}

			newEntry[i] = this.entries[j];	// Make a new array that is smaller
			j++;
		}

		if(deleted){
			this.entries = newEntry;	//	Returns the new list
			return true;	// If the element has been deleted, exit out of the loop
		}
		return false;		// If false, something didn't go as planned
	}

	public boolean deleteEntry(int number){

		//Find the array element that has to be deleted
		boolean found = false;
		int increment = 0;

		if(entries == null){	// Avoid null pointer excpetion if the list is empty
			return false;
		}
		
		while(!found && increment<entries.length){
			if(entries[increment].getExtention() == number){	// when found stop the while loop
				found = true;
				break;	
			}
			increment++;							// Look in the next index
		}

		if(found){
			Entry[] newEntry = new Entry[this.entries.length-1];	//	New array that is 1 element smaller
			int j = 0;								//	Follows the index of the orriginal array
			for(int i = 0; i<newEntry.length;i++){	//	Makes a new array with one less element
				if(i==increment){						//	if deleted id, skips this index in the new entry
					j++;
				}
				newEntry[i] = this.entries[j];	//	Create the array that is 1 element smaller and fill the info ( and forget 1 of the entries)
				j++;
			}
			this.entries = newEntry;					// Returns the new list
			return true;											// Job done, wooh !
		}
		else{
			return false;											// Otherwise item was not found - thus not deleted		
		}
								
	}	

	public int lookup(String  value){

		Entry searchedPerson = binarySearch(value);	// Do a binary search on the whole directory
																								// The search returns an object that is found, or fake object if not
		if(searchedPerson.getName().equalsIgnoreCase("not_found404")){
			return -1;																// If not found, binary search returns object with name not_found, 
																								// thus gives you -1 (an impossible extention)
		}
		else{
			return searchedPerson.getExtention();			// If search is successul, give the actual number
		}
	}

	public Entry binarySearch(String value){			// Implementation fo binary search
		if(this.entries == null){	// If the list is empty return the not found object
			return notFoundObj;
		}
		
		int low = 0;									// The lower bound of the binary search
		int high = this.entries.length - 1;	// The highest bound of the search
		boolean found = false;
		while(low <= high && !found){	// While the bounds don't overlap and not found, perform the search algorithm
			int mid = (low + high)/2;		// Take the middle of the searched entries as a starting point

			if(this.entries[mid].getName().compareToIgnoreCase(value)>0){
				high = mid-1;							// If the searched value is smaller than the middle one, adjust the upper bound
			}

			else if(this.entries[mid].getName().compareToIgnoreCase(value) < 0){
				low = mid+1;							// Again, here if the value searched is bigger than the middle(or current one), reasign the lower bound to the middle 
			}
			else if(this.entries[mid].getName().compareToIgnoreCase(value) == 0){
				found = true;							// If it is neither smaller or bigger, the searching is complete
				return this.entries[mid];	// Perform returning
			}

			else
				return notFoundObj;	// otherwise return a not found object that is interpreted by the lookup method
		}
		return notFoundObj;
	}

	@Override
	public boolean changeNumber(String person, int newNumber) {
		Entry personChangedNum = binarySearch(person);	// Find the person that number will be changed
		if(personChangedNum.getName().equalsIgnoreCase("not_found404")){
			return false;	// Means not found
		}

		else{
			personChangedNum.setNumber(newNumber);	// otherwise use the set method in the entry class to chnage it to the parameter of the method
			return true;

		}
	}
	
	public void reset(){	// Empties everything in the array when called
		for(int i=0;i<entries.length;i++){
			Entry nlist	= new Entry("", "", 0);
			this.entries[i] = nlist;
		}
	}

	public String printDirectory(){
		
		if(this.entries==null){
			return "Empty directory";
		}
		
		String content = "";
		for(Entry k : this.entries){	// iterate through all the entries and separate them in the fashion below
			content+= k.getName() + "   \t";
			content+=	k.getInitials() + "\t";
			content+=	k.getExtention() + "\n";					
		}
		return content;	// Returns a string of all the entries
	}

	public Entry[] getEntries(){
		return this.entries;
	}

}
