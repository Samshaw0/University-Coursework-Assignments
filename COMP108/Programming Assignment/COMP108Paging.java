//
// Coded by Prudence Wong 2021-12-29
// Updated 2023-02-26
// Updated 2023-03-03
//
// NOTE: You are allowed to add additional methods if you need.
//
// Name: Sam Shaw
// Student ID: 201858198
//
// Time Complexity and explanation: You can use the following variables for easier reference.
// n denotes the number of requests, p denotes the size of the cache
// n and p can be different and there is no assumption which one is larger
//
// evictFIFO():
//	f(n, p) = np, which goes to O(np)
// My custom 'inCache' function iterates through the values in the cache and checks if our value is equal to it. At worst, this gives it p comparisons (in the
// event that the value is not found in the cache, or is found as the last value in cache). We call inCache n times, as we iterate through each request. This gives
// us a worst case time complexity of n*p.
// evictLRU():
//	f(n, p) = 3np, which goes to O(np)
// My function 'pos' finds the index of an element of an array by iterating through each element. Since we are using this function on an array of equal 
// length to the cache, this gives it a worst case time complexity of p (if the element is in the last index of the array). The function increment iterates 
// through and increments the value of each element in an array by 1. Since we are using this function on an array of equal length to the cache, it also has a 
// worst case time complexity of p. We each of pos, inCache and increment once for each iteration of our outer loop (the one which iterates through each request).
// This gives us a worst case time complexity of n*(p+p+p) = 3np. 

class COMP108Paging {


	// evictFIFO
	// Aim: 
	// if a request is not in cache, evict the page present in cache for longest time
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108PagingOutput
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108PagingOutput evictFIFO(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108PagingOutput output = new COMP108PagingOutput();
		
		for (int i = 0; i<rSize; i++) {
			if (inCache(cArray, cSize, rArray[i])) {
				// hit
				output.hitCount = output.hitCount + 1;
				output.hitPattern = output.hitPattern + 'h';
			}
			else {
				// change the cache
				cArray[output.missCount % cSize] = rArray[i];
				// miss
				output.missCount = output.missCount + 1;
				output.hitPattern = output.hitPattern + 'm';
			}	
		}
		
		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// evict LRU
	// Aim:
	// if a request is not in cache, evict the page that hasn't been used for the longest amount of time
	// count number of hit and number of miss, and find the hit-miss pattern; return an object COMP108PagingOutput
	// Input:
	// cArray is an array containing the cache with cSize entries
	// rArray is an array containing the requeset sequence with rSize entries
	static COMP108PagingOutput evictLRU(int[] cArray, int cSize, int[] rArray, int rSize) {
		COMP108PagingOutput output = new COMP108PagingOutput();
		
		// set safety array with all elements initially equal to 0
		int[] safety = new int[cSize];
		int position;
		for (int i = 0; i<rSize; i++) {
			if (inCache(cArray, cSize, rArray[i])) {
				// update safety array				
				safety = increment(safety, cSize);
				safety[pos(cArray, rArray[i])] = 0;
				// hit
				output.hitCount = output.hitCount + 1;
				output.hitPattern = output.hitPattern + 'h';
			}
			else {
				// update safety array				
				safety = increment(safety, cSize);
				position = pos(safety, largest(safety));
				safety[position] = 0;
				// change the cache
				cArray[position] = rArray[i];
				// miss
				output.missCount = output.missCount + 1;
				output.hitPattern = output.hitPattern + 'm';
			}	
		}

		output.cache = arrayToString(cArray, cSize);
		return output;
	}

	// DO NOT change this method
	// this will turn the cache into a String
	// Only to be used for output, do not use it to manipulate the cache
	static String arrayToString(int[] array, int size) {
		String outString="";
		
		for (int i=0; i<size; i++) {
			outString += array[i];
			outString += ",";
		}
		return outString;
	}

	static boolean inCache(int[] cArray, int cSize, int elem) {
		int i = 0;
		boolean found = false;
		while (i<cSize && found==false) {
			if (cArray[i]==elem) {
				found = true;
			}
			i = i+1;
		}
		return found;
	}

	static int pos(int[] cArray, int elem) {
		int i = 0;
		boolean found = false;
		int pos = -1;
		while (i<cArray.length && found==false) {
			if (cArray[i]==elem) {
				found = true;
				pos = i;
			}
			i = i+1;
		}
		return pos;
	}

	static int[] increment(int[] arr, int arraySize) {
		for (int i = 0; i<arraySize; i++) {
			arr[i] = arr[i]+1;
			}
		return arr;
	}
	
	static int largest(int[] arr) {       
        // Initialize maximum element 
        int max = arr[0]; 
        
          // Traversing and comparing max element
        for (int i = 1; i < arr.length; i++) {
         // If current element is greater than max
            if (arr[i] > max) {
            // Then update max element
                max = arr[i]; 
	        }
	    }
        return max; 
	}
}

