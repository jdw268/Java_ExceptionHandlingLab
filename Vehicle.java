/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  9.18.17
* Description: This class is an abstract class that implements ModelFormat interface.
* It's used to instantiate common characteristics of a vehicle using an inputted PID#.
* The PID# is formatted using the ModelFormat interface for easier data extraction from the vehicle make configuration file.
*/

import java.io.*;
import java.util.ArrayList;
import java.text.DecimalFormat;

public abstract class Vehicle implements ModelFormat{
	private String make;
	private String model;
	private String[] options = new String[8];
	protected String[] parts;
	private double price;
	private FileReader config;

	//used to store config file so not have to read-in multipe times
	protected ArrayList<String> configFileList = new ArrayList<String>();

	//overload construtor method - mine
	public Vehicle(String makeInput, FileReader configInput, String pidNumber){

		//call setMake to set private make variable to input
		setMake(makeInput);

		//set the configuration file of the object so can use config file now
		this.config = configInput;

		//create ArrayList to read in the config file
		buildArrayList(this.config);

		try{
			//call setModel with pidNumber
			setModel(pidNumber);
		}
		catch(ModelNotFoundException e){
			System.out.println(e.getMessage());

			//call request again
			AnyCarManufactor.request();
		}
		//last catch block
		catch(Exception e){
			System.out.println("Unknown error");
			System.exit(0);
		}

		try{
			//call setOptions with pidNumber
			setOptions(pidNumber);
		}

		catch(OptionNotFoundException e){
			System.out.println(e.getMessage());

			//call request again
			AnyCarManufactor.request();
		}
		//finally catch block
		catch(Exception e){
			System.out.println("Unknown error");
			System.exit(0);
		}
		//after this constructor runs...these private variables can be
		//accessed by the subclasses using the getMethods here
	}


	//**double check this constructor below
	//overload construtor method
	public Vehicle(String makeInput, String modelInput, FileReader configInput){
		//set the FileRead object to input parameter
		config = configInput;

		//call setMake to update String Make
		setMake(makeInput);

		//don't use setModel because it requires PID number
		model = modelInput;
	} //end Vehicle constructor

	public void setMake(String m){
		make = m;
	}

	public String getMake(){
		return make;
	}

	/*
	* Input: String - pidNumber
	* Return: Void
	* Description: setModel using the inputted pidNumber to set the private model variable.
	*/
	public void setModel(String pidNumber) throws ModelNotFoundException{
		String[] pidFormatted = new String[8];  //formatted PID number
		boolean continueRead = true;  //used for while loop when reading config file
		String line = null;  //store string when found in config file
		String[] lineArray = null;  //break line into string array w/ space delimiter
		int counter = 0;  //count lines in configFileList
		String id;

		//set string array to a formatted pidNumber using pidFormat method
		pidFormatted = pidFormat(pidNumber);

		try{

			while(continueRead){

				//read in line from config file starting at counter = 0
				line = configFileList.get(counter);

				//check if string contains the model number in pidNumber (1st element of pidFormatted); if so stop reading
				if (line.contains(pidFormatted[0])){
					continueRead = false;
				}

				//increment counter to read next line of list
				counter++;
			} //end while

		} //end try
		catch(Exception e){
			//counter reached the end of the list without a match so
			//System.out.println(e.getMessage());
			throw new ModelNotFoundException(pidFormatted[0], this.getMake());
		}

		//break stored line string into array
		//reference:  https://stackoverflow.com/questions/6086381/split-string-into-an-array-of-string
		lineArray = line.split("\\s+");

		//second element of line array is the model name
		this.model = lineArray[1];
	} //end setModel

	//getter for object model instance variable
	public String getModel(){
		return model;
	}


