

//With this assignment, you should learn the goal of testing (focus is on detecting faults
//and finding inputs that do that. 
//You should also familiarize yourself with using assertions by writing test cases in JUnit. 
//Note: you can only show the presence of defects, not their absence

//Expected behaviour if int []x = null -> throw an exception


public class findValues {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       //System.out.print("Started");

	}

	//findlast(int [] x, int y) takes an array of integers and should return the index of
	//the last element in the array that is equal to y.
	public int findLast(int [] x, int y) {
		for (int i=x.length-1; i > 0; i--) {
			if (x[i] == y) {
				return i;
			}
		}
		return -1;
	} //end FindLast


	//lastZero(int [] x) takes an array of integers and should return the index of the last
	//0 in x.
	public int lastZero(int [] x) {
		for (int i = 0; i < x.length; i++) {
	      if (x[i] == 0) {
	    	  return i;
	      }
		}
		return -1;
	}

	public int countPositive (int[] x) {
		//Returns the number of positive elements in X
		int count = 0;
		for (int i=0; i < x.length; i++) {
			if (x[i] >= 0) {
				count++;
			}
		}
		return count;
	}

	public static int oddOrPos(int[] x) {
		//Return numbers in x that are either odd or positive or both
		int count = 0;
		for (int i = 0; i < x.length; i++) {
			if (x[i] % 2 == 1 || x[i] > 0) {
				count++;
			}
		 }
		 return count;
	}


}
