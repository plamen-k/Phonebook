package cs22_phonebook;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

class Gui{


	public static void main(String[] args) throws IOException {

		final ListDirectory test = new ListDirectory();
		final JTextArea printedText = new JTextArea(30,25);	// The directory view window

		boolean badFile = false;
		try {	// If the dictionary file is not found, outputs to the terminal that it is not found and launches the program with an empty file
			test.readFile("C:\\Users\\Hallagat\\Desktop\\50s00.txt");
		} catch (FileNotFoundException e) {
			badFile = true;

		}

		final JFrame frame = new JFrame("Phone directory");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setLayout(new FlowLayout());

		frame.setVisible(true);


		printedText.setText(test.printDirectory());	// Refreshes the text
		printedText.setBorder(new EmptyBorder(10, 10, 10, 10) ); // Adds a bit of padding

		JPanel textFrame = new JPanel();

		textFrame.setVisible(true);	// Makes the frame visible
		textFrame.setLayout(new FlowLayout());

		frame.add(textFrame);	// Puts the text panel in the main panel

		// Option panel is the second segment of the GUI [1st is the text field second is the options available to the right
		JPanel optionPanel = new JPanel();

		optionPanel.setLayout(new FlowLayout());
		optionPanel.setPreferredSize(new Dimension(400, 460));
		optionPanel.setVisible(true);
		frame.add(optionPanel);

		JButton loadFile = new JButton("Load a directory file");
		optionPanel.add(loadFile);

		loadFile.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				//The file chooser
				JFileChooser fileChooser = new JFileChooser();
				// Filters the file that will be imported
				FileNameExtensionFilter filter = new FileNameExtensionFilter(
						"TEXT FILES", "txt", "text");
				fileChooser.setFileFilter(filter);
				int returnVal = fileChooser.showOpenDialog(frame);
				if(returnVal == JFileChooser.APPROVE_OPTION) {

					try {
						test.reset();	// Reset empties the hashArray and reads file from the new file
						test.readFile(fileChooser.getSelectedFile().toString());
						printedText.setText(test.printDirectory());	// Refreshes the directory

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

		// The panel responsible for inserting an entry
		JPanel insertFrame = new JPanel();
		insertFrame.setVisible(true);
		insertFrame.setBorder(new EmptyBorder(20, 20, 20, 20) );

		insertFrame.setLayout(new BoxLayout(insertFrame, BoxLayout.PAGE_AXIS));
		insertFrame.setPreferredSize(new Dimension(400, 200));	// Sets the default size

		optionPanel.add(insertFrame);

		// The text indicators that tell the user what the boxes are for
		JLabel surnameLabel = new JLabel("Surname");
		JLabel initialsLabel = new JLabel("Initials");
		JLabel extentionLabel = new JLabel("Extention");

		// The boxes themselves
		final JTextField surname = new JTextField(5);
		final JTextField initials = new JTextField(5);
		final JTextField extention = new JTextField(5);
		JButton insertEntry = new JButton("Add");

		// add an action listener to the button after pressing the button "Add"
		insertEntry.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){

				// When it is pressed, triggers the method in hashdirectory repsonsible for adding an entry
				int errors = 0;	// it will indicate how many errors have arisen // 0 means execute add

				//==============	error checking	=================

				if(surname.getText().length()==0){	// check for empty input
					errors++;
					surname.setText("The surname must contain 1 or more characters");
				}

				if(initials.getText().length()==0){
					initials.setText("Must be two letters, second must match Surname");
					errors++;
				}

				if(extention.getText().length()==0 || extention.getText().length()>4){
					extention.setText("Must contain 4 digits");
					errors++;
				}

				if(!(surname.getText().toLowerCase().charAt(0) == initials.getText().toLowerCase().charAt(1))){
					initials.setText("The last letter must match the surname.");
					errors++;
				}

				try {
					Integer.parseInt(extention.getText());
				} catch (NumberFormatException err){
					errors++;
					extention.setText("Please input up to 4 numbers");
				}

				//==============	/error checking	=================
				if(errors==0){
					test.insertEntry(surname.getText(), initials.getText(), Integer.parseInt(extention.getText()));	// with syso it also returns a debug info
					// Then resets the fields
					surname.setText("");
					initials.setText("");
					extention.setText("");
					// And prints the new directory after insertion
					printedText.setText(test.printDirectory());
				}

			}
		});

		// Add the segments to the gui
		insertFrame.add(surnameLabel);
		insertFrame.add(surname);

		insertFrame.add(initialsLabel);
		insertFrame.add(initials);

		insertFrame.add(extentionLabel);
		insertFrame.add(extention);

		insertFrame.add(insertEntry);


		// This is the panel under add entry and deletes an entry by number
		JPanel deleteNum = new JPanel();
		deleteNum.setVisible(true);
		deleteNum.setPreferredSize(new Dimension(190, 60));
		final JTextField number = new JTextField(8);
		JButton deleteNumButton = new JButton("Delete");

		optionPanel.add(deleteNum);
		deleteNum.add(number);
		deleteNum.add(deleteNumButton);

		deleteNumButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int errors = 0;	// If 0, no errors and perform the action

				if(number.getText().length()==0){
					errors++;
					number.setText("Empty field");
				} else{	// if not empty see if it is integer
					try {
						Integer.parseInt(number.getText());
					} catch (NumberFormatException err){
						errors++;
						number.setText("Digits only");
					}
				}

