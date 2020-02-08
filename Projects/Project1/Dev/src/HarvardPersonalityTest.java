import javax.swing.JOptionPane;
import java.util.*;
import java.util.stream.Stream;
import java.io.*;


public class HarvardPersonalityTest {			/* Program to provide a user the ability to take the Harvard Comprehensive Personality Test, 
 												 * calculate their personality type, and view the number of people in each personality type. */

	public static void main(String[] args) throws IOException{			//main method
		
		String welcome = "Welcome to the Harvard Comprehensive Personality Test." + 		//welcome message
						 "\nDeveloped by David Huson";
		
		JOptionPane.showMessageDialog(null, welcome, "Welcome!", 1);			//show welcome message to user	

		Menu();			//display menu to user
		return;			//end program

	}//end Main

	public static void Menu()throws IOException {
																					
		String menu = "\t\t\tMenu"									//create a menu string to display to user
					+ "\nOption A:\t Append a test" 					
					+ "\nOption B:\t Calculate Personality Type"
					+ "\nOption C:\t Add up total for each personality type" 
					+ "\nOption D:\t Exit Program";

		boolean validChoice = false;				//initialize vaidChoice to false

		do			//show the menu and get the user's choice at least once while the user's choice is invalid
		{


			
			String userInput = JOptionPane.showInputDialog(null, menu + "\nPlease enter a single letter, either A,B, C, or D to choose an option from the menu.", "Menu", 4);			//display the menu to the user and get their answer as a string

			if (userInput.equalsIgnoreCase("a")) {				//if the user's choice is a
				OptionA();										//Append a test
				validChoice = true;
				return;


			}//end if a
			else if (userInput.equalsIgnoreCase("b")) {			//if the user's choice is b
				OptionB();										// Calculate Personality Type
				validChoice = true;
				return;

			}// end if b
			else if (userInput.equalsIgnoreCase("c")) {			//if the user's choice is C
				OptionC();										//Add up total for each personality type
				validChoice = true;
				return;

			}//end if c
			else if (userInput.equalsIgnoreCase("d")) {			//if the user's choice is D
				OptionD();
				return;//Exit Program

			}// end if d
			else {
				JOptionPane.showMessageDialog(null, "Oops! Looks like you entered an invalid input. Please try again", "Invalid Input", 0);
				validChoice = false;
			}//end else valid = false

		}while(validChoice == false);			//while valid is false

		
		
	}//end Menu

	// Administers the Harvard Comprehensive Personality Test, validates their answers, then appends the Unprocessed Test to the UnprocessedTests.txt file
	public static void OptionA() throws IOException {
		
		String unprocessedFile = "UnprocessedTests.txt";			//Define a file path for the UnprocessesTests.txt
		String testQuestionsPath = "Test.txt";						//Define a file path for Test.txt
		String data;												//Define a String object to hold data from UnprocessedTests file
		String question;											//Define a String object to hold the question to display to user
		String answer;												//Define a String object to hold the user's answer
		
		FileWriter tempFile = null;									//declare a file writer object to hold the tempFile
		File questionFile = new File(testQuestionsPath);			//create a new file object to hold test questions file
		Scanner testQuestions = null;								//declare a scanner object for the testQuestoins
		PrintWriter unprocessedTest = null;							//declare a printWriter object for the unprocessed tests file
		
		try {			//try to open a scanner object to read the question file expecting the file to exist
			testQuestions = new Scanner(questionFile);				//use a scanner object to read the file line by line
			
		}
		catch(FileNotFoundException errorMsg) {				//catch the File not found error
			
			JOptionPane.showMessageDialog(null, "ERROR! Please contact your System Administrator with the following error code:" + "\n" +errorMsg, "Error Message", 0);				//display the error message
			return;
		}
		
		tempFile = new FileWriter (unprocessedFile, true);					//create a new file object to hold the unprocessed File so it will be appended to and not overwritten
		unprocessedTest = new PrintWriter(tempFile);			//create a print writer object to user to write the user's name and answer's to

		String name = GetName();								//get the user's name and save it as name
		String formattedName = titleCaseName(name);				//format the user's name to be titlecase --> Kobe Bryant
		unprocessedTest.println(formattedName);					//write the formatted name to the unprocessed test file
		
		ArrayList<ArrayList<String>> questions = new ArrayList<ArrayList<String>>();			//create a 2D array list to append each line of the question file to
		
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

		testQuestions.close();			//close the test questions file
		
		for (int questionNum = 0; questionNum <= 9; questionNum++) {			//loop over each row in the 2d array list (every question)

																			//format the question to present to the user by getting each index in each column in the 2D array list
			question = questions.get(questionNum).get(0) + "\n" 			//Question	
					+ questions.get(questionNum).get(1) + "\n" 				//Option A
					+ questions.get(questionNum).get(2) + "\n"				//Option B
					+ questions.get(questionNum).get(3) + "\n"				//Option C
					+ questions.get(questionNum).get(4);					//Option D
			
			answer = GetAnswer(question, questionNum);			//get the user's answer passing it the question to ask
			unprocessedTest.println(answer);		//write the user's answer to the unprocessed test file
		}
		

		unprocessedTest.close();			//close the unprocessedTests file
		
		Repeat();
		return;
	}//end OptionA

