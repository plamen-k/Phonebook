package cs22_phonebook;

public interface Directory {

	boolean insertEntry(String surname, String initials, int extention);
	boolean deleteEntry(int id);	// returns true if successful
	boolean deleteEntry(String surname);	// returns true if successful
	int lookup(String searchTerm);
	boolean changeNumber(String person, int newNumber);
	String printDirectory();
}
