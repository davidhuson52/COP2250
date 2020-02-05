import javax.swing.JOptionPane;
import java.util.regex.*;
import java.util.Scanner;
import java.io.*;


public class HarvardPersonalityTest {			/* Program to provide a user the ability to take the Harvard Comprehensive Personality Test, 
 												 * calculate their personality type, and view the number of people in each personality type. */
												 
	public static void main(String[] args) throws IOException{
		
		String welcome = "Welcome to the Harvard Comprehensive Personality Test."+ 			
						 "\nDeveloped by David Huson";
		System.out.println(welcome);
		
		Menu();

	}//end Main
	
	public static void Menu() throws IOException {
		
		String menu = "Option A:\t Append a test" + 
					  "\nOption B:\t Calculate Personality Type" +
					  "\nOption C:\t Add up total for each personality type" + 
					  "\nOption C:\t Exit Program";
					  
		boolean validChoice;
		
		do
		{
			System.out.println(menu);
			
			String userInput = JOptionPane.showInputDialog("Please enter a single letter, either A,B, C, or D to choose an option from the menu.");
			String menuChoice = userInput;
			
			if (menuChoice.equalsIgnoreCase("a")) {
				OptionA();
				validChoice = true;
				
			}
			else if (menuChoice.equalsIgnoreCase("b")) {
				OptionB();
				validChoice = true;
				
			}
			else if (menuChoice.equalsIgnoreCase("c")) {
				OptionC();
				validChoice = true;
				
			}
			else if (menuChoice.equalsIgnoreCase("d")) {
				OptionD();
				validChoice = true;
				
			}
			else {
				validChoice = false;
			}
			
		}while(validChoice == false);
		
	}//end Menu
	
	// Administers the Harvard Comprehensive Personality Test, validates their answers, then appends the Unprocessed Test to the UnprocessedTests.txt file
	public static void OptionA() throws IOException {
		
		
		try {
			String fileName = "UnprocessedTests.txt";			//Define a relative file path for the UnprocessesTests.txt file and save it as filename
			File tempFile = new File(fileName);	
			
			PrintWriter unprocessedTest = new PrintWriter(tempFile);
			
			String name = GetName();
			unprocessedTest.println(name);
			
			for (int questionNum = 1; questionNum <= 10; questionNum++) {
				String answer = GetAnswer(questionNum);
				unprocessedTest.println(answer);
			}
			
			unprocessedTest.close();
		}
		catch(FileNotFoundException errorMsg) {
			System.out.println("ERROR! Please contact your System Administrator with the following error code:");
			System.out.println(errorMsg);
		}
		finally {
			Menu();
		}
		
		
	}//end OptionA
	
	public static void OptionB() throws IOException{
		
	}//end OptionB
	
	public static void OptionC() {
		
	}//end OptionC
	
	public static void OptionD() {
		
	}//end OptionD
	
	public static String GetAnswer(int question) throws IOException {
		
		int offset = question - 2;
		int lineNum = (5 * offset) + 5;			//equation to calculate the proper line number to start on based on question number while taking into account the file starting  at line = 0 --> f(x) = 5x +5
		String answer = null;
		
		try {
			String fileName = "Dev/Test.txt";
			FileReader testQuestions = new FileReader(fileName);
			BufferedReader testReader = new BufferedReader(testQuestions);
			
			for (int line = 1; line < lineNum; line++) {			//read and discard all lines before the question the user is on
				testReader.readLine();
				
			}//end for
			
			for (int flag = 1; flag <= 5; flag++) {				//read the next five lines and display the m to user 
				String LineReader = testReader.readLine();
				JOptionPane.showMessageDialog(null, LineReader);
			}
			answer = JOptionPane.showInputDialog("\nPlease enter your choice in the box below then press enter.\n"
					+ "Your choice should be a single alphabetical character from a-d, such as A or a."); 

			testReader.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}
		finally {
			System.exit(0);
		}
		return answer;
	
	}//end GetAnswer
		
	
	public static String GetName() {
		
		boolean valid = false;
		
		String fullName;
		String firstName;
		String lastName;
		
		do	{
			
			firstName = JOptionPane.showInputDialog("Please enter your first name here. Please use only alphabetical charaters, such as: David.");
			valid = ValidateInput(firstName, 1);
			
			lastName = JOptionPane.showInputDialog("Please enter your last name here. Please use only alphabetical charaters, such as: David.");
			valid = ValidateInput(lastName, 1);
		}while (valid == false);
		
		fullName = (firstName + " " + lastName);
		
		return fullName;
		
	}//end GetName

	
	public static boolean ValidateInput(String data, int pattern) {
		boolean valid = false;
			
		if (pattern == 1) {
			
			boolean match = data.matches("[a-zA-Z]");			// create a boolean match object that results to true if the pattern is matched by data
			
			if (match == true) {			//if match is true, set valid = true
				valid = true;
			}//end if
			
			else {			// if match is false, set valid = false
				valid = false;
			}//end else
		}//end if
		
		else if(pattern == 2) {
			
			boolean match = data.matches("[a-dA-D]");
			
			if (match == true) {
				valid = true;
			}//end if
			else {
				valid = false;
			}//end else
		}//end else-if
		 
		return valid;
	}
}//end HarvardPersonalityTest

