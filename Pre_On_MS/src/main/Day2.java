package main;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class Day2 {
	
	public static void main(String[] args) {
		
		// Column precision and scale
		int precision = 25;
	    int scale = 5;
	       
	    // Test cases
	    testRounding("12345.12345467", "12345.12345", precision, scale);
	    testRounding("12345.123456", "12345.12346", precision, scale);
	    testRounding("999999999999999.9999999", "1000000000000000.00000", precision, scale);
	    
	}
	
	private static void testRounding(String input, String expected, int precision, int scale) {
		
		BigDecimal number = new BigDecimal(input);
		BigDecimal roundedNumber = roundToPrecisionAndScale(number, precision, scale);
		
		System.out.println("Original Number: " + input);
		System.out.println("Rounded Number: " + roundedNumber);
		System.out.println("Rounded Number Precision: " + roundedNumber.precision());
		System.out.println("Expected Number: " + expected);
		System.out.println("Match: " + roundedNumber.toString().equals(expected));
		System.out.println("---------------");
		
	}
	
	private static BigDecimal roundToPrecisionAndScale(BigDecimal number, int precision, int scale) {
		
		// Round to the given scale
		number = number.setScale(scale, RoundingMode.HALF_UP);
		
		// Ensure the precision does not exceed the limit
		if (number.precision() > precision) {
			throw new ArithmeticException("Number exceeds defined precision of " + precision);
		}
		return number;
	}
}
