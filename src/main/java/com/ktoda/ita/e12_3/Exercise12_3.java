package com.ktoda.ita.e12_3;

import static java.lang.Math.*;

/**
 * Find the smallest value of {@code n} such that, on the same machine,
 * an algorithm with running time {@code 100n^2} runs faster than an
 * algorithm with running time {@code 2^n}.
 * <p>
 * Answer: n = 15
 */
public class Exercise12_3 {

    public static void main(String[] args) {
        long n = 1;

        while ((100 * pow(n, 2)) > pow(2, n)) {
            n++;
        }

        System.out.println("The smallest value of 'n' " + n);

    }

}
