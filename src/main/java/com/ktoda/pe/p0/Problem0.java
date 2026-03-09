package com.ktoda.pe.p0;


/**
 * A number is a perfect square, or a square number, if it is the square of a positive integer.
 * For example, 25 is a square number because 5^2 = 5 x 5 = 25 ; it is also an odd square.
 * The first 5 square numbers are: 1,4,9,16,25, and the sum of the odd squares is 1 + 9 + 25 = 35.
 * Among the first 617 thousand square numbers, what is the sum of all the odd squares?
 */
public class Problem0 {

    private static final int MAX_NUMBERS = 617_000;

    public static void main(String[] args) {
        long oddSum = 0;

        for (int i = 1; i <= MAX_NUMBERS; i++) {
            double powed = Math.pow(i, 2);
            if (powed % 2 != 0) { // is the powed an ODD number
                oddSum += (long) powed;
            }
        }

        System.out.println("Sum of all odd numbers  = " + oddSum);
    }

}
