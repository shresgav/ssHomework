package assignment2.sum;

public class GetSum {

	public static void main(String[] args) {
		Double sum = 0.0;
		for (int i = 0; i < args.length; ++i) {
			if (parseInt(args[i])) {
				sum += Integer.parseInt(args[i]);
			} else if (parseDouble(args[i])) {
				sum += Double.parseDouble(args[i]);
			} else if (parseFloat(args[i])) {
				sum += Float.parseFloat(args[i]);
			} else {
				System.out.println("Invalid input");
				break;
			}
		}
		System.out.println(sum);
	}
	
	public static boolean parseInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean parseDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public static boolean parseFloat(String s) {
		try {
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