	// Calculates the personality types for all the test records in the Unprocessed tests file
	public static void OptionB() throws IOException{
		
		String pathToUnprocessedTest = "UnprocessedTests.txt";			//create a string to hold the unprocessed test file path
		String pathToResultsFile = "TestResults.txt";					//create a string to hold the results file
		String pType = null;											//initialize the pType variable
		
		File unprocessedFile = new File(pathToUnprocessedTest);			//create a file object for the unprocessed test file
		FileWriter appendResultsFile;									//declare a file writer to be used for appending to an existing unprocessed tests file
		PrintWriter results = null;										//declare a print writer object for the results test file
		
		Scanner unprocessedData = null;									//declare a scanner object for the unprocessed data
		
		
		int gradeWeights[][] = { {3, 1, 6, 2},	//Question 1 weights						Legend:
							     {2, 1, 3, 4},	//Question 2 weights			[questionNumber][option]
							     {2, 1, 3, 4},	//Question 3 weights						Examples:
							     {1, 2, 5, 3},	//Question 5 weights			[1][0] ---> question 2, option A
							     {3, 1, 2, 6},	//Question 4 weights			[2][2] ---> question 3, option D
							     {3, 1, 4, 2},	//Question 6 weights
							     {2, 1, 3, 4},	//Question 7 weights
							     {4, 3, 2, 5},	//Question 8 weights
							     {6, 4, 8, 2},	//Question 9 weights
							     {7, 5, 1, 3} };//Question 10 weights
	//option by index from 0-3	  A	 B	C  D
		
		
		try {			//try to create a new scanner object for the unprocessed tests file
			unprocessedData = new Scanner(unprocessedFile);				//create a new scanner object called unprocessed Data that references the unprocessed file object
		
		}
		catch (FileNotFoundException errorMsg){			//catch the file not found exception
			JOptionPane.showMessageDialog(null, errorMsg + "\n" + "You must have a test to append from option A.\n"
														 + "Please return to the menu and complete option A before trying again.", "ErrorMessage", 0);			//display a dialog box with the error message in it
			Menu();			//return to the menu
		}
		
		appendResultsFile = new FileWriter(pathToResultsFile, true);			//create a file writer object to append data to
		results = new PrintWriter(appendResultsFile);							//create a print writer object to use to write data to the results file

		int score = 0;		//initialize the user's score to be 0
		
		while (unprocessedData.hasNext()); {		//while there is still data to be read un the unprocessed data file		
			
			
			String userName = unprocessedData.nextLine();						//create a string object to hold the first line of each test as the user's name
			results.println(userName);											//write the user's name to the results file
			for (int questionNum = 0; questionNum <= 9; questionNum++) {			//keeps track of the question number
																	
				String answer = unprocessedData.nextLine();						//create a string object to hold the next 10 lines of the file line by line and store the data as the user's answer for that specific question
				
				if (answer.equalsIgnoreCase("a")) {								//if the user's answer is A or a
					score += gradeWeights[questionNum][0];						//accumulate the value of index [questionNum][0] in the grade weights array
				}
				else if (answer.equalsIgnoreCase("b")) {						//if the user's answer is B or b
					score += gradeWeights[questionNum][1];						//accumulate the value of index [questionNum][1] in the grade weights array
					
				}
				else if (answer.equalsIgnoreCase("c")) {						//if the user's answer is C or c
					score += gradeWeights[questionNum][2];						//accumulate the value of index [questionNum][2] in the grade weights array
											
				}
				else if (answer.equalsIgnoreCase("d")) {						//if the user's answer is D or d
					score += gradeWeights[questionNum][3];						//accumulate the value of index [questionNum][3] in the grade weights array
						
				}

			}//end for
			
			if (score >= 12 && score <= 20) {				//if the total score after all 10 questions is between 12 and 20 inclusive
				pType = "Personality Type 1";				//the user's personality type is personality type 1
				
			}
			else if (score >= 21 && score <= 31) {			//if the total score after all 10 questions is between 21 and 31 inclusive
				pType = "Personality Type 2";				//the user's personality type is personality type 2
				
			}
			else if (score >= 32 && score <= 42) {			//if the total score after all 10 questions is between 32 and 42 inclusive
				pType = "Personality Type 3";				//the user's personality type is personality type 3
				
			}
			else if (score >= 43 && score <= 53) {			//if the total score after all 10 questions is between 43 and 53 inclusive
				pType = "Personality Type 4";				//the user's personality type is personality type 4
			}
			
			results.println(pType);							//append the personality type to the test results file
		}//end while
					
		results.close();			//close the results file
		unprocessedData.close();	//close the unprocessed data file
		
		PrintWriter clearFile = new PrintWriter(pathToUnprocessedTest);			//clear the contents of the unprocessed Test file
		clearFile.close();
		
		JOptionPane.showMessageDialog(null, "The Personality Types were sucessfully calculated!\n"
										  + "Please return to the menu and run option C", "Sucess!", 1);				//let the user know the operation was successful
		
		Repeat();
		return;
		
	}//end OptionB

