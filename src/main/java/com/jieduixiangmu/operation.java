package com.jieduixiangmu;

import java.util.Scanner;
import java.util.Random;

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


    private static void generateArithmeticProblem(int range, Random random) {
        int num1 = random.nextInt(range) + 1; // 随机生成1到range之间的整数
        int num2 = random.nextInt(range) + 1; // 随机生成1到range之间的整数
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
        System.out.println("题目: " + num1 + " " + operator + " " + num2 + " = " + result);
    }
}