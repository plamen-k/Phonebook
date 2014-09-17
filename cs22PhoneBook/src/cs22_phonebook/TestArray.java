package cs22_phonebook;

import java.io.IOException;



public class TestArray {
	public static void main(String[] args) throws IOException{

		StopWatch watch = new StopWatch();
		StopWatch intervalWatch = new StopWatch();

		//the only purpouse of this code is to enable automation of delete by surname
		Entry[] names = new PeopleNames("j:\\phonebook.txt").getNames();
		//(names[1].getName()); //	With this call, it is possible to simulate random name guessing, sequential 																			
		//deletion and many ways of simulation
		//

		watch.start();
		
		intervalWatch.start();
		
		ArrayDirectory directory = new ArrayDirectory();
		directory.readFile("j:\\phonebook.txt");	// This benchmark will read 2,000 entries
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after reading 2 000 entries from file");
		intervalWatch.reset();
		
		
		intervalWatch.start();
		
		for(int i=0;i<1000;i++){
			directory.insertEntry("Johnson", "PJ", 1111);
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after inserting 1 000 entries");
		intervalWatch.reset();
		
		
		// Array BENCHMARK

		intervalWatch.start();
		for(int i=0;i<1000;i++){	// Delete a random name that exists
			directory.deleteEntry(names[(int) (Math.random() * names.length)].getName());
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after deleting random 1000 names that exist");
		intervalWatch.reset();
		
		
		// Delete by number
		intervalWatch.start();
		
		for(int i=0;i<1000;i++){
			directory.deleteEntry(i);
		}	
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after deleting 1000 by number");
		intervalWatch.reset();
		

		
		intervalWatch.start();
		for(int i=0;i<1000;i++){	// delete by name beginning
			directory.deleteEntry(names[i].getName());
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after deleting 1000 names from the beginning");
		intervalWatch.reset();

		intervalWatch.start();
		
		for(int i=2000;i>1000;i--){	// delete by name from the end
			directory.deleteEntry(names[i].getName());
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after deleting 1000 names from the end");
		intervalWatch.reset();

		for(int i=3500;i>2500;i--){	// delete by name from the middle
			directory.deleteEntry(names[i].getName());
		}


		intervalWatch.start();
		
		for(int i=0;i<1000;i++){	// delete by name with miss
			directory.deleteEntry("IDONTEXIST");
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after deleting 1000 names with a miss");
		intervalWatch.reset();

		intervalWatch.start();
		directory.lookup("Qqjep");	// Last lookup
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after last lookup");
		intervalWatch.reset();

		intervalWatch.start();
		
		directory.insertEntry("Antonai", "SA", 2345);
		directory.lookup("Antonai");	// First lookup
	
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after first lookup ( depends on the implementation");
		intervalWatch.reset();

		intervalWatch.start();
		
		for(int i=0;i<1000;i++){
			directory.changeNumber(names[i].getName(), i);
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after changing the numbers of the first 1000 people");
		intervalWatch.reset();
		
		intervalWatch.start();
		
		
		for(int i=4500;i>3500;i--){
			directory.changeNumber(names[i].getName(), i);
		}
		
		intervalWatch.stop();
		System.out.println(intervalWatch.getElapsedTime() + " after changing the numbers of the last 1000 people");
		intervalWatch.reset();

		

		//directory.printDirectory();
		watch.stop();

		System.out.println("	======== Performance Report	======= ");
		System.out.println(watch.getElapsedTime());
	}
}