	public static void OptionC() throws IOException {
		
		String pathToResults = "TestResults.txt";			//create a string object to hold the Test result file path
		File resultFile = new File(pathToResults);			//create a file object for the result file
		Scanner results;									//declare a scanner object for later user on the results file
		String pType;										//declare the pType variable to use later as a counter
		
		int pType1 = 0;										//initialize all the personality type counters to 0
		int pType2 = 0;
		int pType3 = 0;
		int pType4 = 0;
		
		try {			//try to read the result file, expecting a file not found exception
			
			results = new Scanner(resultFile);			//open a new scanner on the result file
				
			while (results.hasNext()) {				//while the results file has data to read
				
				results.nextLine();						//skip over name
				pType = results.nextLine();				// store the next line as the personality type
				
				if (pType.equals("Personality Type 1")) {			//if the record equals Personality Type 1
					pType1 += 1;									//accumulate 1 to the pType1 counter
				}
				else if (pType.equals("Personality Type 2")) {		//if the record equals Personality Type 2
					pType2 += 1;									//accumulate 1 to the pType2 counter
				}
				else if (pType.equals("Personality Type 3")) {		//if the record equals Personality Type 3
					pType3 += 1;									//accumulate 1 to the pType3 counter
				}
				else {												//else the record equals Personality Type 4
					pType4 += 1;									//accumulate 1 to the pType4 counter
				}
				
			}//end while
			JOptionPane.showMessageDialog(null, "Personality Type 1:" + pType1 +			//display the number of user's who have tested into each personality type
									     "\n" + "Personality Type 2:" + pType2 +
									     "\n" + "Personality Type 3:" + pType3 +
									     "\n" + "Personality Type 4:" + pType4, "Personality Types", 3);
			
		}//end try
		catch(FileNotFoundException errorMsg){			//catch the file not found exception and display the error message
			JOptionPane.showMessageDialog(null, errorMsg + "\nThis Option relies on the data gathered in Options A and B. "			
														 + "\nPlease return to the menu and complete options A and B  before trying option C again.", "Error!", 0);			//display the error message to the user
			
		}
		finally {			//after try and catch blocks, always do the following block
			Repeat();

		}//end finally 

		return;
	}//end OptionC

	//Option D - terminate program
	public static void OptionD() {
		JOptionPane.showMessageDialog(null, "Thank you for trying out our program! GoodBye!", "Thanks!",5);			//display thank you statement
																							//return void
		
	}//end OptionD
	
	
	//GetAnswer - method to get answer from user given a question to ask user from test and return a validated, formated answer to caller
	public static String GetAnswer(String question, int num) {

		boolean valid = false;			//initialize valid as false
		String answer;

		do {			//do at least once while valid is false
			String input = JOptionPane.showInputDialog(null, question, "Question"+ " " + num, 4);			//get user's answer and store in a string
			valid = ValidateInput(input, 2);								//validate answer using pattern 2
			answer = input.toUpperCase();
			
		} while (valid == false); //try again if valid == false
		
		return answer; 													//return input

	}//end GetAnswer

