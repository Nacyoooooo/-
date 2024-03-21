package com.jieduixiangmu;

import java.util.Scanner;
import java.util.Random;
import org.apache.commons.lang3.math.Fraction;

public class operation {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: Myapp.exe -n <number_of_problems> -r <range>");
            System.out.println("Example: Myapp.exe -n 10 -r 5");
            return;
        }
        int numberOfProblems = 0;
        int range = 0;
//        for (String arg : args) {
//            if (arg.startsWith("-n")) {
//                String[] split = arg.split("-");
//                numberOfProblems = Integer.parseInt(arg.split("-")[1]);
//            } else if (arg.startsWith("-r")) {
//                range = Integer.parseInt(arg.split("-")[1]);
//            }
//        }
        try {
            for (int i = 0; i < args.length; i++) {
                if(args[i].startsWith("-n")){
                    numberOfProblems = Integer.parseInt(args[i+1]);
                }else if (args[i].startsWith("-r")) {
                    range = Integer.parseInt(args[i+1]);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        if (range < 1) {
            System.out.println("Range must be a natural number greater than 0.");
            return;
        }
        Random random = new Random();
        for (int i = 0; i < numberOfProblems; i++) {
            generateArithmeticProblem(range, random);
        }


    }

    /**
     * 生成一个范围内的随机整数
     * @param range
     * @return
     */

    public static int generateRandomInt(int range) {
        Random random = new Random();
        return random.nextInt(range) + 1;
    }


    /**
     * 生成一个随机分数，以字符串形式返回
     * @param range
     * @return
     */
    public static String generateRandomFraction(int range) {
        Random random = new Random();

        //分母的范围为1~r，分子为1~r*r（确保分数范围在1~r）
        int denominator = random.nextInt(range) + 1;
        int numerator = random.nextInt(range*range) + 1;

        // 简化分数
        int gcd = greatestCommonDivisor(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        String fac;
        if (numerator<denominator){
            Fraction fraction = Fraction.getFraction(numerator,denominator);
            fac = fraction.toString();
        }
        else {
            Fraction fraction = Fraction.getFraction(numerator,denominator);
            fac = fraction.toProperString();
        }

        return fac;
    }

    /**
     * 计算两个数的最大公约数（GCD）
     * @param a
     * @param b
     * @return
     */
    public static int greatestCommonDivisor(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /**
     * 生成一条四则运算题目
     * @param range
     * @param random
     */
    private static void generateArithmeticProblem(int range, Random random) {
        //随机确定生成整数算式还是分数算式
        int choose =random.nextInt(2);
        int num1=0,num2=0;
        Fraction fac1,fac2;
        switch (choose){
            case 0:
                num1 = generateRandomInt(range);
                num2 = generateRandomInt(range);
                while (num2==0){
                    num2=generateRandomInt(range);
                }
                break;
            case 1:
                fac1 = Fraction.getFraction(generateRandomFraction(range));
                fac2 = Fraction.getFraction(generateRandomFraction(range));
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }

        String operator;
        int result;

        // 随机选择运算符
        switch (random.nextInt(4)) {
            case 0:
                operator = "+";
                result = num1 + num2;
                break;
            case 1:
                operator = "-";
                // 确保不会生成负数
                while (num1 < num2) {
                    num1 = random.nextInt(range) + 1;
                    num2 = random.nextInt(range) + 1;
                }
                result = num1 - num2;
                break;
            case 2:
                operator = "*";
                result = num1 * num2;
                break;
            case 3:
                operator = "/";
                // 确保结果为真分数，且不会除以0
                while (num2 == 1 || num1 % num2 == 0) {
                    num1 = random.nextInt(range) + 1;
                    num2 = random.nextInt(range) + 1;
                }
                result = num1 / num2;
                break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
        String problem = num1+operator+num2+"=?";
        System.out.println("题目: " + num1 + " " + operator + " " + num2 + " = " + result);
    }
}