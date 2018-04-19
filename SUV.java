/*
* Name:  Jillian Irvin
* Class: CIT-244
* Date:  9.18.17
* Description: This class is the SUV class that extends the Vehicle class
* It's used to create a SUV object.
*/
import java.io.*;
import java.util.*;

public class SUV extends Vehicle{
	private int rows;
	private int seats;

	//overload constructor method - mine
	public SUV(String makeInput, FileReader configInput, String pidNumber){

		//set inherited data with super
		super(makeInput, configInput, pidNumber);

		try{
			//use getRows to call setRows
			setRows(getRows());
		}
		catch(SUVOptionsException e){
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
			//use getSeats to call setSeat
			setSeats(getSeats());
		}
		catch(SUVOptionsException e){
			System.out.println(e.getMessage());

			//call request again
			AnyCarManufactor.request();
		}
		//last catch block
		catch(Exception e){
			System.out.println("Unknown error");
			System.exit(0);
		}
		
		//setParts
		setParts(this.getOptions());

		//call setPrice
		super.setPrice(this.getParts());

		//call toString
		System.out.println(super.toString());
	}

	public void setRows(int r){
		rows = r;
	}


	//sets the BedSize option using PID# and config file
	public int getRows() throws SUVOptionsException{
		//get customOption by calling readModelName (since model name is already set)
		String customOption = readModelName();

		Boolean continueRead = true;  //used to search configFile
		int counter = 0;  //count through configFile
		String line; //store lines in configFileList
		String rowsString = null;
		int rowsInt;
		int locationSUV = 0;

		//search customOption in config file for:  search from SUV and 5 indexes of list
		//find index of "SUV" first
		while(continueRead){

			//read line from config file starting at counter = 0
			line = this.configFileList.get(counter);

			//pick a spot before custom options are listed
			if (line.equalsIgnoreCase("SUV")) {
				continueRead = false;

				//set where SUV found
				locationSUV = counter;
			}

			//increment counter
			counter++;
		} //end while

		//now in SUV options; find bed size
		continueRead = true;
		//read config file searching for match; if reaches end of config file throw exception then custom exception
		try{
			while(continueRead){

				//store line at position in configFile
				line = this.configFileList.get(counter);

				//initialize new array because lines could have different sizes
				String[] lineArray = line.split("\\s+");
				//break stored line string into array
				//lineArray

				//see if first element matches customOption
				if (lineArray[0].equalsIgnoreCase(customOption)) {
					//store 3rd element for option result
					rowsString = lineArray[2];
					//stop loop
					continueRead = false;
				}
				//if counter + 5 reaches then no option
				counter++;
			} //end while
		} //end try
		catch(Exception e){
			//locationSedan + 5 b/c 5 sedan options
			throw new SUVOptionsException(customOption, this.getOptionsList(locationSUV, locationSUV + 5));
		}

		//parse bedSizeString into an integer
		rowsInt = Integer.parseInt(rowsString);

		return rowsInt;
	}  //end getRows

	//setter for seats
	public void setSeats(int s){
		seats = s;
	}

	//getter for seats
	public int getSeats() throws SUVOptionsException{
		//get customOption by calling readModelName (since model name is already set)
		String customOption = readModelName();
		Boolean continueRead = true;  //used to search configFile
		int counter = 0;  //count through configFile
		String line; //store lines in configFileList
		String seatsString = null; //
		int seatsInt;
		int locationSUV = 0;

		//search customOption in config file for:  search from SUV and 5 indexes of list
		//find index of "SUV" first
		while(continueRead){

			//read line from config file starting at counter = 0
			line = this.configFileList.get(counter);

			//check if string contains the model number in pidNumber (1st element of pidFormatted); if so stop reading
			if (line.equalsIgnoreCase("SUV")) {
				continueRead = false;
				locationSUV = counter;
			}

			//increment counter
			counter++;
		} //end while

		//use counter count to start for loop to search for customOption string
		//search only 5 lines
		for(int i = counter; i <= (counter+4); i++){
			//initialize new array because lines could have different sizes
			String[] lineArray;

			//store line at position in configFile
			line = this.configFileList.get(i);


			//break stored line string into array
			lineArray = line.split("\\s+");

			//see if first element matches customOption
			if (lineArray[0].equalsIgnoreCase(customOption)) {

				//store 5th element for option result
				seatsString = lineArray[5];
			}

			//if we reach end of counter and line is still null then throw exception
			if ((i == counter + 5) && (seatsString.equalsIgnoreCase(null))){
				//throw SedanOptionExceptoin
				throw new SUVOptionsException(customOption, this.getOptionsList(locationSUV, (locationSUV + 5)));
			}

		} //end for
		//parse string into a double
		seatsInt = Integer.parseInt(seatsString);

		return seatsInt;
	}