				if(errors==0){
					// When delete is pressed, executes the method that deletes by int in hashdirectory
					boolean success = (test.deleteEntry(Integer.parseInt(number.getText())));	
					// Then updates the display
					if(success){
						number.setText("Success !");
					} else{
						number.setText("Not Found !");
					}
					printedText.setText(test.printDirectory());	// Refresh the directory
				}
			}
		});

		// The panel responsible for deleting by surname
		JPanel deleteSur = new JPanel();
		deleteSur.setVisible(true);
		deleteSur.setPreferredSize(new Dimension(190, 60));

		optionPanel.add(deleteSur);
		final JTextField textSurname = new JTextField(8);
		deleteSur.add(textSurname);
		JButton deleteSurButton = new JButton("Delete");
		deleteSur.add(deleteSurButton);

		deleteSurButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int errors = 0;	// If 0, no errors and perform the action

				if(textSurname.getText().length()==0){	// error if empty field
					errors++;
					textSurname.setText("Empty field");
				} else{
					if(!(textSurname.getText().matches("[a-zA-Z]+$"))){	// If not empty but contains things other than letters
						errors++;
						textSurname.setText("Letters only !");
					}
				}

				if(errors==0){

					String surn = textSurname.getText();
					boolean success = (test.deleteEntry(surn));
					if(success){
						textSurname.setText("Success !");
					} else{
						textSurname.setText("Not Found !");
					}
					printedText.setText(test.printDirectory());	// Refresh the directory display
				}
			}
		});




		JPanel lookupPanel = new JPanel();	// The panel responsible for lookup
		lookupPanel.setVisible(true);				// Make visible
		lookupPanel.setPreferredSize(new Dimension(400, 60));		
		optionPanel.add(lookupPanel);
		final JTextField lookupTerm = new JTextField(8);	// Set sizes
		final JTextField resultText = new JTextField(8);
		// Create labels
		JLabel lookupTLabel = new JLabel("Surname");
		JLabel resultLabel = new JLabel("Extention");

		resultText.setEditable(false);

		// Add components to the lookup panel
		lookupPanel.add(lookupTLabel);
		lookupPanel.add(lookupTerm);
		lookupPanel.add(resultLabel);
		lookupPanel.add(resultText);


		// Create button for finding
		JButton lookupButton = new JButton("Find");
		lookupPanel.add(lookupButton);

		lookupButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				if(lookupTerm.getText().length()==0){
					lookupTerm.setText("Can't be empty !");
				} else{

					int result = test.lookup(lookupTerm.getText());
					resultText.setText(Integer.toString(result));
					if(result==-1){
						resultText.setText("Not found !");
					}

					lookupTerm.setText("");	// Reset the surname field
				}
			}
		});

		JPanel changeNumPanel = new JPanel();
		changeNumPanel.setVisible(true);
		optionPanel.add(changeNumPanel);

		final JTextField lookupSurname = new JTextField(5);
		final JTextField newNumber = new JTextField(5);

		JLabel lookupLabel = new JLabel("Surname");
		JLabel newNumberLabel = new JLabel("Number");

		changeNumPanel.add(lookupLabel);
		changeNumPanel.add(lookupSurname);

		changeNumPanel.add(newNumberLabel);		
		changeNumPanel.add(newNumber);

		JButton changeNumButton = new JButton("Change Number");
		changeNumPanel.add(changeNumButton);

		changeNumButton.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				int errors = 0;	// If 0, no errors detected
				if(lookupSurname.getText().length()==0){
					lookupSurname.setText("Can't be empty !");
				} 
				if(newNumber.getText().length()==0){
					newNumber.setText("EMPTY !");
					errors++;
				}else
					if(newNumber.getText().length()>4){
					newNumber.setText("Up to 4 numbers");
				}
				
				try {
					Integer.parseInt(newNumber.getText());
				} catch (NumberFormatException err){
					errors++;
					
				}
				
				
				
				if(errors==0){
					int newNum = Integer.parseInt(newNumber.getText());	// Take the new number and convert it to int for the method
					boolean success = test.changeNumber(lookupSurname.getText(),newNum);	// get the surname of the person from the textbox and the new number in the change method
					
					if(success){
						newNumber.setText("Success !");
					} else {
						newNumber.setText("Not found !");
					}
					
					printedText.setText(test.printDirectory());					// Refresh the directory
				}
			}
		});

		textFrame.add(printedText);

		JScrollPane scroller = new JScrollPane(printedText);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textFrame.add(scroller);

		// The border and text around most of the windows
		textFrame.setBorder(BorderFactory.createTitledBorder("Phone Directory"));
		optionPanel.setBorder(BorderFactory.createTitledBorder("Options"));
		insertFrame.setBorder(BorderFactory.createTitledBorder("Add new number"));
		deleteNum.setBorder(BorderFactory.createTitledBorder("Delete by number"));
		deleteSur.setBorder(BorderFactory.createTitledBorder("Delete by Surname"));
		lookupPanel.setBorder(BorderFactory.createTitledBorder("Search by Surname"));
		changeNumPanel.setBorder(BorderFactory.createTitledBorder("Change Number"));

		if(badFile){	// If the file is not found, print this to the directory view
			printedText.setText("File not found");
		}
	}
}