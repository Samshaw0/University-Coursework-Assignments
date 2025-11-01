//
// Prepared by Prudence Wong (2021-02-01)
// Updated 2023-01-30
//
import java.util.*;
import java.io.*;

class COMP108W04App {

	private static Scanner keyboardInput = new Scanner (System.in);


	// main program
	public static void main(String[] args) throws Exception {
		int x=0, y=0;

		// get two integer input x and y
		// note: there is no error checking
		try {
			System.out.print("Enter a positive number: ");
			x = keyboardInput.nextInt();
			System.out.print("Enter a larger number: ");
			y = keyboardInput.nextInt();
		}
		catch (Exception e) {
			keyboardInput.next();
		}

		System.out.println();

		System.out.println("calling COMP108W04.sumFromOne(" + x + ") ...");
		COMP108W04.sumFromOne(x);
		System.out.println();

		System.out.println("calling COMP108W04.sumFromOne(" + y + ") ...");
		COMP108W04.sumFromOne(y);
		System.out.println();

		System.out.println("calling COMP108W04.sumFromTo(" + x + ", " + y + ") ...");
		COMP108W04.sumFromTo(x, y);
		System.out.println();

		System.out.println("calling COMP108W04.isFactor(" + x + ", " + y + ") ...");
		COMP108W04.isFactor(x, y);
		System.out.println();

		System.out.println("calling COMP108W04.isFactor(" + y + ", " + x + ") ...");
		COMP108W04.isFactor(y, x);
		System.out.println();

		System.out.println("calling COMP108W04.multipleFactor(" + x + ", " + y + ") ...");
		COMP108W04.multipleFactor(x, y);
		System.out.println();
		
		System.out.println("calling COMP108W04.bugOne(" + x + ", " + y + ") ...");
		COMP108W04.bugOne(x, y);
		System.out.println();
		

	}

}