	//override abstract method from vehicle.java; p is the options array
	@Override
	public void setParts(String[] p){
		Boolean continueRead;
		Boolean continueRead2;
		String line = null;

		//used to store parts list
		ArrayList<String> partsList = new ArrayList<String>();

		int counter;

		//make partsArray as a list
		//String[] partsArray = new String[10];

		//set 1st element to the model name and price
		//store the modelName in a string
		String modelName = this.getModel();

		continueRead = true;
		continueRead2 = true;
		counter = 0;

		//search until match in config list
		while (continueRead){

			line = this.configFileList.get(counter);

			//test if line contains modelName
			if(line.contains(modelName)) {
				//match found; stop whlie loop
				continueRead = false;

				//break line variable into an array
				String[] lineArray = line.split("\\s+");

				//use StringBuilder to for parts Array 1st element
				StringBuilder partsString1 = new StringBuilder();

				//first part of parts string is Model:
				partsString1.append("Model: " + lineArray[1] + " $ " + lineArray[2]);

				//store partsArray1 in partsArray element 1
				//partsArray[0] = partsString1.toString();
				partsList.add(partsString1.toString());
			} //end if

			//increment counter
			counter++;
		}  //end while

		String[] optionNames = {"Exterior Color", "Interior Color", "Powertrain", "Seat", "Radio",
		"Tire", "Rim", "Miscellaneous"};

		//**repeat similar process but now w/ options array (p)
		for (int i = 0; i < p.length-1; i++){
			//set line = to first element in options array
			line = p[i];

			//redefine lineArray as split line
			String[] lineArray = line.split("\\s+");

			//
			if ((lineArray[(lineArray.length)-1]).equalsIgnoreCase("none")) {
				//don't copy or search
			}
			else{
				//take first element of lineArray and match to configFileList
				continueRead = true;
				counter = 0;
				while (continueRead){
					//test if line contains optionName - Exterior, Interior; etc.
					if((this.configFileList.get(counter)).contains(lineArray[0])) {
						//match found; stop while loop
						continueRead = false;

						/**
						* found optoinName so now find last element of lineArray - options array split in configfile list
						*use counter position to start search from (based on optionName found)
						*/
						//then find the match of option p at index against config file
						continueRead2 = true;
						int counter2 = counter;
						while (continueRead2){

							//loop from counter2 until you find option set //checking against p = lineArray
							if ((this.configFileList.get(counter2)).contains(lineArray[(lineArray.length)-1])) {
								//match found
								continueRead2 = false;

								//store the line just found
								line = this.configFileList.get(counter2);

								//split this line and don't take 1st element
								String[] lineArray2 = line.split("\\s+");

								//use StringBuilder to for parts Array 1st element
								StringBuilder partsString2 = new StringBuilder();

								partsString2.append(optionNames[i] + " : ");

								//only will have 3 elements - update partsString2 with lineArray
								partsString2.append(lineArray2[1] + " $ " + lineArray2[2]);

								//add to partsList
								partsList.add(partsString2.toString());

							}  //end inner if
							counter2++;

						}  //end inner while
					}// endouter while if

					//increment counter
					counter++;
				} //end outer most while
			}  //outer else
		} //end for

		//now lets update the miscellaneous
		//find optionName in configfile
		continueRead= true;
		counter=0;
		while(continueRead){
			line = this.configFileList.get(counter);
			//test if line contains optionname - Misc.
			if(line.contains(optionNames[7])){
				//String[] lineArray = line.split("\\s+");
				continueRead = false;
			} //found misc
			counter++;
		} //end while

		continueRead = true;
		//**now see if you can match what's in the options array p to configFileList
		String[] lineArray = p[7].split("\\s+");
		while(continueRead){
			line = this.configFileList.get(counter);

			//check against length of misc
			if(lineArray.length == 3){

				//check only last element
				if (line.contains(lineArray[(lineArray.length)-1])) {

					continueRead=false;
					//store the line just found
					line = this.configFileList.get(counter);

					//split this line and don't take 1st element
					String[] lineArray2 = line.split("\\s+");

					//use StringBuilder to for parts Array 1st element
					StringBuilder partsString2 = new StringBuilder();
					partsString2.append(optionNames[7] + " :" );
					//append full line in configFile
					for(int z = 1; z < lineArray2.length; z++){
						partsString2.append(" " + lineArray2[z]);
					}
					partsList.add(partsString2.toString());
				} //end if for contains lineArray requirements
			}//end if for length <=3
			//check if optons array had more than one misc item
			else if(lineArray.length == 5){
				//check only last element and 5
				if (line.contains(lineArray[(lineArray.length)-1]) && line.contains(lineArray[2])) {
					continueRead=false;

					//store the line just found
					line = this.configFileList.get(counter);

					//split this line and don't take 1st element
					String[] lineArray2 = line.split("\\s+");

					//use StringBuilder to for parts Array 1st element
					StringBuilder partsString2 = new StringBuilder();
					partsString2.append(optionNames[7] + " : " );
					//append full line in configFile
					for(int z = 1; z < lineArray2.length; z++){
						partsString2.append(" " + lineArray2[z]);
					}
					partsList.add(partsString2.toString());
				} //end if for contains lineArray requirements
			}//end else if for 9
			//check if optons array had more than one misc item
			else if(lineArray.length ==7) {
				//check only last element
				if (line.contains(lineArray[(lineArray.length)-1]) && line.contains(lineArray[2]) && line.contains(lineArray[4])) {
					continueRead=false;
					//store the line just found
					line = this.configFileList.get(counter);

					//split this line and don't take 1st element
					String[] lineArray2 = line.split("\\s+");

					//use StringBuilder to for parts Array 1st element
					StringBuilder partsString2 = new StringBuilder();
					partsString2.append(optionNames[7] + " : " );
					//append full line in configFile
					for(int z = 1; z < lineArray2.length; z++){
						partsString2.append(" " + lineArray2[z]);
					}
					partsList.add(partsString2.toString());
				} //end if for contains lineArray requirements
			}//end else

			// 4 options in misc options
			else{

				//check only last element
				if (line.contains(lineArray[(lineArray.length)-1]) && line.contains(lineArray[2]) && line.contains(lineArray[4]) && line.contains(lineArray[6])) {
					continueRead=false;
					//store the line just found
					line = this.configFileList.get(counter);

					//split this line and don't take 1st element
					String[] lineArray2 = line.split("\\s+");

					//use StringBuilder to for parts Array 1st element
					StringBuilder partsString2 = new StringBuilder();
					partsString2.append(optionNames[7] + " : " );
					//append full line in configFile
					for(int z = 1; z < lineArray2.length; z++){
						partsString2.append(" " + lineArray2[z]);
					}
					partsList.add(partsString2.toString());
				} //end if for contains lineArray requirements
			}//end else
			//nothing found so increment couter
			counter++;
		} //end while

		//lets do the custom options to store in partsList
		String customOption = readModelName();

		//reset variables
		continueRead = true;
		continueRead2 = true;
		int counter2;
		counter = 0;

		while(continueRead){

			//read line from config file starting at counter = 0
			line = this.configFileList.get(counter);

			//pick a spot before custom options are listed
			if (line.equalsIgnoreCase("SUV")) {
				continueRead = false;
				counter2= counter;

				while(continueRead2){
					//store line at position in configFile
					line = this.configFileList.get(counter2);

					String[] lineArray3 = line.split("\\s+");


					if(lineArray3[0].equalsIgnoreCase(customOption)){
						continueRead2 = false;

						//store elements from linearray starting at one
						StringBuilder optionBuilder = new StringBuilder();
						optionBuilder.append("Model Options : ");
						for(int i =1; i < lineArray3.length; i++){
							optionBuilder.append(lineArray3[i] + " ");
						}
						//add to partsList
						partsList.add(optionBuilder.toString());
					}
					counter2++;
				}  //end inner while
			} //end outer if

			counter++;
		}  //end while for custom option


		//now set parts array to partsList
		//first set size of parts array
		parts = new String[partsList.size()];

		//set parts array to partsList
		for (int i = 0; i < partsList.size(); i++){
			parts[i] = partsList.get(i).toString();
		}
		// //now lets print parts list
		// for (int i = 0; i < parts.length; i++){
		// 	System.out.println(parts[i]);
		// }
	}  //end setParts

	/*
	* Input: String - pidNumber
	* Return: String
	* Description: this method reads the object's model name and passes back its custom option
	*/
	public String readModelName(){
		String modelName = this.getModel();  //variable to store object's model

		String customOptions;  //string to hold options for hatchback and engine types
		int position;  //hold substring location

		//search the name for substring "SUV" and add 5 for other options
		position = modelName.indexOf("SUV") + 3;


		//store substring starting from positin to end of string
		customOptions = modelName.substring(position);

		return customOptions;
	} //end real Model Name

} //end SUV
