//
// Prepared by Prudence Wong (2021-02-01)
// Updated 2023-01-30
//
import java.util.*;
import java.io.*;

class COMP108W05App {

	private static Scanner keyboardInput = new Scanner (System.in);


	// main program
	public static void main(String[] args) throws Exception {
		int key=0, n=20;
		int[] data = {15, 25, 10, 30, 35, 20, 5, 60, 80, 65, 75, 70, 100, 55, 90, 45, 50, 85, 95, 40};
		int[] sortData = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};


		System.out.print("Content of data array: ");
		COMP108W05.printArray(data, n);
		System.out.print("Content of sorted data array (for binary search): ");
		COMP108W05.printArray(sortData, n);

		// get an integer input key to search
		// note: there is no error checking
		try {
			System.out.print("Enter an integer to search: ");
			key = keyboardInput.nextInt();
		}
		catch (Exception e) {
			keyboardInput.next();
			System.exit(0);
		}
		
		System.out.println();

		System.out.println("calling COMP108W05.seqSearch()...");
		COMP108W05.seqSearch(data, n, key);
		System.out.println();

		System.out.println("calling COMP108W05.binarySearch()...");
		COMP108W05.binarySearch(sortData, n, key);
		System.out.println();

		System.out.println("calling COMP108W05.findMin()...");
		COMP108W05.findMin(data, n);
		System.out.println();

		System.out.println("calling COMP108W05.findMax()...");
		COMP108W05.findMax(data, n);
		System.out.println();

		System.out.println("calling COMP108W05.findSecondMax()...");
		COMP108W05.findSecondMax(data, n);
		System.out.println();


	}

}