	/*
	* Input: String - pidNumber
	* Return: Void
	* Description: setOptions using the inputted pidNumber to set the options array variable
	*/
	public void setOptions(String pidNumber) throws OptionNotFoundException{

		//string array to append with userOptions string array
		String[] optionNames = {"Exterior Color", "Interior Color", "Powertrain", "Seat", "Radio",
		"Tire", "Rim", "Miscellaneous"};

		int[] optionNameIndexes = new int[9];

		boolean continueRead;  //used for while loop when reading config file
		int counter;  //store index of optionName when found
		String line = null;  //store string when found in config file
		String[] pidFormatted = new String[8];  //formatted PID number

		//set string array to a formatted pidNumber using pidFormat method
		pidFormatted = pidFormat(pidNumber);

		//loop through option names and find their index position config file
		for (int i = 0; i<optionNames.length; i++){
			//initiate for while-loop
			continueRead= true;
			counter =0;

			//loop through config file finding optionNames and index them
			while (continueRead){
				//set line to line of configFileList
				line = configFileList.get(counter);

				//test if the line is for the current option search
				if(line.contains(optionNames[i])){
					//System.out.println("optionname found");
					optionNameIndexes[i] = counter;
					//if matches optionName searching for then get out of loop
					continueRead = false;
				}//end if

				//no match so increment counter
				counter++;
			}//end while loop
		} //end for loop to set indexes of optionNames

		//set last index of optionNamesIndex to first line marked "Sadan" or first " " after 8th element
		continueRead = true;
		counter = optionNameIndexes[7];

		while(continueRead){
			//set line to last optionName
			line = configFileList.get(counter);

			//compare to "Sadan" to see if end of options
			if(line.equalsIgnoreCase("Sadan")){
				continueRead = false;
				optionNameIndexes[8] = counter;
			}
			counter++;
		}

		//loop through optionNames to find match for each option w/ pid
		for(int i = 0; i < optionNames.length; i++){
			//System.out.println("matching for option: " + optionNames[i]);
			//initiate for while-loop
			continueRead = true;

			//set counter to optionNameIndex value
			counter = optionNameIndexes[i];

			while (continueRead){
				//while (continueRead && (counter != optionNameIndexes[i+1])){

				//if counter has reached optionNameIndexes[i+1]; then no match has been found
				if (counter == optionNameIndexes[i+1]){
					//stop reading
					continueRead = false;

					//throw exception --need input parameters for this
					//string input id, option name, string of options
					throw new OptionNotFoundException(pidFormatted[i+1], optionNames[i], getOptionsList(optionNameIndexes[i], optionNameIndexes[i+1]));
				}

				//first check if pidFormatted is zero for none
				if(pidFormatted[i+1].equalsIgnoreCase("0")) {
					//update options Array
					this.options[i] = optionNames[i] + " : none";

					//stopping reading for match
					continueRead = false;
				}

				//now read in line starting directly after found Option
				line = configFileList.get(counter);

				//break stored line string into array
				String[] lineArray = line.split("\\s+");

				//check if first element matches the pidNumber option element - 1
				if (lineArray[0].equalsIgnoreCase(pidFormatted[i+1])) {

					//match found so stop reading
					continueRead = false;

					//test if lineArray larger then 3 elements (misc elements maybe larger)
					if (lineArray.length < 4){
						//update options Array
						this.options[i] = optionNames[i] + " : " + lineArray[1];
					}

					//line array has 5 elements
					else if (lineArray.length < 6) {
						//update options Array
						this.options[i] = optionNames[i] + " : " + lineArray[1] + " & "+ lineArray[3];
					}
					//line array has 7 elements
					else if (lineArray.length < 8) {
						//update options Array
						this.options[i] = optionNames[i] + " : " + lineArray[1] + " & "+ lineArray[3] + " & " + lineArray[5];
					}
					//line array has 9 elements
					else {
						//update options Array
						this.options[i] = optionNames[i] + " : " + lineArray[1] + " & "+ lineArray[3] + " & " + lineArray[5] + " & "+ lineArray[7];
					}
				} //end if matched to option in config file

				//no match so increment the counter
				counter++;
			}//end while that searches for match
		}//end for that goes through each optionName
	} //end setOptions

	/*method to return set of options not seleected
	getOptionsList(counter, optionNameIndexes[i+1])
	/*
	* Input: String - counter (that equals optionName option searching for) & end index to go through
	* Return: String with available list of options for optionName
	* Description: builds a string of options available for specific option type such as Exterior, interior, misc..
	*/
	public String getOptionsList(int start, int end){
		//System.out.println("in get options list");
		//String to build-out with options list
		String optionsList = "";

		//build string from config file
		for (int i = start +1 ; i < end; i++){
			optionsList = optionsList + "\n" + configFileList.get(i).toString();
			//System.out.println("optionsList: " + optionsList);
		}

		return optionsList;
	}
	//
	//getter for object's options array
	public String[] getOptions(){
		return options;
	}


	//to be overridden
	public abstract void setParts(String[] p);


	//getter for parts array
	public String[] getParts(){
		return parts;
	}


