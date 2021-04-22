package assignment2.array;

public class FindMax {
	private int[][] myNumbers = { 
			{10, 2, 3, 5, 6}, 
			{8, 11, 54, 31, 0}, 
			{16, 142, 0, 113, 500 },
			{123, 456, 789, 10, 11},
			{14, 899, 999, 0, 13}
			};
	private Integer max = null;
	public Coords coords = new Coords(0,0);
	
	public static void main(String[] args) {
		FindMax a = new FindMax();
		int maxVal = a.getMax();
		System.out.print("Max Val: " + maxVal + " at (" + a.coords.x + "," + a.coords.y + ")");
	}
	
	public Integer getMax() {
		for (int i = 0; i < myNumbers.length; ++i) {
			for (int j = 0; j < myNumbers[i].length; ++j) {
				if (max == null) {
					max = myNumbers[i][j];
					coords.x = i;
					coords.y = j;
				} else if (max < myNumbers[i][j]) {
					max = myNumbers[i][j];
					coords.x = i;
					coords.y = j;
				}
			}
		}
		return max;
	}
}
