//
// by Prudence Wong 2021-02-20
// Updated 2023-02-02
//

import java.util.*;
import java.io.*;

class COMP108W06App {

	private static Scanner keyboardInput = new Scanner (System.in);


	// main program
	public static void main(String[] args) throws Exception {
		int key=0, databaseSize=8, requestSize=20;
		int[] database = {70, 20, 60, 40, 50, 30, 10, 80};
		int[] request = {5, 10, 60, 70, 15, 50, 30, 20, 20, 20, 25, 15, 20, 10, 20, 5, 70, 70, 10, 10};


		System.out.print("Content of database array: ");
		COMP108W06.printArray(database, databaseSize);
		System.out.print("Content of request array: ");
		COMP108W06.printArray(request, requestSize);

		System.out.println();

		System.out.println("calling COMP108W06.notExists()...");
		COMP108W06.notExists(request, requestSize, database, databaseSize);
		System.out.println();

		System.out.println("calling COMP108W06.count()...");
		COMP108W06.count(request, requestSize, database, databaseSize);
		System.out.println();
	}


}

