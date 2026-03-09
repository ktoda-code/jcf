package com.ktoda.ita.e12_2;

import static com.ktoda.ita.util.ItaUtil.lg;

/**
 * For which size of <code>n</code> does insertion sort beat merge sort:
 *
 * <ul>
 *     <li>insertion sort: <code>8 n^2</code> -> <code>n</code></li>
 *     <li>merge sort: <code>64 n(lg n)</code> -> <code>8 (lg n)</code></li>
 * </ul>
 *
 * Answer: 2 <= n <= 43
 */
public class Exercise12_2 {

    public static void main(String[] args) {
        long n = 2;

        while (n < 8 * lg(n)) {
            n++;
        }

        System.out.println("Largest 'n' where insertion beats merge: " + (n - 1));
    }

}
