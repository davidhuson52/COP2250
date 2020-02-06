import javax.swing.JOptionPane;
import java.util.*;
import java.util.stream.Stream;
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
			String UnprocessedFile = "UnprocessedTests.txt";			//Define a relative file path for the UnprocessesTests.txt file and save it as filename
			String testQuestionsPath = "Test.txt";
			String data;
			String message;
			String answer;
			
			File tempFile = new File(UnprocessedFile);	
			File questionFile = new File(testQuestionsPath);			//create a new file object to hold out test data file

			ArrayList<ArrayList<String>> questions = new ArrayList<ArrayList<String>>();
			
			
			Scanner testQuestions = new Scanner(questionFile);				//use a scanner object to read the file line by line
			

			PrintWriter unprocessedTest = new PrintWriter(tempFile);

			String name = GetName();
			String formattedName = titleCaseName(name);
			unprocessedTest.println(formattedName);

			while (testQuestions.hasNext()){			/*while the testQuestions file still has data to be read do, write the lines to a 2D array mapped as follows:
														*	[ [Question, OptionaA, OptionB, Option C, OptionD],
														*	  [Question, OptionaA, OptionB, Option C, OptionD], etc... ]
														*/
				
				for (int outerIndex = 0; outerIndex <= 9; outerIndex++)	{	//create a new row for each question	
					questions.add(new ArrayList<String>()); 		//create an inner space for the next row
					for (int index = 0; index <= 4; index++) {		//loop over every 4 lines for the inner array list
						data = testQuestions.nextLine();			//store the data from the file in a variable
						questions.get(outerIndex).add(index, data);		//use the question number -1 to get the outer index and the index counter to get the inner index
					}//end for	
				}//end for
			}//end while

			testQuestions.close();
			
			for (int questionNum = 1; questionNum <= 10; questionNum++) {
				int qNum = questionNum - 1;
				
				message = questions.get(qNum).get(0) + "\n" 
						+ questions.get(qNum).get(1) + "\n" 
						+ questions.get(qNum).get(2) + "\n"
						+ questions.get(qNum).get(3) + "\n"
						+ questions.get(qNum).get(4);
				
				answer = GetAnswer(message);
				unprocessedTest.println(answer);
			}
			

			unprocessedTest.close();
		}
		catch(FileNotFoundException errorMsg) {
			JOptionPane.showMessageDialog(null, "ERROR! Please contact your System Administrator with the following error code:");
			JOptionPane.showMessageDialog(null, errorMsg);
		}
		finally {
			String repeat = JOptionPane.showInputDialog("Would you like to return to the menu?\n"
						  							  + "Type Y for yes or N for no.");
			boolean valid = false;
			
			do {
				if (repeat.equalsIgnoreCase("y")) {
					Menu();
				}
				else if (repeat.equalsIgnoreCase("n")) {
					valid = false;
				}
				else {
					valid = false;
				}
			}while (valid == false);
		}


	}//end OptionA

	public static void OptionB() throws IOException{

		
	}//end OptionB

	public static void OptionC() {

	}//end OptionC

	public static void OptionD() {

	}//end OptionD

	public static String GetAnswer(String question) throws IOException {

		String input;

		boolean valid = false;

		do {
			input = JOptionPane.showInputDialog(question);			//get user's answer
			valid = ValidateInput(input, 2);			//validate answer using pattern 2
			return input; 
		} while (valid == false); //try again if valid == false

	}//end GetAnswer


	public static String GetName() {

		boolean valid = false;

		String fullName;
		String firstName;
		String lastName;

		do	{

			firstName = JOptionPane.showInputDialog("Please enter your first name here. Please use only alphabetical charaters, such as: David.");
			firstName = firstName.trim();
			valid = ValidateInput(firstName, 1);

			lastName = JOptionPane.showInputDialog("Please enter your last name here. Please use only alphabetical charaters, such as: David.");
			valid = ValidateInput(lastName, 1);
		}while (valid == false);

		fullName = (firstName + " " + lastName);

		return fullName;

	}//end GetName
	
	public static String titleCaseName(String name) {

        StringBuffer resultPlaceHolder = new StringBuffer(name.length());
 
        Stream.of(name.split(" ")).forEach(stringPart -> {
            char[] charArray = stringPart.toLowerCase().toCharArray();
            charArray[0] = Character.toUpperCase(charArray[0]);
            resultPlaceHolder.append(new String(charArray)).append(" ");
        });
        return resultPlaceHolder.toString();
	}

	public static boolean ValidateInput(String data, int key) {
		
		boolean valid = false;
		boolean match = false;
		
		int len = data.length();
		
		if (len >= 1) {
			if (key == 1) {
				
				match = data.matches("[a-zA-Z]{"+len+"}");			// create a boolean match object that results to true if the pattern is matched by data

				
			}//end if

			else if(key == 2) {
				
				match = data.matches("[a-zA-Z]{"+len+"}");			// create a boolean match object that results to true if the pattern is matched by data

			}//end else-if
			if (match == true) {			//if match is true, set valid = true
				valid = true;
			}//end if

			else {			// if match is false, set valid = false
				valid = false;
				
			}//end else
		}
		else {
			valid = false;
		}
		
		return valid;
	}
}//end HarvardPersonalityTest