	//setter for price
	public void setPrice(String[] p){
		//p is for parts string array passed in

		//variable to hold total cost of car and options
		double total = 0.0;
		double lineCost;

		String line;  //variable to hold each line of parts string array

		//loop through each element of parts array p and break into line
		for(int i =0; i<p.length; i++){
			line = p[i];

			//break line into separate array
			String[] lineArray = line.split("\\s+");

			//try to parse each element of lineArray into Double
			for(int y =0; y< lineArray.length; y++){
				try{
					lineCost = Double.parseDouble(lineArray[y]);

					//if successfurl and less than 50.00
					if(lineCost > 50.00){
						total = total + lineCost;
					}
				}//end try
				catch(Exception e){
					//do not change total
					total = total;
				}
			}//end inner for

		} //end outer for


		//set instance variable to total
		this.price = total;
	}


	//getter for price
	public double getPrice(){
		return price;
	}


	public String toString(){
		//strings to join together for return statement
		String userCarMake;
		String userCarModel;

		//create StringBuilder object to get strings from arrays
		StringBuilder userCar = new StringBuilder();

		//create format object
		DecimalFormat formatter = new DecimalFormat("$###,###,##0.00");

		//first part of string is make
		userCarMake = "Make: " + this.make;

		//define userCarModel string
		userCarModel = "\nModel: " + this.model;

		//add Make and Model strings to string builder
		userCar.append(userCarMake + userCarModel);

		//loop through options string array to append to userCar string
		for (int i =0; i < options.length; i ++){
			userCar.append("\n"+options[i]);
		}

		//format with "Parts" section
		userCar.append("\nParts:");

		//loop through parts string array to append to userCar string
		for (int i =0; i < parts.length; i ++){
			userCar.append("\n"+ parts[i]);
		}

		//append with price
		userCar.append("\nPrice: " + formatter.format(price));

		return userCar.toString();
	}


	/*
	* Input: String - pidNumber
	* Return: String array with formatted pidNumber
	* Description: pidFormat breaks down pidNumber into indexes depending on config file
	*/
	public String[] pidFormat(String pidNumber){
		//use to return pidNumber formatted with 8 elements
		String[] pidFormatter = new String[9];

		//determine config file identiefer to format pid number
		if (pidNumber.charAt(0) == '1'){

			//loop through first 4 digits and put in index 0 the first four digits of pidNumber
			pidFormatter[0] = pidNumber.substring(0,4);

			//loop through remaining indexes and set to chars of pidNumber
			for (int i = 1; i < pidFormatter.length; i++){
				pidFormatter[i] = Character.toString(pidNumber.charAt(i+3));
			}
		}  //end if

		//check for ThatAuto.config  - 15 digits
		else if(pidNumber.charAt(0) == '2'){

			//loop through first 6 digits and put in index 0 the first six digits of pidNumber
			pidFormatter[0] = pidNumber.substring(0,6);

			//loop through all but last index and set to chars of pidNumber
			for (int i = 1; i < pidFormatter.length - 1; i++){
				pidFormatter[i] = Character.toString(pidNumber.charAt(i+5));
			}

			//set last two digits of pidNumber to last index of pidFormatter
			pidFormatter[8] = pidNumber.substring(13);
		}  //end else if

		//check for OtherAuto.config
		else if(pidNumber.charAt(0) == '3'){
			//loop through first 4 digits and put in index 0 the first four digits of pidNumber
			pidFormatter[0] = pidNumber.substring(0,4);

			//loop through all but last index and set to chars of pidNumber
			for (int i = 1; i < pidFormatter.length - 1; i++){

				pidFormatter[i] = Character.toString(pidNumber.charAt(i+3));
			}

			//set last four digits to last index of pidFormatter
			pidFormatter[8] = pidNumber.substring(11);
		}  //end else if

		else{
			System.out.println("Invalid option. Exiting program...");
			System.exit(0);
		}

		return pidFormatter;
	} //end pidFormat method

	/*
	* Input: FileReader - object's pass in file reader
	* Return: Void
	* Description: builds private arrayList for object so it can be ready by other methods
	*/
	private void buildArrayList(FileReader configFile){
		String line;  //variable to hold each line of config file

		try{
			//instantiate new bufferedreader object from configFile
			//reference:  https://stackoverflow.com/questions/15577688/search-a-file-for-a-string-and-return-that-string-if-found
			BufferedReader configReader = new BufferedReader(configFile);

			//set line to first line of file
			line = configReader.readLine();

			//loop until null
			while (line != null){
				//add line to ArrayList
				this.configFileList.add(line);

				//move to next line
				line = configReader.readLine();
			}

			configReader.close();
		}
		catch(Exception e){
			System.out.println("Error: e");
			System.exit(0);
		}
	} //end buildArrayList method
}
