package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class DataObtention {
	private static final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

	// Reads data entered by the user

	public static final String readLine() {
		String line = "";
		try {
			line = console.readLine();
		} catch (IOException e) {
			System.out.println("Error en entrada de datos: " + e.getMessage());
		}
		if (line == null) {
			line = "";
		}
		return line;
	}

	// Forces the data entered by the user to be an integer 
	public static int readInt(String msg) {
		Boolean correctInput = false;
		int result = -1;
		String line;
		
		System.out.println(msg);
		while (!correctInput) {
			try {
				line = console.readLine().trim();
				result = Integer.parseInt(line);
				correctInput = true;
			} catch (IOException e) {
				System.out.println("Error at the input of data: The data introduced can't be read");
			} catch (NumberFormatException ex) {
				System.out.println("Error: You must introduce an integer");
			}
		}
		return result;
	}
	// Forces the data entered by the user to be a Float
	public static float readFloat(String msg) {
		Boolean correctInput = false;
		float result = -1;
		String line;
		
		System.out.println(msg);
		while (!correctInput) {
			try {
				line = console.readLine().trim();
				result = (float) Float.parseFloat(line);
				correctInput = true;
			} catch (IOException e) {
				System.out.println("Error at the input of data: The data introduced can't be read");
			} catch (NumberFormatException ex) {
				System.out.println("Error: You must introduce a float (real number)");
			}
		}
		return result;
	}
	//// Forces the data entered by the user to be a name
	public static String readName(String msg) {
		String name=null;
		
		while (name == null) {
			System.out.println(msg);	
			name = DataObtention.readLine().trim();
			if (name.length() == 0) {
				System.out.println("Error: You must input a name");
				name=null;
			}
		}
		
		return name;
	}
	public static double readDouble(String msg) {
		Boolean correctInput = false;
		double result = -1;
		String line;
		
		System.out.println(msg);
		while (!correctInput) {
			try {
				line = console.readLine().trim();
				result = (double) Double.parseDouble(line);
				correctInput = true;
			} catch (IOException e) {
				System.out.println("Error at the input of data: The data introduced can't be read");
			} catch (NumberFormatException ex) {
				System.out.println("Error: You must introduce a double");
			}
		}
		return result;
	}
}
