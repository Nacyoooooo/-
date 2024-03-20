package com.jieduixiangmu;

import java.util.Scanner;
import java.util.Random;
import org.apache.commons.lang3.math.Fraction;

public class operation {
    public static void main(String[] args) {
        System.out.println("欢迎使用小学四则运算题目生成器");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入需要生成的题目个数(1<=n<=10000)：");
        int numberOfProblems = 0;
        int range = 0;
        numberOfProblems = scanner.nextInt();
        System.out.println("请输入生成题目中的数值范围(r>=1)：");
        range = scanner.nextInt();
        if (range < 1) {
            System.out.println("范围必须大于0");
            return;
        }
        Random random = new Random();
        for (int i = 0; i < numberOfProblems; i++) {
            generateArithmeticProblem(range, random);
        }

        scanner.close();


    }

    // 生成一个范围内的随机整数

    public static int generateRandomInt(int range) {
        Random random = new Random();
        return random.nextInt(range) + 1;
    }

    //生成一个随机分数，以字符串形式返回
    public static String generateRandomFraction(int range) {
        Random random = new Random();

        //分母的范围为1~r，分子为1~r*r（确保分数范围在1~r）
        int denominator = random.nextInt(range) + 1;
        int numerator = random.nextInt(range*range) + 1;

        // 简化分数
        int gcd = greatestCommonDivisor(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
        Fraction fraction = Fraction.getFraction(numerator,denominator);
        String fac = fraction.toString();
        return fac;
    }

    // 计算两个数的最大公约数（GCD）
    public static int greatestCommonDivisor(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    //生成一条四则运算题目
    private static void generateArithmeticProblem(int range, Random random) {
        //随机确定生成整数算式还是分数算式
        int choose =random.nextInt(2);
        int num1=0,num2=0;
        Fraction fac1,fac2;
        switch (choose){
            case 0:
                num1 = generateRandomInt(range);
                num2 = generateRandomInt(range);
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