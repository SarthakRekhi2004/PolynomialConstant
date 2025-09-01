import java.math.BigInteger;
import java.util.*;

public class PolynomialConstantExact {

    // Decode number from base
    static BigInteger decode(String value, int base) {
        return new BigInteger(value, base);
    }

    // Compute constant term c = f(0) using exact rational arithmetic
    static BigInteger findConstant(Map<Integer, BigInteger> points) {
        BigInteger numeratorSum = BigInteger.ZERO;
        BigInteger denominatorLCM = BigInteger.ONE;

        // We'll store each term as numerator/denominator and reduce later
        List<BigInteger[]> fractions = new ArrayList<>();

        for (int j : points.keySet()) {
            BigInteger yj = points.get(j);
            BigInteger num = BigInteger.ONE;
            BigInteger den = BigInteger.ONE;

            for (int i : points.keySet()) {
                if (i != j) {
                    num = num.multiply(BigInteger.valueOf(-i));      // (0 - xi)
                    den = den.multiply(BigInteger.valueOf(j - i));   // (xj - xi)
                }
            }

            // term = yj * num / den
            BigInteger termNum = yj.multiply(num);
            BigInteger termDen = den;

            // Reduce fraction by gcd
            BigInteger g = termNum.gcd(termDen);
            termNum = termNum.divide(g);
            termDen = termDen.divide(g);

            fractions.add(new BigInteger[]{termNum, termDen});
        }

        // Find least common denominator
        for (BigInteger[] frac : fractions) {
            BigInteger den = frac[1].abs(); // denominator always positive
            denominatorLCM = denominatorLCM.multiply(den).divide(denominatorLCM.gcd(den));
        }

        // Convert all to same denominator
        for (BigInteger[] frac : fractions) {
            BigInteger num = frac[0];
            BigInteger den = frac[1];
            BigInteger scale = denominatorLCM.divide(den);
            numeratorSum = numeratorSum.add(num.multiply(scale));
        }

        // Final result = numeratorSum / denominatorLCM
        return numeratorSum.divide(denominatorLCM);
    }

    public static void main(String[] args) {
        // ---- JSON FILE 1 ----
        int k1 = 3;
        Map<Integer, BigInteger> points1 = new LinkedHashMap<>();
        points1.put(1, decode("4", 10));       // base 10 → 4
        points1.put(2, decode("111", 2));      // base 2 → 7
        points1.put(3, decode("12", 10));      // base 10 → 12
        points1.put(6, decode("213", 4));      // base 4 → 39

        // Use first k1 points
        Map<Integer, BigInteger> subset1 = new LinkedHashMap<>();
        int count = 0;
        for (int key : points1.keySet()) {
            if (count < k1) {
                subset1.put(key, points1.get(key));
                count++;
            }
        }

        BigInteger c1 = findConstant(subset1);
        System.out.println("File 1 → Constant c = " + c1);


        // ---- JSON FILE 2 ----
        int k2 = 7;
        Map<Integer, BigInteger> points2 = new LinkedHashMap<>();
        points2.put(1, decode("13444211440455345511", 6));
        points2.put(2, decode("aed7015a346d635", 15));
        points2.put(3, decode("6aeeb69631c227c", 15));
        points2.put(4, decode("e1b5e05623d881f", 16));
        points2.put(5, decode("316034514573652620673", 8));
        points2.put(6, decode("2122212201122002221120200210011020220200", 3));
        points2.put(7, decode("20120221122211000100210021102001201112121", 3));
        points2.put(8, decode("20220554335330240002224253", 6));
        points2.put(9, decode("45153788322a1255483", 12));
        points2.put(10, decode("1101613130313526312514143", 7));

        // Use first k2 points
        Map<Integer, BigInteger> subset2 = new LinkedHashMap<>();
        count = 0;
        for (int key : points2.keySet()) {
            if (count < k2) {
                subset2.put(key, points2.get(key));
                count++;
            }
        }

        BigInteger c2 = findConstant(subset2);
        System.out.println("File 2 → Constant c = " + c2);
    }
}
