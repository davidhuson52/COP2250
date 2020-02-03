import javax.swing.JOptionPane;
import java.util.regex.*;
import java.util.Scanner;
import java.io.*;

public class HarvardPersonalityTest {

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
					  
		boolean valid;
		
		do
		{
			System.out.println(menu);
			
			String userInput = JOptionPane.showInputDialog("Please enter a single letter, either A,B, C, or D to choose an option from the menu.");
			String menuChoice = userInput;
			
			if (menuChoice.equalsIgnoreCase("a")) {
				OptionA();
				valid = true;
				
			}
			else if (menuChoice.equalsIgnoreCase("b")) {
				OptionB();
				valid = true;
				
			}
			else if (menuChoice.equalsIgnoreCase("c")) {
				OptionC();
				valid = true;
				
			}
			else if (menuChoice.equalsIgnoreCase("d")) {
				OptionD();
				valid = true;
				
			}
			else {
				valid = false;
			}
			
		}while(valid == false);
		
	}//end Menu
	
	public static void OptionA() throws IOException{
		
		try {			//Try to open the unprocessedTests.txt file
			
			FileWriter outFile = new FileWriter("UnprocessedTests.txt", true);
			PrintWriter unprocessedTests = new PrintWriter(outFile);
		}
		catch (FileNotFoundException e) {			//if the file cannot be found, create a new empty file
			System.out.println("File Does not exist yet. Must create a new, empty file");
			PrintWriter unprocessedTests = new PrintWriter("UnprocessedTests.txt");
		}
		
		
		boolean valid = false;
		do {
				
			String userName = GetName();
			
			for (numQuestiion = 1; numQuestion >= 10, numQuestion++)
				String answer = GetAnswer(numQuestion);
				

				
			}
			
			
		}while (valid == false);
		
	}//end OptionA
	
	public static void OptionB() {
			
	}//end OptionB
	
	public static void OptionC() {
		
	}//end OptionC
	
	public static void OptionD() {
		
	}//end OptionD
	
	public static String GetAnswer(int question) {
		
		File testQuestions = new File("Test.txt");
		Scanner testFile = new Scanner(testQuestions);
		
		boolean valid;
		
		if (question == 1) {
			do {
				testFile.nextLine();			/read the test Question
				
				
			}while (valid == false);
		}
	}
		
	
	public static String GetName() {
		
		String fullName;
		String firstName;
		String lastName;
		
		boolean valid = false;
		
		while (valid == false); {
			firstName = JOptionPane.showInputDialog("Please enter your first name here. Please use only alphabetical charaters, such as: David.");
			valid = ValidateName(firstName);
			
			lastName = JOptionPane.showInputDialog("Please enter your last name here. Please use only alphabetical charaters, such as: David.");
			valid = ValidateName(lastName);
		}
		
		fullName = (firstName + " " + lastName);
		
		return fullName;
		
	}//end GetName

	
	public static boolean ValidateName(String name) {
		boolean valid;
			
		boolean match = Pattern.matches("[a-zA-Z]", name);
		if (match == true) {
			valid = true;
		}
		
		else {
			valid = false;
		}
		
		return valid;
	}
}//end HarvardPersonalityTest

