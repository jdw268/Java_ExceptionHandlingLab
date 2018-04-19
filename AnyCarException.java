/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  9.26.17
* Description: This file holds the custom exception classes to be thrown for building a car with AnyCarManufactor program.
* It extends the exception class.
*/

//import java.lang.Exception;
public class AnyCarException{
	public AnyCarException(){
		//
	}

}  //e
/*
* Input: none
* Return: none
* Description: Exception thrown if the user input contains anything other than integers.
*/
class StringException extends Exception{

	//constructor for exception class
	public StringException(){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Input must contain only numbers, not letters.");
	}
}  //end StringException class


/*
* Input: none
* Return: none
* Description: Exception thrown if the car make ID is not defined
*/
class MakeNotFoundException extends Exception{

	//constructor for exception class
	public MakeNotFoundException(){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Vehicle company not found.");
	}
}  //end MakeNotFoundException class

/*
* Input: none
* Return: none
* Description: Exception thrown the input pid length does not match the Car company's length
*/
class MakeFormatException extends Exception{
	//instance variable
	//private int length;

	//constructor for exception class
	public MakeFormatException(int lengthPID){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		//length = lengthPID
		super("The Car's Product ID must have a length of " + lengthPID + " numbers");
	}
}  //end MakeFormatException class

/*
* Input: none
* Return: none
* Description: Exception thrown if the vehicle model does not exist
*/
class ModelNotFoundException extends Exception{

	//constructor for exception class
	public ModelNotFoundException(String inputID, String carCompany){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Model ID: " + inputID + " does not exist for " + carCompany + ".");
	}

	// //overload constructor for beginning of user input
	// public ModelNotFoundException(int inputID, String carCompany){
	// 	//error message displayed for this time of exception thrown
	// 	//pass string to super so set message as default error message
	// 	super("Model ID: " + inputID + " does not exist for " + carCompany + ".");
	// }

}  //end ModelNotFoundException class

/*
* Input: none
* Return: none
* Description: Exception thrown if option id does not exist with option index
*/
class OptionNotFoundException extends Exception{

	//constructor for exception class
	public OptionNotFoundException(String inputID, String option, String listOfOptions){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Option ID: " + inputID + " does not exist for " + option +
		", only options available are: " + listOfOptions);
	}
}  //end OptionNotFoundException class

/*
* Input: none
* Return: none
* Description: Exception thrown if option specific to Sedan's does not exist
*/
class SedanOptionsException extends Exception{

	//constructor for exception class
	public SedanOptionsException(String optionSpecific, String listofSedanOptions){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Sedan option: " + optionSpecific + " is not available, only available options are: "
		+ listofSedanOptions);
	}
}  //end SedanOptionsException class

/*
* Input: none
* Return: none
* Description: Exception thrown if option specific to Coupe's does not exist
*/
class CoupeOptionsException extends Exception{

	//constructor for exception class
	public CoupeOptionsException(String optionSpecific, String listofCoupeOptions){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Coupe option: " + optionSpecific + " is not available, only available options are "
		+ listofCoupeOptions);
	}
}  //end CoupeOptionsException class

/*
* Input: none
* Return: none
* Description: Exception thrown if option specific to Minivan's does not exist
*/
class MinivanOptionsException extends Exception{

	//constructor for exception class
	public MinivanOptionsException(String optionSpecific, String listofMinivanOptions){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Minivan option: " + optionSpecific + " is not available, only available options are "
		+ listofMinivanOptions);
	}
}  //end MinivanOptionsException class

/*
* Input: none
* Return: none
* Description: Exception thrown if option specific to SUV's does not exist
*/
class SUVOptionsException extends Exception{

	//constructor for exception class
	public SUVOptionsException(String optionSpecific, String listofSUVOptions){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("SUV option: " + optionSpecific + " is not available, only available options are "
		+ listofSUVOptions);
	}
}  //end SUVOptionsException class

/*
* Input: none
* Return: none
* Description: Exception thrown if option specific to Truck's does not exist
*/
class TruckOptionsException extends Exception{

	//constructor for exception class
	public TruckOptionsException(String optionSpecific, String listofTruckOptions){
		//error message displayed for this time of exception thrown
		//pass string to super so set message as default error message
		super("Truck option: " + optionSpecific + " is not available, only available options are "
		+ listofTruckOptions);
	}
}  //end TruckOptionsException class
