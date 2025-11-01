//
// Coded by Prudence Wong 2021-03-06
// Updated 2023-02-25
//
// Note: You are allowed to add additional methods if you need.
// Name: Sam Shaw
// Student ID: 201858198
//
// Time Complexity and explanation: 
// f denotes initial cabinet size
// n denotes the total number of requests 
// d denotes number of distinct requests
// You can use any of the above notations or define additional notation as you wish.
// 
// appendIfMiss():
// 	g(d, f) = 0.5(d^2+2df-d), which dominates to O(d^2 + df)
// Requests will take longer if they are not already in the cabinet (as every element in the linked list will need to be checked). 
// This also increases the size of the cabinet for future requests, which makes our algorithm take longer.
// So if we assume all d elements requested are not in the cabinet, the first element requested will have f comparisons,
// the second element requested will have f+1 comparisons, and this will continue until the d'th element, which will have taken f+(d-1) comparisons.
// Summing all these comparisons together gives 0.5(d^2+2df-d). We ccalculated this using formuale sum of 1->n = 0.5*n*(n+1), and since our
// sum of comparisons was the sum of f->(f+d-1), this is equivalent to sum of 1->(f+d-1) subtrace sum of 1->(f-1).
// This makes sense, as we had one nested loop in our algorithm, so we would expect it to be quadratic.
//
// freqCount():
// 	g(f, n) = 0.5(n^2+2fn-n), which dominates to O(n^2 + fn)
// freqCount has an almost identical structure to appendIfMiss(). The only structual change is an additional nested loop which we use to ensure the frequencies
// of nodes in our linked list remain in descending order. An additional nested loop could make you think that this would make our algorithm of cubic length.
// However, this nested loop can only run once for the whole iteration of the middle loop, as once it is run the middle loop breaks. This prevents our algorithm
// from getting a cubic time complexity. I'm going to computer two upper bounds for the time complexity of this algorithm. The first is the upper bound we calculated
// in appendIfMiss (where every element requested is not in the cabinet). The second upper bound I will calculate will occur where (1) I find every element in the
// last position in the cabinet and (2) every element found is then moved to the front of the cabinet. This second condition is dubious in practice. If the last 
// element, a, was moved to the front of the cabinet, it's freq must be greater than every other element. This means the next element cannot be moved to the front of 
// the cabinet, as it's freq can at best only be equal to a, in which case it won't move infront of it. Regardless, this should give be an upper bound for the length
// of this algorithm. Finding every element in the last position of the cabinet will take f*n time, while moving every element to the front of the cabinet will give
// (f-1)*n time. Summing these two together will give n*(2f-1)=2fn-n time complexity. For large values of n, the first upper bound is greater.


class COMP108Cab {

	public COMP108Node head, tail;
	
	public COMP108Cab() {
		head = null;
		tail = null;
	}

	// append to end of list when miss
	public COMP108CabOutput appendIfMiss(int[] rArray, int rSize) {
		COMP108CabOutput output = new COMP108CabOutput(rSize);
		int j;
		for (int i = 0; i<rSize; i++) {
			COMP108Node myNode = head;
			boolean found = false;
			j = 1;
			while (myNode != null && found == false) {
				if (myNode.data == rArray[i]) {
					found = true;
					output.compare[i] = j;
					output.hitCount = output.hitCount + 1;
					}
				myNode = myNode.next;
				j = j + 1;
				}
			if (found == false) {
				insertTail(new COMP108Node(rArray[i]));
				output.compare[i] = j-1;
				output.missCount = output.missCount + 1;
				}
			
			}

		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		return output;
	}

	// move the file requested so that order is by non-increasing frequency
	public COMP108CabOutput freqCount(int[] rArray, int rSize) {
		COMP108CabOutput output = new COMP108CabOutput(rSize);
		
		int j;
		for (int i = 0; i<rSize; i++) {
			COMP108Node myNode = head;
			boolean found = false;
			j = 1;
			while (myNode != null && found == false) {
				if (myNode.data == rArray[i]) {
					// hits
					found = true;
					output.compare[i] = j;
					output.hitCount = output.hitCount + 1;
					myNode.freq = myNode.freq + 1;
					// rearranges linked list
					while (myNode != head && myNode.freq > myNode.prev.freq) {
						switchNode(myNode.prev, myNode);
						}
					}
				myNode = myNode.next;
				j = j + 1;
				}
			if (found == false) {
				// misses
				insertTail(new COMP108Node(rArray[i]));
				output.compare[i] = j-1;
				output.missCount = output.missCount + 1;
				}
			
			}
		
		output.cabFromHead = headToTail();
		output.cabFromTail = tailToHead();
		output.cabFromHeadFreq = headToTailFreq();
		return output;		
	}

	// goes from (... -> a -> b -> ...) to (... -> b -> a -> ...)
	public void switchNode(COMP108Node a, COMP108Node b) {
		a.next = b.next;
		b.prev = a.prev;
		a.prev = b;
		b.next = a;
		if (a == head) {head = b;}
		else {b.prev.next = b;}
		if (b == tail) {tail = a;}
		else {a.next.prev = a;}
	}


	// DO NOT change this method
	// insert newNode to head of list
	public void insertHead(COMP108Node newNode) {		

		newNode.next = head;
		newNode.prev = null;
		if (head == null)
			tail = newNode;
		else
			head.prev = newNode;
		head = newNode;
	}

	// DO NOT change this method
	// insert newNode to tail of list
	public void insertTail(COMP108Node newNode) {

		newNode.next = null;
		newNode.prev = tail;
		if (tail != null)
			tail.next = newNode;
		else head = newNode;
		tail = newNode;
	}

	// DO NOT change this method
	// delete the node at the head of the linked list
	public COMP108Node deleteHead() {
		COMP108Node curr;

		curr = head;
		if (curr != null) {
			head = head.next;
			if (head == null)
				tail = null;
			else
				head.prev = null;
		}
		return curr;
	}
	
	// DO NOT change this method
	// empty the cabinet by repeatedly removing head from the list
	public void emptyCab() {
		while (head != null)
			deleteHead();
	}


	// DO NOT change this method
	// this will turn the list into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTail() {
		COMP108Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the list into a String from tail to head
	// Only to be used for output, do not use it to manipulate the list
	public String tailToHead() {
		COMP108Node curr;
		String outString="";
		
		curr = tail;
		while (curr != null) {
			outString += curr.data;
			outString += ",";
			curr = curr.prev;
		}
		return outString;
	}

	// DO NOT change this method
	// this will turn the frequency of the list nodes into a String from head to tail
	// Only to be used for output, do not use it to manipulate the list
	public String headToTailFreq() {
		COMP108Node curr;
		String outString="";
		
		curr = head;
		while (curr != null) {
			outString += curr.freq;
			outString += ",";
			curr = curr.next;
		}
		return outString;
	}

}
