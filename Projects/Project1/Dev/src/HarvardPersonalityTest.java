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
		return;

	}//end Main

	public static void Menu()throws IOException {

		String menu = "Option A:\t Append a test" + 
					  "\nOption B:\t Calculate Personality Type" +
					  "\nOption C:\t Add up total for each personality type" + 
					  "\nOption D:\t Exit Program";

		boolean validChoice = false;

		do
		{


			String userInput = JOptionPane.showInputDialog(menu + "\nPlease enter a single letter, either A,B, C, or D to choose an option from the menu.");
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
				return;
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
			JOptionPane.showMessageDialog(null, "ERROR! Please contact your System Administrator with the following error code:" + "\n" +errorMsg);
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
					JOptionPane.showMessageDialog(null, "Thank you for trying out our program! GoodBye!");
					return;
				}
				else {
					valid = false;
				}
			}while (valid == false);
		}//end finally

		
	}//end OptionA

	public static void OptionB() throws IOException{
		String pathToUnprocessedTest = "UnprocessedTests.txt";
		String pathToResultsFile = "TestResults.txt";
		String pType = null;
		
		File unprocessedFile = new File(pathToUnprocessedTest);
		FileWriter appendResultsFile;
		PrintWriter results = null;
		
		Scanner unprocessedData = null;
		
		
		int gradeWeights[][] = { {3, 1, 6, 2},	//Question 1 weights
			     {2, 1, 3, 4},	//Question 2 weights
			     {2, 1, 3, 4},	//Question 3 weights
			     {3, 1, 2, 6},	//Question 4 weights
			     {1, 2, 5, 3},	//Question 5 weights
			     {3, 1, 4, 2},	//Question 6 weights
			     {2, 1, 3, 4},	//Question 7 weights
			     {4, 3, 2, 5},	//Question 8 weights
			     {6, 4, 8, 2},	//Question 9 weights
			     {7, 5, 1, 3} };//Question 10 weights
		
		
		
		try {
			unprocessedData = new Scanner(unprocessedFile);
		}
		catch (FileNotFoundException errorMsg){
			JOptionPane.showMessageDialog(null, errorMsg + "\n" + "You must have a test to append from option A.\n"
														 + "Please return to the menu and complete option A before trying again.");
			Menu();
		}
		
		
		try {
			appendResultsFile = new FileWriter(pathToResultsFile, true);
			results = new PrintWriter(appendResultsFile);
			
		}
		catch (FileNotFoundException errorMsg) {
			results = new PrintWriter(pathToResultsFile);
			
		}
		finally {
			
			int score = 0;
			
			do {
				
				
				String userName = unprocessedData.nextLine();
				results.println(userName);
				for (int outerIndex = 0; outerIndex <= 9; outerIndex++) {			//keeps track of the question number
					
					String answer = unprocessedData.nextLine();						//holds the answer temporarily for the quesition the loop is on
					
					if (answer.equalsIgnoreCase("a")) {
						score += gradeWeights[outerIndex][0];
					}
					else if (answer.equalsIgnoreCase("b")) {
						score += gradeWeights[outerIndex][1];
						
					}
					else if (answer.equalsIgnoreCase("c")) {
						score += gradeWeights[outerIndex][2];
												
					}
					else if (answer.equalsIgnoreCase("d")) {
						score += gradeWeights[outerIndex][3];
							
					}		
	
				}//end for
				
				if (score >= 12 && score <= 20) {
					pType = "Personality Type 1";
					
				}
				else if (score >= 21 && score <= 31) {
					pType = "Personality Type 2";
					
				}
				else if (score >= 32 && score <= 42) {
					pType = "Personality Type 3";
					
				}
				else if (score >= 43 && score <= 53) {
					pType = "Personality Type 4";
				}
				
				results.println(pType);
			}while (unprocessedData.hasNext());
						
			results.close();
			unprocessedData.close();
			
			PrintWriter clearFile = new PrintWriter(pathToUnprocessedTest);			//the following 2 lines clears the contents of the unprocessed Test file
			clearFile.close();
			
			JOptionPane.showMessageDialog(null, "Done!");
			
			String repeat = JOptionPane.showInputDialog("Would you like to return to the menu?\n"
					  + "Type Y for yes or N for no.");

			boolean valid = false;
			
			do {
				
			if (repeat.equalsIgnoreCase("y")) {
				Menu();
			}
			else if (repeat.equalsIgnoreCase("n")) {
				JOptionPane.showMessageDialog(null, "Thank you for trying out our program! GoodBye!");
				return;
			}
			else {
				valid = false;
			}
			
			}while (valid == false);
		}//end finally
	}//end OptionB

	public static void OptionC() throws IOException {
		
		String fileName = "TestResults.txt";
		File resultFile = new File(fileName);
		Scanner results;
		String pType;
		
		int pType1 = 0;
		int pType2 = 0;
		int pType3 = 0;
		int pType4 = 0;
		
		try {
			results = new Scanner(resultFile);
			
			do {
				
				results.nextLine();			//skip over name
				pType = results.nextLine();
				
				if (pType.equals("Personality Type 1")) {
					pType1 += 1;
				}
				else if (pType.equals("Personality Type 2")) {
					pType2 += 1;
				}
				else if (pType.equals("Personality Type 3")) {
					pType3 += 1;
				}
				else {
					pType4 += 1;
				}
				
			}while (results.hasNext());
			
		}
		catch(FileNotFoundException errorMsg){
			JOptionPane.showMessageDialog(null, errorMsg + "\nThis Option relies on the data gathered in Options A and B. "
														 + "\nPlease return to the menu and complete options A and B  before trying option C again.");
			
		}
		finally {
			
			JOptionPane.showMessageDialog(null, pType1 + "\n" + pType2 + "\n" + pType3 + "\n" + pType4);
			
			String repeat = JOptionPane.showInputDialog("Would you like to return to the menu?\n"
					  + "Type Y for yes or N for no.");

			boolean valid = false;
			
			do {
				
			if (repeat.equalsIgnoreCase("y")) {
				Menu();
			}
			else if (repeat.equalsIgnoreCase("n")) {
				JOptionPane.showMessageDialog(null, "Thank you for trying out our program! GoodBye!");
				return;
			}
			else {
				valid = false;
			}
			
			}while (valid == false);
			

		}
	
		
	}//end OptionC

	public static void OptionD() throws IOException {
		JOptionPane.showMessageDialog(null, "Thank you for trying out our program! GoodBye!");
		return;

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