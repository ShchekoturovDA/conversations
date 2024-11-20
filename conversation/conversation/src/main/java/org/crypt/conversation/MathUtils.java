package org.crypt.conversation;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtils {

    // Первые несколько простых чисел
    static List <Integer> firstPrimes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29,
            31, 37, 41, 43, 47, 53, 59, 61, 67,
            71, 73, 79, 83, 89, 97, 101, 103,
            107, 109, 113, 127, 131, 137, 139,
            149, 151, 157, 163, 167, 173, 179,
            181, 191, 193, 197, 199, 211, 223,
            227, 229, 233, 239, 241, 251, 257,
            263, 269, 271, 277, 281, 283, 293,
            307, 311, 313, 317, 331, 337, 347, 349);

    // Случайно генерируются числа
    // Проверяются на простоту относительно первых нескольких простых
    public static BigInteger generateEasy(int n){
        while (true) {
            BigInteger pretend = new BigInteger(n, new Random());
            int count = 0;
            for (Integer prime : firstPrimes){
                if(pretend.mod(BigInteger.valueOf(prime)).equals(BigInteger.ZERO)){
                    break;
                }
                count++;
            }
            if (count == firstPrimes.toArray().length){
                return pretend;
            }
        }
    }

    // Алгоритм быстрого возведения в степень
    public static BigInteger fastPower(BigInteger base, BigInteger exp, BigInteger mod){
        if (exp.equals(BigInteger.ZERO)){
            return BigInteger.ONE;
        } else if( exp.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return fastPower(base, exp.divide(BigInteger.TWO), mod).pow(2).mod(mod);
        } else {
            return fastPower(base, exp.subtract(BigInteger.ONE), mod).multiply(base).mod(mod);
        }
    }

    // Итерация теста Миллера-Рабина на простоту
    public static boolean MillerRabinTest(BigInteger tester, BigInteger event, BigInteger pretend, int maxDiv){
        if (fastPower(tester, event, pretend).equals(BigInteger.ONE)){
            return true;
        } else {
            for (int i = 0; i < maxDiv; i++) {
                if (fastPower(tester, event.multiply(BigInteger.valueOf(1 << i)), pretend).equals(pretend.subtract(BigInteger.ONE))){
                    return true;
                }
            }
            return false;
        }
    }

    // Генерация простых(скорее всего) чисел
    public static BigInteger generatePrimes(int n){
        while(true) {

            // Генерируется число, не делящееся на первые несколько простых чисел
            BigInteger pretend = generateEasy(n);
            int maxDiv = 0;
            BigInteger event = pretend.subtract(BigInteger.ONE);

            while (event.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                event = event.shiftRight(1);
                maxDiv++;
            }
            int count = 0;

            // Проводится 20 итераций теста Миллера-Рабина
            // Если число прошло эти тесты, то скорее всего оно простое
            for (int i = 0; i < 20; i++) {
                BigInteger tester;
                do {
                    tester = new BigInteger(n, new Random());
                } while (tester.compareTo(pretend.subtract(BigInteger.TWO)) >= 0);
                if (!MillerRabinTest(tester, event, pretend, maxDiv)){
                    break;
                };
                count++;
            }
            if (count == 20) {
                return pretend;
            }
        }
    }
}
