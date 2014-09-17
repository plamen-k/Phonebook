package cs22_phonebook;

// Entry describes an object of surname, initials(second letter matches surname) and extention (4 digits)

public class Entry implements Comparable<Entry> {
	private String name;
	private String initials;
	private int extention;

	// entry constructor
	public Entry(String name, String initials, int extention){
		this.name = name;
		this.initials = initials;
		this.extention = extention;
	}

	// ==============	Getters and setters below	================
	public String getName(){
		return this.name;
	}

	public String getInitials(){
		return this.initials;
	}
	
	public void setInitials(String newInitials){
		this.initials = newInitials;
	}

	public int getExtention(){
		return this.extention;
	}

	@Override
	public int compareTo(Entry nextEntry) {
		return this.getName().compareTo(nextEntry.getName());
	}
	
	public void setNumber(int newNumber){
		this.extention = newNumber;
	}



}