	//GetName - method to get the user's name and return a validated, formated name to caller
	public static String GetName() {
					
		boolean firstNameValid = false;			//initialize firstNameValid as false
		boolean lastNameValid = false;			//initialize lastNameValid as false

		String fullName;				//declare a holder string for full name
		String firstName;				//declare a holder string for first name
		String lastName;				//declare a holder string for last name

		do	{			//do at least once, while input is invalid
			firstName = JOptionPane.showInputDialog(null, "Please enter your first name here. Please use only alphabetical charaters, such as: David.", "First Name", 4);			//get first name from user																											
			firstNameValid = ValidateInput(firstName, 1);																									//validate first name

		}while (firstNameValid == false);			//end first name validation loop
		
		do {			//do at least once, while input is invalid
			lastName = JOptionPane.showInputDialog(null, "Please enter your last name here. Please use only alphabetical charaters, such as: David.", "Last Name", 4);			//get last name from user
			lastNameValid = ValidateInput(lastName, 1);																										//validate last name
		}while (lastNameValid == false);			//end last name validation loop
		
		fullName = (firstName + " " + lastName);			//Concatenate first name and last name and save as full name

		return fullName;									//return full name to caller
			
	}//end GetName
	
	//method to title case a given string
	public static String titleCaseName(String name) {

        StringBuffer resultPlaceHolder = new StringBuffer(name.length());		//create a string buffer for the result of the title cased name
 
        Stream.of(name.split(" ")).forEach(stringPart -> {						//create a stream of strings for each string separated by a space and convert it to a character array
            char[] charArray = stringPart.toLowerCase().toCharArray();			//for each character in the character array, convert it to lower case
            charArray[0] = Character.toUpperCase(charArray[0]);					//convert the 0th (first) character in the character array to upper case
            resultPlaceHolder.append(new String(charArray)).append(" ");		//put the string parts back together with a whitespace on each end
        });
        return resultPlaceHolder.toString();									//return the string buffer as a string
	}

	//input validation method
	public static boolean ValidateInput(String data, int key) {	
		
		boolean valid = false;			//initialize valid as false
		boolean match = false;			//initialize match as false
		
		int len = data.length();		//get the length of the given data
		
		if (len > 0) {					//if the length is greater than 0
			if (key == 1) {					//if the validation key is 1
				match = data.matches("[a-zA-Z]{"+len+"}");			// create a boolean match object that results to true if the pattern is matched by data over the given length

			}//end if

			else if(key == 2) {				//if the validation key is 2
				match = data.matches("[a-dA-D]");			// create a boolean match object that results to true if the pattern is matched by data over the given length

			}//end else-if
			
			if (match == true) {			//if data matched the pattern
				valid = true;				//input is valid
				
			}//end if

			else {						//if the data doesn't match the pattern
				JOptionPane.showMessageDialog(null, "Oops! Looks like you entered an invalid input. Please choose from the following options and try again", "Invalid Input", 0);
				valid = false;			//input is invalid
				
			}//end else
		}
		else {						//if the string is empty
			JOptionPane.showMessageDialog(null, "Oops! Looks like you entered an invalid input. Please choose from the following options and try again", "Invalid Input", 0);
			valid = false;			//data is invalid
		}//end else
		
		return valid;			//return valid
	}
	
	public static void Repeat() throws IOException {
		String repeat = JOptionPane.showInputDialog(null, "Would you like to return to the menu?\n"			//ask the user if they would like to return to the menu, get their answer
				  + "Type Y for yes or N for no.", "Repeat Program", 4);

		boolean valid = false;			//initialize the valid flag as false
			
		do {			//do at least once, while valid is false
			
		if (repeat.equalsIgnoreCase("y")) {			//if the user's answer is Y or y
			Menu();									//return to the menu
		}
		else if (repeat.equalsIgnoreCase("n")) {														//if the user's answer is N or n
			JOptionPane.showMessageDialog(null, "Oops! Looks like you entered an invalid input. Please choose from the following options and try again", "Invalid Input", 0);
			JOptionPane.showMessageDialog(null, "Thank you for trying out our program! GoodBye!", "Thanks!", 5);		//display thank you message
			return;																						//return void
		}
		else {						//otherwise
			JOptionPane.showMessageDialog(null, "Oops! Looks like you entered an invalid input. Please choose from the following options and try again", "Invalid Input", 0);
			valid = false;			//input is invalid
		}
		
		}while (valid == false);			//end validation do-while
	}
}//end HarvardPersonalityTest