/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  9.18.17
* Description: AnyCarManufactor is the main file of your application. Main will read in the configuration file and initiate the application.
*/

import java.util.Scanner;
import java.io.*;
import java.lang.*;

class AnyCarManufactor {
	/*
	* Input: String Array args – Command line arguments
	* Return: Void
	* Description: Main Method for initiating AnyCar application. When called will read line by line AnyCar.config file and append to global variable anyCarConfig. Finally, Main will call request to initiate the interaction with the user.
	*/
	public static void main(String[] args){
		request();
	}


	/*
	* Input: None
	* Return: Void
	* Description: Requests user to input PID(String) and calls buildVehicle to start building each vehicle. Request will continue to prompt the user until the user inputs -1, then request will return.
	*/
	public static void request() {
		boolean continueUserInput = true; //variable to hold if user wants to continue entering PID#s
		boolean invalidPID;  //to check validity of user input
		String userPIDInput;  //variable to store PID#
		Scanner keyboard = new Scanner(System.in);  //create Scanner object for user input

		//prompt user to enter 12-digit PID number until they end with -1
		while (continueUserInput){

			//initialize userPIDInput
			userPIDInput = "";
			//initialize invalidPID to true for each new start
			invalidPID = true;

			//continue to prompt user for PID# until quits or valid one
			while (invalidPID){

				//prompt user
				System.out.print("Enter a PID number (-1 to exit): ");
				userPIDInput = keyboard.nextLine();

				if (userPIDInput.equals("-1")){
					//exit while loops and program
					System.out.println("Exiting program");
					System.exit(0);
				}
				else{ //check PID validity
					//if returns true then continue to prompt user
					//if returns false then exit invalidPID loop and continue with userContinue loop
					try{
						validInput(userPIDInput);
					}
					catch(StringException e){
						//keep invalid PID to true
						invalidPID = true;
						System.out.println(e.getMessage());
					}
					//last catch block
					catch(Exception e){
						System.out.println("Unknown error");
						System.exit(0);
					}

				}


				//call method to build vehicle
				try{
					buildVehicle(userPIDInput);
				}

				//if 1,2,3 not inputted as first digit, catch and still request input
				catch(MakeNotFoundException e){
					invalidPID = true;
					System.out.println(e.getMessage());
				}

				//if specific make doesn't have correct length PID #
				catch(MakeFormatException e){
					invalidPID = true;
					System.out.println(e.getMessage());
				}

				//if first digit of PID doesn't identify 1 of car types
				catch(ModelNotFoundException e){
					invalidPID = true;
					System.out.println(e.getMessage());
				}

				//last catch block
				catch(Exception e){
					System.out.println("Unknown error");
					System.exit(0);
				}

			}  //end invalidPID while loop
		}  //end continueUserInput whlie loop
	}  //end request method


	/*
	* Input: String pid - Product ID number
	* Return: Void
	* Description: Create Vehicle object and call getters and setters for make, model, options, parts, and price, and print the Vehicle object using it’s toString method, and return
	* The first digit is the make and exhibits the following behavior
	*               1: ThisAuto
	*               2: ThatAuto
	*               3: OtherAuto
	* The second is vehicle type and exhibits the following behavior:
	*               1: Sadan
	*               2: Coupe
	*               3: Minivan
	*               4: SUV
	*               5: Truck
	*/
	public static void buildVehicle(String id) throws MakeNotFoundException,
	MakeFormatException,
	ModelNotFoundException
	{
		//String configFileName;
		char vehicleTypeChar;
		String makeInput = "";
		Vehicle userCar = null;

		FileReader configFile = null;

		//try block for file reader
		try{
			//check first digit of PID# for config file
			if (id.charAt(0) == '1'){
				// 1= ThisAuto.Config

				//check pid for ThisAuto is correct length of 12 digits
				if (id.length() != 12){
					//throw exception
					throw new MakeFormatException(12);
				}

				//configFileName = "ThisAuto.config";
				configFile = new FileReader("ThisAuto.config");
				makeInput = "ThisAuto";

			}  //end '1' if

			else if(id.charAt(0) == '2'){
				// 2= ThatAuto.Config

				//check pid for ThatAuto is correct length of 15 digits
				if (id.length() != 15){
					//throw exception
					throw new MakeFormatException(15);
				}

				//configFileName = "ThatAuto.config";
				configFile = new FileReader("ThatAuto.config");
				makeInput = "ThatAuto";
			} //end '2' if

			else if(id.charAt(0) == '3'){
				// 3= OtherAuto.Config

				//check pid for ThatAuto is correct length of 15 digits
				if (id.length() != 15){
					//throw exception
					throw new MakeFormatException(15);
				}

				//	configFileName = "OtherAuto.config";
				configFile = new FileReader("OtherAuto.config");
				makeInput = "OtherAuto";
			}  //end '3' if
			else{
				//throw MakeNotFound Exception
				throw new MakeNotFoundException();
			}
		}
		catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
		//don't want finally block here because thrown below

		//get second digit of PID#
		vehicleTypeChar = id.charAt(1);

		//throw ModelNotFoundExcept for just character-element 2 in pid input number
		if ( (vehicleTypeChar != '1') && (vehicleTypeChar != '2') &&(vehicleTypeChar != '3') &&
		(vehicleTypeChar != '4') && (vehicleTypeChar != '5')) {

			//throw overload constructor exception given first character
			throw new ModelNotFoundException(Character.toString(vehicleTypeChar), makeInput);
		}

		//determine the type of vehicle to make (sedan, coupe, minivan, suv, truck)
		switch (vehicleTypeChar){

			//if case matches to vehicle type; instantiate that vehicle type
			case '1':
			//create Sedan with options to be updated with setter methods and config file
			userCar = new Sedan(makeInput, configFile, id);
			break;

			case '2':
			//create Coupe with options to be updated with setter methods and config file
			userCar = new Coupe(makeInput, configFile, id);
			break;

			case '3':
			//create Minivan with options to be updated with setter methods and config file
			userCar = new Minivan(makeInput, configFile, id);
			break;

			case '4':
			//create SUV with options to be updated with setter methods and config file
			userCar = new SUV(makeInput, configFile, id);
			break;

			case '5':
			//create Truck with options to be updated with setter methods and config file
			userCar = new Truck(makeInput, configFile, id);
			break;

			default:
			break;
		}  //end switch

	} //end buildVehicle

	/*
	* Input: String user entered PID#
	* Return: boolean
	* Description: Checks that user's PID # is valid.
	* The boolean return determines if the user should be prompted again
	*/
	public static void validInput(String pid) throws StringException {
		//boolean invalidInput = false;  //initiate to false - let error checks update

		//loop through characters of pid input and check if is a digit
		for (int i = 0; i < pid.length(); i++){

			//check if each character of pid string is a digit
			if (! Character.isDigit(pid.charAt(i))) {
				throw new StringException();

				//so that request is called again
				//invalidInput = true;
			}
		}
	}  //end validInput method

}
