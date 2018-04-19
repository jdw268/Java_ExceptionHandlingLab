public class Week2Main{
	public static void main(String[] args){
		switch (args[0]){
			case "1":
				example_1();
				break;
			case "2":
				example_2();
				break;
			case "3":
				example_3();
				break;
			case "4":
				example_4();
				break;
			case "5":
				example_5();
				break;
			case "6":
				example_6();
				break;
			case "7":
				example_7();
				break;
			case "8":
				example_8();
				break;
		}
	}

	/*
	Compile and Run Time
	*/
	public static void example_1(){
		int i = 1;
		String l = "Hello";
		//System.out.println(l.charAt(8));
		try{
			//i = l; // compile time
			System.out.println(l.charAt(8));
		} catch (Exception e){
			System.out.println(e.getMessage());
		}
		System.out.println("Example 1 Complete");
	}


	/*
	Index Out of Bounds Exception
	*/
	public static void example_2(){
		int i = 1;
		String l = "Hello";
		//System.out.println(l.charAt(8));
		try{
			//i = l; // compile time
			System.out.println(l.charAt(8));
		} catch (ArrayIndexOutOfBoundsException o){
			System.out.println("Index Out of Bounds Error: " + o.getMessage());
		} catch (Exception e){
			System.out.println("Exception Error: " + e.getMessage());
		}
		System.out.println("Example 2 Complete");
	}


	/*
	Race Condition
	*/
	public static void example_3(){
		int i = 1;
		String l = "Hello";
		//System.out.println(l.charAt(8));
		try{
			//i = l; // compile time
			System.out.println(l.charAt(8));
		//} catch (Exception e){
		//	System.out.println("Exception Error: " + e.getMessage());
		} catch (IndexOutOfBoundsException o){
			System.out.println("Index Out of Bounds Error: " + o.getMessage());
		}
		System.out.println("Example 3 Complete");
	}


	/*
	Race Condition
	*/
	public static void example_4(){
		int i = 1;
		String l = "Hello";
		//System.out.println(l.charAt(8));
		try{
			//i = l; // compile time
			System.out.println(l.charAt(8));
		} catch (Exception outer){
			try {
				System.out.println("Exception Error Outer: " + outer.getMessage());
			    System.out.println("Exception Error Outer: " + outer.getMessage().charAt(1000));
			} catch(Exception inner){
			    System.out.println("Exception Error Inner: " + inner.getMessage());
			}
		}
		System.out.println("Example 4 Complete");
	}

	/*
	Try Catch Inception
	*/
	public static void example_5(){
		int i = 1;
		String l = "Hello";
		//System.out.println(l.charAt(8));
		try{
			//i = l; // compile time
			//System.out.println(l.charAt(8));
		} catch (Exception e){
			System.out.println("Exception Error: " + e.getMessage());
		} finally {
			System.out.println("This will always run");
		}
		System.out.println("Example 5 Complete");
	}

	/*
	Throws
	*/
	public static void example_6(){
		try{
			throw new Exception("This is a throw example 6");
		} catch (Exception e){
			System.out.println("Exception Error: " + e.getMessage());
		}
		System.out.println("Example 6 Complete");
	}
	/*
	Custom Exception
	*/
	public static void example_7(){
		try{
			throw new TestException();
		} catch (Exception e){
			System.out.println("Exception Error: " + e.getMessage());
		}
		System.out.println("Example 7 Complete");
	}
	/*
	Custom Exception with arguments
	*/
	public static void example_8(){
		try{
			throw new TestException2("This is a throw example 8");
		} catch (Exception e){
			System.out.println("Exception Error: " + e.getMessage());
		}
		System.out.println("Example 8 Complete");
	}

}



class TestException extends Exception {
	public TestException(){
		super("Test 1 Exception");
	}
}

class TestException2 extends Exception {
	public TestException2(String args){
		super("Test 2 Exception: " + args);
	}
}
