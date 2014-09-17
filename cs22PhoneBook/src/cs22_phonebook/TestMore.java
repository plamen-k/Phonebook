package cs22_phonebook;

import java.io.IOException;

public class TestMore {
	public static void main(String[] args) throws IOException {
		
		ArrayDirectory directory = new ArrayDirectory();
		
		System.out.println(directory.deleteEntry(1) + " expected false, deleting by num empty dir");
		System.out.println(directory.deleteEntry("Mike") + " expected false, deleting by name in empty dir");
		System.out.println(directory.changeNumber("Mike", 5) + " expected false, change num in empty dir");
		System.out.println(directory.lookup("asd")	+ " expected -1 (eg not found), empty dir");
		System.out.println(directory.insertEntry("Johnson", "MS", 1234) + " expected false-Initial not matching surname");
		System.out.println(directory.insertEntry("Johnson", "MJ", 12344)+ " expected false-More than 4 digits");
		System.out.println(directory.insertEntry("Johnson", "MJ", 1244) + " expected true" + " after inserting ");
		System.out.println(directory.insertEntry("Johnson", "MJ", 1244) + " expected true" + " after inserting");
		System.out.println();
		System.out.println("Print directory");
		System.out.println(directory.printDirectory());
		System.out.println(directory.deleteEntry(1244)+ " expected true, delete by number");
		System.out.println(directory.deleteEntry("Johnson")	+	" expected true, deleting last entry by surname");
		System.out.println(directory.printDirectory());
		
		directory.readFile("C:\\Users\\Hallagat\\Desktop\\5000.txt");
		System.out.println(directory.printDirectory());
		
		
	}
}
